package org.mensagiasoft.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.mensagiasoft.dto.WhatsappRequest;

@ApplicationScoped
public class KafkaProducerService {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    @Channel("whatsapp-out")
    Emitter<KafkaRecord<String, String>> emitter;

    public void sendKafka(WhatsappRequest request) {

        try {

            String json = objectMapper.writeValueAsString(request);

            KafkaRecord<String, String> record =
                    KafkaRecord.of(request.getPhoneNumber(), json);

            emitter.send(record);

        } catch (JsonProcessingException e) {

            throw new RuntimeException("Error serializing the message", e);

        }

    }
}