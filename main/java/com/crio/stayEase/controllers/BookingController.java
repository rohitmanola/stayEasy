package com.org.StayEase.controllers;

import com.org.StayEase.entities.Booking;
import com.org.StayEase.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Endpoint to book a room in a hotel, accessible by CUSTOMER, MANAGER, and
    // ADMIN roles

    @PostMapping("/hotels/{hotelId}/book")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER','ADMIN')")
    public ResponseEntity<Booking> bookRoom(@PathVariable Long hotelId) {
        Booking booking = bookingService.bookRoom(hotelId);
        return ResponseEntity.ok().body(booking);
    }

    // Endpoint to cancel a booking, accessible only by MANAGER role

    @DeleteMapping("/bookings/{bookingId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("The booking (Booking Id " + bookingId + ") has been cancelled successfully");
    }

    // Endpoint to retrieve all bookings, accessible by MANAGER and ADMIN roles

    @GetMapping("/bookings")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok().body(bookingService.getAllBookings());
    }

    // Endpoint to retrieve a booking by ID, accessible by MANAGER and ADMIN roles

    @GetMapping("/bookings/{bookingId}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        return ResponseEntity.ok().body(bookingService.getBookingById(bookingId));
    }
}