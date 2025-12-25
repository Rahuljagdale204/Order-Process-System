package com.mini.orderapp.service.customService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.orderapp.service.dto.OrdersDTO;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderFileWriter {

    private static final Logger LOG = LoggerFactory.getLogger(OrderFileWriter.class);

    private final ObjectMapper objectMapper;

    public OrderFileWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void writeOrder(OrdersDTO ordersDTO) {
        try {
            Path dir = Path.of("input", "orders");
            Files.createDirectories(dir);

            Path filePath = dir.resolve("order-" + ordersDTO.getId() + ".json");
            objectMapper.writeValue(filePath.toFile(), ordersDTO);

            LOG.info("Order file written successfully: {}", filePath);
        } catch (Exception e) {
            LOG.error("Exception in writeOrder()", e);
            throw new RuntimeException("Failed to write order file", e);
        }
    }
}
