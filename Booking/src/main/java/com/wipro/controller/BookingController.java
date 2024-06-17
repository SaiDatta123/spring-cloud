package com.wipro.controller;

import com.wipro.Service.BookingService;
import com.wipro.entity.Booking;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Tag(name = "Booking", description = "Booking management APIs")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
    @CircuitBreaker(name = "paymentCircuitBreaker", fallbackMethod = "showServiceDown")
    @Operation(summary = "Save a Booking",
            description = "Saves the booking object into the database based on the availability of rooms and payment.")
    public Booking addBooking(@RequestBody Booking booking) {
        Booking booked = bookingService.saveBooking(booking);
        if (booked !=null) {
            return booked;
        }else {
            throw new RuntimeException("Error came in booking");
        }
    }

    public Booking showServiceDown(Throwable throwable) {
        System.out.println("System is down  " + throwable.getMessage());
        return null;
    }

    @GetMapping
    @Operation(summary = "Get all Bookings", description = "Gets all bookings present in the database.")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Booking ", description = "Gets a booking present in the database for the given Id.")
    public ResponseEntity<Booking> getBookingById(@PathVariable int id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Booking ", description = "Deletes a booking present in the database for the given Id.")
    public void deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
    }

    @PutMapping
    @Operation(summary = "Update a Booking ", description = "Updates a booking present in the database for the given Id, " +
            "else throw exception.")
    public Booking updateBooking(@RequestBody Booking booking) {
        return bookingService.updateBooking(booking);
    }

}
