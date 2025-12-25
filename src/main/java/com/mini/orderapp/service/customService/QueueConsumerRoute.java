package com.mini.orderapp.service.customService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.orderapp.service.dto.OrdersDTO;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumerRoute extends RouteBuilder {

    private final ObjectMapper objectMapper;

    public QueueConsumerRoute(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure() {
        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(objectMapper, OrdersDTO.class);

        from("activemq:queue:ORDER.CREATED.QUEUE")
            .routeId("activemq-consumer-route")
            .unmarshal(jacksonDataFormat)
            .log("Order processed | OrderId=${body.id} | CustomerId=${body.customerId} | Amount=${body.amount}");
    }
}
