package com.example.TickNet.service;

import com.example.TickNet.entity.Checkout;
import com.example.TickNet.entity.CheckoutPayment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    public StripeService(@Value("${stripe.secret-key}") String secretKey) {
        Stripe.apiKey = secretKey;
    }

    public String paymentWithCheckoutPage(CheckoutPayment payment) {
        try {
            Session session = createPaymentStripeSession(payment);
            return "{\"sessionId\":\"" + session.getId() + "\"}";
        } catch (StripeException e) {
            return null;
        }
    }

    public String subscriptionWithCheckoutPage(Checkout checkout) {
        try {
            Session session = createSubscriptionStripeSession(checkout);
            return "{\"sessionId\":\"" + session.getId() + "\"}";
        } catch (StripeException e) {
            return null;
        }
    }

    private Session createPaymentStripeSession(CheckoutPayment payment) throws StripeException {
        // gère Long ou long proprement
        Long qtyObj = null;
        try { qtyObj = payment.getQuantity(); } catch (Throwable ignored) {}
        long qty = (qtyObj == null || qtyObj <= 0) ? 1L : qtyObj;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(payment.getSuccessUrl())
                .setCancelUrl(payment.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(qty)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(payment.getCurrency())
                                                .setUnitAmount(payment.getAmount())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(payment.getName())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        return Session.create(params);
    }

    private Session createSubscriptionStripeSession(Checkout checkout) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl(checkout.getSuccessUrl())
                .setCancelUrl(checkout.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice(checkout.getPriceId())
                                .build()
                )
                .build();

        return Session.create(params);
    }
}
