package com.wipro.Service;

import com.wipro.Repository.BookingRepo;
import com.wipro.dto.Payment;
import com.wipro.dto.Property;
import com.wipro.entity.Booking;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    RestTemplate restTemplate;

    public Booking saveBooking(Booking booking) {

        System.out.println("Booking Object : " + booking.toString());
        Property property = getPropertyById(booking.getPropertyId());

        if (property != null && property.getAvailableRooms() >= booking.getNumRooms() && !property.isLockForBook()) {
            property.setLockForBook(true);
            updateProperty(property);

            booking.setBookingStatus("Initiated");
            Booking initialBooking = bookingRepo.save(booking);
            System.out.println("Initial Booking Object : " + initialBooking);

            Payment payment = new Payment();
            payment.setBookingId(initialBooking.getId());
            payment.setAmount(String.valueOf(initialBooking.getNumRooms() * property.getPrice()));
            Payment createdPayment = createPayment(payment);

            if (createdPayment != null) {
                property.setAvailableRooms((property.getAvailableRooms() - booking.getNumRooms()));

                booking.setBookingDescription("Payment is successful...Booked a property with id : " + booking.getPropertyId());
                booking.setBookingStatus("Booked");
            } else {
                booking.setBookingDescription("Payment is not successful..So booking is failed");
                booking.setBookingStatus("Failed");
            }
            property.setLockForBook(false);
            updateProperty(property);
        } else {
            booking.setBookingDescription("No property found with the given property id : " + booking.getPropertyId()
                    + " or Booking is being done on the property.");
            booking.setBookingStatus("Failed");
            System.out.println("Booking object to be updated : " + booking);
            throw new RuntimeException(booking.getBookingDescription());
        }
        return updateBooking(booking);
    }

    public Property getPropertyById(int propertyId) {
        String url = "https://PROPERTY/property/{id}";
        System.out.println("Getting property for given id : " + propertyId);
        try {
            ResponseEntity<Property> response = restTemplate.getForEntity(url, Property.class, propertyId);
            System.out.println("Property creation response : " + response.getStatusCode().value());
            if (response.getStatusCode().value() == 200 && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            System.out.println("Error occurred in property : " + e.getMessage());
        }
        return null;
    }

    public void updateProperty(Property property) {
        String updatePropertyUrl = "https://PROPERTY/property";
        restTemplate.put(updatePropertyUrl, property);
    }

    public Payment createPayment(Payment payment) {
        String savePaymentUrl = "https://PAYMENT/pay";
        ResponseEntity<Payment> response = restTemplate.postForEntity(savePaymentUrl, payment, Payment.class);
        if (response.getStatusCode().value() == 200 && response.getBody() != null) {
            return response.getBody();
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public Optional<Booking> getBookingById(int id) {
        return bookingRepo.findById(id);
    }

    public void deleteBooking(int id) {
        bookingRepo.deleteById(id);
    }

    public Booking updateBooking(Booking booking) {

        Optional<Booking> existingBooking = bookingRepo.findById(booking.getId());
        if (existingBooking.isPresent()) {
            if (booking.getBookingDescription() != null)
                existingBooking.get().setBookingDescription(booking.getBookingDescription());
            if (booking.getNumRooms() != null)
                existingBooking.get().setNumRooms(booking.getNumRooms());
            if (booking.getOrderNumber() != null)
                existingBooking.get().setOrderNumber(booking.getOrderNumber());
            if (booking.getBookingStatus() != null)
                existingBooking.get().setBookingStatus(booking.getBookingStatus());
            if (booking.getPropertyId() != null)
                existingBooking.get().setPropertyId(booking.getPropertyId());
            System.out.println("Booking to be updated : " + existingBooking.get());
            bookingRepo.save(existingBooking.get());
            return existingBooking.get();
        } else {
            throw new RuntimeException("Booking not found for the given id : " + booking.getId());
        }
    }

}
