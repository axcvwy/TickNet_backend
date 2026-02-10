package com.example.TickNet.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Aly
 */
@Getter
@Setter
public class Checkout {
    private String priceId;
    private String successUrl;
    private String cancelUrl;
}

