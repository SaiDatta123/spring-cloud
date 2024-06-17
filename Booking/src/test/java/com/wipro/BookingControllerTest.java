package com.wipro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.Service.BookingService;
import com.wipro.entity.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBookings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetBookingById() throws Exception {
        // Assuming booking with id 1 exists
        mockMvc.perform(MockMvcRequestBuilders.get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateBooking() throws Exception {
        Booking booking = new Booking();
        booking.setBookingDescription("Sample Booking");
        booking.setOrderNumber("BK-01");
        booking.setPropertyId(1);
        booking.setNumRooms(2);
        booking.setBookingStatus("Initiated");

        String jsonBooking = objectMapper.writeValueAsString(booking);

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBooking))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBooking() throws Exception {
        Booking booking = new Booking();
        booking.setId(1);
        booking.setBookingDescription("Updated Booking");
        booking.setOrderNumber("BK-01");
        booking.setNumRooms(2);
        booking.setBookingStatus("Booked");

        String jsonBooking = objectMapper.writeValueAsString(booking);

        mockMvc.perform(MockMvcRequestBuilders.put("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBooking))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBooking() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
