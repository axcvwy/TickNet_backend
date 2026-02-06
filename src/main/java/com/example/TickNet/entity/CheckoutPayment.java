package com.example.TickNet.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mamadou
 */
@Getter
@Setter
public class CheckoutPayment {
    private String name;
    private String currency;
    private String successUrl;
    private String cancelUrl;
    private long amount;
    private long quantity;
}

