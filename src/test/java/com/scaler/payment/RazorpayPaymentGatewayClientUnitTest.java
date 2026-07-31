//package com.scaler.payment;
//
//
//import com.razorpay.*;
//import com.scaler.payment.clients.RazorpayPaymentGatewayClient;
//import com.scaler.payment.dto.RazorpayCustomerContactDetails;
//import com.scaler.payment.dto.RazorpayPlanRequest;
//import com.scaler.payment.dto.RazorpaySubscriptionRequest;
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class RazorpayPaymentGatewayClientUnitTest {
//    @MockitoBean
//    private RazorpayClient razorpayClient;
//
//    @MockitoBean
//    private PlanClient planClient;
//
//    @MockitoBean
//    private SubscriptionClient subscriptionClient;
//
//    @Autowired
//    private RazorpayPaymentGatewayClient paymentGatewayClient;
//
//    @BeforeEach
//    public void setup() {
//        razorpayClient.plans = planClient;
//        razorpayClient.subscriptions = subscriptionClient;
//    }
//
//    @Test
//    void testCreateSubscriptionLink_Success() throws RazorpayException {
//        // Arrange
//        RazorpayCustomerContactDetails contactDetails = new RazorpayCustomerContactDetails();
//        contactDetails.setPhoneNumber("1234567890");
//        contactDetails.setEmail("test@example.com");
//
//        RazorpayPlanRequest planRequest = new RazorpayPlanRequest();
//        planRequest.setFrequency("monthly");
//        planRequest.setName("Gold Plan");
//        planRequest.setDescription("Premium subscription");
//        planRequest.setAmount(99.99);
//
//        RazorpaySubscriptionRequest subscriptionRequest = new RazorpaySubscriptionRequest();
//        subscriptionRequest.setRazorpayPlanRequest(planRequest);
//        subscriptionRequest.setTotalCount(12L);
//        subscriptionRequest.setQuantity(5L);
//        subscriptionRequest.setStartTime(1L);
//        subscriptionRequest.setExpiryTime(12L);
//        subscriptionRequest.setRazorpayCustomerContactDetails(contactDetails);
//
//        JSONObject planJsonObject = new JSONObject();
//        planJsonObject.put("id","planid_234qerw");
//        Plan plan = new Plan(planJsonObject);
//        when(planClient.create(argThat(new PlanJsonObjectMatcher("Gold Plan",99.99,"INR","Premium subscription")))).thenReturn(plan);
//
//
//        String expectedUrl = "https://rzp.io/i/m0y0f";
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("short_url",expectedUrl);
//        Subscription subscription = new Subscription(jsonObject);
//        when(subscriptionClient.create(argThat(new SubscriptionJsonObjectMatcher(12L,5L,1L,12L,"1234567890","test@example.com","planid_234qerw","offer_JTUADI4ZWBGWur")))).thenReturn(subscription);
//
//        // Act
//        Subscription response = paymentGatewayClient.createSubscriptionLink(subscriptionRequest);
//
//        // Assert
//        assertEquals(expectedUrl, response.get("short_url").toString(), "The returned URL should match the expected URL");
//    }
//
//    @Test
//    void testCreateSubscriptionLink_CreatePlanThrowsException() throws RazorpayException {
//
//        RazorpayPlanRequest planRequest = new RazorpayPlanRequest();
//        planRequest.setFrequency("monthly");
//        planRequest.setName("Gold Plan");
//        planRequest.setDescription("Premium subscription");
//        planRequest.setAmount(99.99);
//        RazorpaySubscriptionRequest subscriptionRequest = new RazorpaySubscriptionRequest();
//        subscriptionRequest.setRazorpayPlanRequest(planRequest);
//
//        when(planClient.create(argThat(new PlanJsonObjectMatcher("Gold Plan",99.99,"INR","Premium subscription"))))
//                .thenThrow(new RazorpayException("Failed to create plan"));
//
//        // Act & Assert
//        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
//            paymentGatewayClient.createSubscriptionLink(subscriptionRequest
//            );
//        });
//
//        assertEquals("Failed to create plan", thrown.getMessage(), "Exception message should match");
//    }
//
//    @Test
//    void testCreateSubscriptionLink_CreateSubscriptionLinkThrowsException() throws RazorpayException {
//        // Arrange
//        RazorpayCustomerContactDetails contactDetails = new RazorpayCustomerContactDetails();
//        contactDetails.setPhoneNumber("1234567890");
//        contactDetails.setEmail("test@example.com");
//
//        RazorpayPlanRequest planRequest = new RazorpayPlanRequest();
//        planRequest.setFrequency("monthly");
//        planRequest.setName("Gold Plan");
//        planRequest.setDescription("Premium subscription");
//        planRequest.setAmount(99.99);
//
//        RazorpaySubscriptionRequest subscriptionRequest = new RazorpaySubscriptionRequest();
//        subscriptionRequest.setRazorpayPlanRequest(planRequest);
//        subscriptionRequest.setTotalCount(12L);
//        subscriptionRequest.setQuantity(5L);
//        subscriptionRequest.setStartTime(1L);
//        subscriptionRequest.setExpiryTime(12L);
//        subscriptionRequest.setRazorpayCustomerContactDetails(contactDetails);
//
//        JSONObject planJsonObject = new JSONObject();
//        planJsonObject.put("id","planid_234qerw");
//        Plan plan = new Plan(planJsonObject);
//        when(planClient.create(argThat(new PlanJsonObjectMatcher("Gold Plan",99.99,"INR","Premium subscription")))).thenReturn(plan);
//
//        when(subscriptionClient.create(argThat(new SubscriptionJsonObjectMatcher(12L,5L,1L,12L,"1234567890","test@example.com","planid_234qerw","offer_JTUADI4ZWBGWur"))))
//                .thenThrow(new RazorpayException("Failed to create subscription link"));
//
//        // Act & Assert
//        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
//            paymentGatewayClient.createSubscriptionLink(subscriptionRequest
//            );
//        });
//
//        assertEquals("Failed to create subscription link", thrown.getMessage(), "Exception message should match");
//    }
//}
//
