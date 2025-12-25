package com.mini.orderapp.service.mapper;

import static com.mini.orderapp.domain.OrdersAsserts.*;
import static com.mini.orderapp.domain.OrdersTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdersMapperTest {

    private OrdersMapper ordersMapper;

    @BeforeEach
    void setUp() {
        ordersMapper = new OrdersMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrdersSample1();
        var actual = ordersMapper.toEntity(ordersMapper.toDto(expected));
        assertOrdersAllPropertiesEquals(expected, actual);
    }
}
