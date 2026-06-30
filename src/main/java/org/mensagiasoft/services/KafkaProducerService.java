package org.mensagiasoft.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;

import org.mensagiasoft.dto.WhatsappRequest;

@ApplicationScoped
public class KafkaProducerService {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    @Channel("whatsapp-out")
    Emitter<String> emitter;

    public void sendKafka(WhatsappRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request);

            OutgoingKafkaRecordMetadata<String> metadata =
                    OutgoingKafkaRecordMetadata.<String>builder()
                            .withKey(request.getPhoneNumber())
                            .build();

            Message<String> message = Message.of(json)
                    .addMetadata(metadata);

            emitter.send(message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing the message", e);
        }

    }

}