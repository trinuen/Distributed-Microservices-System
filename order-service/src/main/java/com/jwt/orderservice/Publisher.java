package com.jwt.orderservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.UUID;

@Component
@AllArgsConstructor
public class Publisher {
    private final SqsClient sqsClient;

    public void publish(String queueUrl, String messageBody, String messageGroupId){
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .messageGroupId(messageGroupId)
                .messageDeduplicationId(UUID.randomUUID().toString())
                .build();
        System.out.println(sqsClient.sendMessage(request));
    }
}
