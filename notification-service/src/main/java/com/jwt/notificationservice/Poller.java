package com.jwt.notificationservice;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
public class Poller {
    private final SqsClient sqsClient;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.notifications-queue-url}")
    private String notificationQueueUrl;  // injected separately, NOT via constructor

    // manual constructor for beans only — excludes the @Value field
    public Poller(SqsClient sqsClient,
                  NotificationService notificationService,
                  ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 5000)
    public void poll() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(notificationQueueUrl)
                .maxNumberOfMessages(10)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        for (Message message : messages) {
            try {
                // deserialize JSON back into event object
                OrderCreatedEvent event = objectMapper
                        .readValue(message.body(), OrderCreatedEvent.class);

                // hand off to service for business logic
                notificationService.handleOrderCreated(event);

                // delete from SQS so it doesn't redeliver
                sqsClient.deleteMessage(DeleteMessageRequest.builder()
                        .queueUrl(notificationQueueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build());

            } catch (Exception e) {
                System.out.println("Failed to process message: " + e.getMessage());
            }
        }
    }
}
