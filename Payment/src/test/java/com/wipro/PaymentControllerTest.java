package com.wipro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.entity.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPayments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pay")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetPaymentById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pay/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCreatePayment() throws Exception {
        Payment payment = new Payment();
        payment.setBookingId(1);
        payment.setAmount("100.00");

        String jsonPayment = objectMapper.writeValueAsString(payment);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayment))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Payment savedPayment = objectMapper.readValue(content, Payment.class);

        assertEquals(1, savedPayment.getBookingId());
        assertEquals("100.00", savedPayment.getAmount());
    }

    @Test
    void testUpdatePayment() throws Exception {
        Payment payment = new Payment();
        payment.setId(1);
        payment.setBookingId(1);
        payment.setAmount("150.00");

        String jsonPayment = objectMapper.writeValueAsString(payment);

        mockMvc.perform(MockMvcRequestBuilders.put("/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayment))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePayment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/pay/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
