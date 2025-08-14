package com.assignment.ticket_management_service.controller;

import com.assignment.ticket_management_service.entity.Booking;
import com.assignment.ticket_management_service.model.BookingRequest;
import com.assignment.ticket_management_service.projections.PeakBookingHourProjection;
import com.assignment.ticket_management_service.projections.TopShowtimeProjection;
import com.assignment.ticket_management_service.service.BookingService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin
public class BookingController {

    @Autowired private BookingService bookingService;

    @PostMapping
    public String bookSeats(@RequestBody BookingRequest bookingRequest) throws BadRequestException {
        return bookingService.bookSeats(bookingRequest);
    }

    @DeleteMapping("/{id}")
    public String cancel(@PathVariable Integer id) {
        return bookingService.cancelBooking(id);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/users/{username}")
    public List<Booking> getBookingsByUser(@PathVariable String username) { return bookingService.getBookingsByUser(username); }

    @GetMapping("/analytics/popular-showtimes")
    public List<TopShowtimeProjection> getPopularShowtimes(@RequestParam(defaultValue = "5") int limit) { return bookingService.getPopularShowtimes(limit); }

    @GetMapping("/analytics/peak-hours")
    public List<PeakBookingHourProjection> getPeakBookingHours() {
        return bookingService.getPeakBookingHours();
    }
}
