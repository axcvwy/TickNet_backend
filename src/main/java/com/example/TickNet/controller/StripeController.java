package com.example.TickNet.controller;

import com.example.TickNet.entity.Checkout;
import com.example.TickNet.entity.CheckoutPayment;
import com.example.TickNet.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Aly
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/service/stripe")
@CrossOrigin(origins = "http://localhost:5174")
public class StripeController {
    private final StripeService stripeService;

    /**
     * @param payment
     * @return
     */
    @PostMapping("/payment")
    public ResponseEntity<Map<String, String>> paymentWithCheckoutPage(@RequestBody CheckoutPayment payment) {
        Map<String, String> response = stripeService.paymentWithCheckoutPage(payment);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * @param checkout
     * @return
     */
    @PostMapping("/subscription")
    public ResponseEntity<Map<String, String>> subscriptionWithCheckoutPage(@RequestBody Checkout checkout) {
        Map<String, String> response = stripeService.subscriptionWithCheckoutPage(checkout);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
}
