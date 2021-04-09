package com.shichko.deliveryservice.model.entity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStateTest {
    @Test
    public void test() {
        String testState = "Ordered";
        OrderState state = OrderState.valueOf(testState);

        assertEquals(testState, state.name());

    }
}