package com.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.model.ChargeRequest;
import com.store.model.ChargeRequest.Currency;
import com.store.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@RestController
public class ChargeController {

    @Autowired
    private StripeService stripeService;

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @PostMapping("/charge")
    public ResponseEntity<Charge> charge(@RequestBody ChargeRequest chargeRequest)
            throws StripeException {
        chargeRequest.setStripeToken(stripePublicKey);
        // chargeRequest.setDescription("Example charge");
        // chargeRequest.setCurrency(Currency.EUR);
        Charge charge = stripeService.charge(chargeRequest);
        // model.addAttribute("id", charge.getId());
        // model.addAttribute("status", charge.getStatus());
        // model.addAttribute("chargeId", charge.getId());
        // model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return ResponseEntity.ok(charge);
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(StripeException ex) {
        System.out.println("error" + ex.getMessage());
        return "result";
    }
}