package com.domin0x.NBARadars.amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

        @Value("${amazonS3.accessKey}")
        String accessKey;
        @Value("${amazonS3.secretKey}")
        String accessSecret;

        @Bean
        public AmazonS3 generateS3Client() {
            AWSCredentials creds = new BasicAWSCredentials(accessKey,accessSecret);

            return AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1)
                    .withCredentials(new AWSStaticCredentialsProvider(creds)).build();
        }
}
