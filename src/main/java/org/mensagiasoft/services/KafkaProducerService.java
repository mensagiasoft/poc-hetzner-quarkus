package org.mensagiasoft.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
public class KafkaProducerService {

    @Inject
    @Channel("whatsapp-out")
    Emitter<Message<String>> emitter;

    public void sendKafka(String phoneNumber, String message) {

        emitter.send(KafkaRecord.of(phoneNumber, message));
    }
}