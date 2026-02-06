package com.example.TickNet.controller;

import com.example.TickNet.entity.Checkout;
import com.example.TickNet.entity.CheckoutPayment;
import com.example.TickNet.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aly
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/service/stripe")
public class StripeController {
    private final StripeService stripeService;

    /**
     * @param payment
     * @return
     */
    @PostMapping("/payment")
    public String paymentWithCheckoutPage(@RequestBody CheckoutPayment payment) {
        return stripeService.paymentWithCheckoutPage(payment);
    }

    /**
     * @param checkout
     * @return
     */
    @PostMapping("/subscription")
    public String subscriptionWithCheckoutPage(@RequestBody Checkout checkout) {
        return stripeService.subscriptionWithCheckoutPage(checkout);
    }
}

