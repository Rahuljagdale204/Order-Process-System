package com.mini.orderapp.service.customService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.orderapp.service.dto.OrdersDTO;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class FileToQueueRoute extends RouteBuilder {

    private final ObjectMapper objectMapper;

    public FileToQueueRoute(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure() {
        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(objectMapper, OrdersDTO.class);

        from("file:input/orders?delete=true")
            .routeId("file-to-activemq-route")
            .log("Processing file: ${file:name}")
            .unmarshal(jacksonDataFormat)
            .choice()
            .when(simple("${body.id} != null && ${body.customerId} != null && ${body.amount} > 0"))
            .log("Valid order | OrderId=${body.id}")
            // convert back to JSON
            .marshal(jacksonDataFormat)
            // send JSON string, NOT Java object
            .to("activemq:queue:ORDER.CREATED.QUEUE?exchangePattern=InOnly")
            .otherwise()
            .log("Invalid order file | File=${file:name}")
            .to("file:error/orders");
    }
}
