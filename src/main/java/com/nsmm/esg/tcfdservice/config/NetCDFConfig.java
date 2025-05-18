package com.nsmm.esg.tcfdservice.config;

import com.nsmm.esg.tcfdservice.util.NetCDFUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class NetCDFConfig {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Bean
    public NetCDFUtils netCDFUtils(S3Client s3Client) {
        return new NetCDFUtils(s3Client, bucket);
    }
}
