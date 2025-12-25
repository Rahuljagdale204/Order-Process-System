package com.mini.orderapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrdersTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Orders getOrdersSample1() {
        return new Orders().id(1L).customerId("customerId1").product("product1");
    }

    public static Orders getOrdersSample2() {
        return new Orders().id(2L).customerId("customerId2").product("product2");
    }

    public static Orders getOrdersRandomSampleGenerator() {
        return new Orders().id(longCount.incrementAndGet()).customerId(UUID.randomUUID().toString()).product(UUID.randomUUID().toString());
    }
}
