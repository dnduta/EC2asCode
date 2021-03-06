package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.autoscaling.AutoScalingClient;
import software.amazon.awssdk.services.elasticloadbalancingv2.ElasticLoadBalancingV2Client;

@Component
public class AwsClients {

    @Value("${aws.accessKeyId}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secret;

    @Bean
    public AwsCredentialsProvider credentialsProvider() {
//        return new SystemPropertiesCredentialsProvider();
        return StaticCredentialsProvider.create(credentials());
    }

    @Bean
    public AwsBasicCredentials credentials()
    {
        return  AwsBasicCredentials.create(accessKey, secret);
    }

    @Bean
    public ElasticLoadBalancingV2Client elb()
    {
        return ElasticLoadBalancingV2Client.builder()
                .credentialsProvider(credentialsProvider())
                .region(Region.US_EAST_2)
                .build();
    }

    @Bean
    public AutoScalingClient scalingClient()
    {
        return AutoScalingClient.builder()
                .credentialsProvider(credentialsProvider())
                .region(Region.US_EAST_2)
                .build();
    }
}
