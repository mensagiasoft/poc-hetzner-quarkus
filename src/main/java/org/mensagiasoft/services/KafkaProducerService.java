package org.mensagiasoft.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.mensagiasoft.dto.WhatsappRequest;

@ApplicationScoped
public class KafkaProducerService {

    @Inject
    @Channel("whatsapp-out")
    Emitter<KafkaRecord<String, WhatsappRequest>> emitter;

    public void sendKafka(WhatsappRequest request) {
        KafkaRecord<String, WhatsappRequest> record = KafkaRecord.of(request.getPhoneNumber(), request);
        emitter.send(record);
    }
}