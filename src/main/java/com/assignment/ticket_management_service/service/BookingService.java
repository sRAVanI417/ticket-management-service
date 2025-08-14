package com.assignment.ticket_management_service.service;

import com.assignment.ticket_management_service.entity.Booking;
import com.assignment.ticket_management_service.entity.Showtime;
import com.assignment.ticket_management_service.exception.ResourceNotFoundException;
import com.assignment.ticket_management_service.model.BookingRequest;
import com.assignment.ticket_management_service.projections.PeakBookingHourProjection;
import com.assignment.ticket_management_service.projections.TopShowtimeProjection;
import com.assignment.ticket_management_service.repository.BookingRepository;
import com.assignment.ticket_management_service.repository.ShowtimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public String bookSeats(BookingRequest bookingRequest){
        Showtime showtime = showtimeRepository.findById(bookingRequest.getShowtimeId()).orElseThrow(() -> new ResourceNotFoundException("Invalid showtime ID"));

        if (showtime.getAvailableSeats() < bookingRequest.getSeatCount()) {
            return "Insufficient availability";
        }
        showtime.setAvailableSeats(showtime.getAvailableSeats() - bookingRequest.getSeatCount());
        showtimeRepository.save(showtime);

        Booking booking = new Booking(null, showtime, bookingRequest.getUserName(), bookingRequest.getSeatCount(), LocalDateTime.now());
        bookingRepository.save(booking);
        return "Seats booked";
    }

    @Transactional
    public String cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Showtime showtime = booking.getShowtime();

        showtime.setAvailableSeats(showtime.getAvailableSeats() + booking.getSeatCount());
        showtimeRepository.save(showtime);
        bookingRepository.delete(booking);

        return "Booking cancelled";
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(@PathVariable String username) {
        return bookingRepository.findByUserNameIgnoreCase(username);
    }

    public List<TopShowtimeProjection> getPopularShowtimes(@RequestParam(defaultValue = "5") int limit) {
        return bookingRepository.findTopShowtimes(limit);
    }

    public List<PeakBookingHourProjection> getPeakBookingHours() {
        return bookingRepository.findPeakBookingHours();
    }

}
