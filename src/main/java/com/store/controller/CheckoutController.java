package com.store.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.ProductDao;
import com.store.dto.RequestDTO;
import com.store.model.ChargeRequest;
import com.store.util.CustomerUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.common.EmptyParam;
import com.stripe.param.checkout.SessionCreateParams.ShippingAddressCollection;

@RestController
@CrossOrigin
public class CheckoutController {

        @Value("${STRIPE_PUBLIC_KEY}")
        private String stripePublicKey;

        @Value("${STRIPE_SECRET_KEY}")
        private String stripeSecretKey;

        @Value("${CLIENT_BASE_URL}")
        private String client_base_url;

        @PostMapping("/checkout/hosted")
        String hostedCheckout(@RequestBody RequestDTO requestDTO) throws StripeException {

                Stripe.apiKey = stripeSecretKey;
                // String clientBaseURL = System.getenv().get("CLIENT_BASE_URL");
                String clientBaseURL = client_base_url;

                // Start by finding an existing customer record from Stripe or creating a new
                // one if needed
                // Customer customer =
                // CustomerUtil.findOrCreateCustomer(requestDTO.getCustomerEmail(),
                // requestDTO.getCustomerName());

                System.out.println(requestDTO.getCustomerName());

                CustomerCreateParams params = CustomerCreateParams.builder()
                                .setName(requestDTO.getCustomerName())
                                .setEmail(requestDTO.getCustomerEmail())
                                .setAddress(
                                                CustomerCreateParams.Address.builder()
                                                                .setLine1("510 Townsend St")
                                                                .setPostalCode("98140")
                                                                .setCity("San Francisco")
                                                                .setState("Delhi")
                                                                .setCountry("Australia")
                                                                .build())
                                .build();

                Customer customer = Customer.create(params);

                System.out.println(clientBaseURL);
                // System.out.println(customer);
                // Next, create a checkout session by adding the details of the checkout
                SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                                .setMode(SessionCreateParams.Mode.PAYMENT)
                                .setCustomer(customer.getId())
                                .setCurrency("INR")
                                .setSuccessUrl(clientBaseURL + "/success?session_id={CHECKOUT_SESSION_ID}")
                                .setCancelUrl(clientBaseURL + "/failure");

                for (Product product : requestDTO.getItems()) {
                        paramsBuilder.addLineItem(
                                        SessionCreateParams.LineItem.builder()
                                                        .setQuantity(1L)
                                                        .setPriceData(
                                                                        PriceData.builder()
                                                                                        .setProductData(
                                                                                                        PriceData.ProductData
                                                                                                                        .builder()
                                                                                                                        .putMetadata("app_id",
                                                                                                                                        product.getId())
                                                                                                                        .setName(product.getName())
                                                                                                                        .build())
                                                                                        // .setCurrency(ProductDao.getProduct(product.getId()).getDefaultPriceObject()
                                                                                        // .getCurrency())
                                                                                        .setCurrency("INR")
                                                                                        .setUnitAmountDecimal(ProductDao
                                                                                                        .getProduct(product
                                                                                                                        .getId())
                                                                                                        .getDefaultPriceObject()
                                                                                                        .getUnitAmountDecimal())
                                                                                        .build())
                                                        .build());
                }

                Session session = Session.create(paramsBuilder.build());

                return session.getUrl();
        }

        // @RequestMapping("/checkout")
        // public String checkout(Model model) {
        // model.addAttribute("amount", 50 * 100); // in cents
        // model.addAttribute("stripePublicKey", stripePublicKey);
        // model.addAttribute("currency", ChargeRequest.Currency.INR);
        // return "checkout";
        // }
}