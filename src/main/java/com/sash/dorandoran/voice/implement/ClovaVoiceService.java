package com.sash.dorandoran.voice.implement;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

@Service
public class ClovaVoiceService {

    private final AmazonS3 amazonS3;

    @Value("${clova.api.client-id}")
    private String clientId;

    @Value("${clova.api.client-secret}")
    private String clientSecret;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucketName;

    public ClovaVoiceService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String synthesizeSpeechAndUpload(String speaker, String text, String format) {
        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            // post request
            String postParams = "speaker=" + speaker + "&volume=0&speed=0&pitch=0&format=" + format + "&text=" + encodedText;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File tempFile = File.createTempFile(tempname, "." + format);
                try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                    while ((read = is.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                }
                is.close();

                // Check if file is created successfully
                if (tempFile.length() == 0) {
                    throw new RuntimeException("The synthesized speech file is empty");
                }

                // Set metadata for the file
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType("audio/" + format);
                metadata.setContentLength(tempFile.length());

                // Upload the file to Object Storage with ACL setting
                String fileName = "speech/" + tempFile.getName();
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, tempFile)
                        .withMetadata(metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead);
                amazonS3.putObject(putObjectRequest);

                // Return the URL of the uploaded file
                return amazonS3.getUrl(bucketName, fileName).toString();
            } else { // 오류 발생
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.err.println("Error response body: " + response.toString());
                throw new RuntimeException("Failed to synthesize speech: " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process request", e);
        }
    }
}
