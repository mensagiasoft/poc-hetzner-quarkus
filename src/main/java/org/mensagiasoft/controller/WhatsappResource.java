package org.mensagiasoft.controller;

import org.mensagiasoft.dto.WhatsappRequest;
import org.mensagiasoft.services.KafkaProducerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/whatsapp")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WhatsappResource {

    @Inject
    KafkaProducerService producer;

    @POST
    public String send(WhatsappRequest request) {

        producer.sendKafka(request);

        return "Sent Message: " + request.getMessage();
    }
}
