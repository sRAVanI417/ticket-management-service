package com.assignment.ticket_management_service.service;

import com.assignment.ticket_management_service.entity.Booking;
import com.assignment.ticket_management_service.entity.Showtime;
import com.assignment.ticket_management_service.exception.ResourceNotFoundException;
import com.assignment.ticket_management_service.model.BookingRequest;
import com.assignment.ticket_management_service.projections.PeakBookingHourProjection;
import com.assignment.ticket_management_service.projections.TopShowtimeProjection;
import com.assignment.ticket_management_service.repository.BookingRepository;
import com.assignment.ticket_management_service.repository.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private ShowtimeRepository showtimeRepository;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Showtime showtime;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        showtime = new Showtime();
        showtime.setId(1L);
        showtime.setAvailableSeats(10);
    }

    @Test
    void bookSeats_Success() {
        BookingRequest request = new BookingRequest(1L, "John", 2);

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(showtimeRepository.save(any())).thenReturn(showtime);
        when(bookingRepository.save(any())).thenReturn(new Booking());

        String result = bookingService.bookSeats(request);

        assertEquals("Seats booked", result);
        verify(showtimeRepository).save(showtime);
        verify(bookingRepository).save(any());
        assertEquals(8, showtime.getAvailableSeats());
    }

    @Test
    void bookSeats_InsufficientSeats() {
        BookingRequest request = new BookingRequest(1L, "John", 20);
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));

        String result = bookingService.bookSeats(request);

        assertEquals("Insufficient availability", result);
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void bookSeats_InvalidShowtimeId() {
        BookingRequest request = new BookingRequest(99L, "John", 2);
        when(showtimeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.bookSeats(request));
    }

    @Test
    void cancelBooking_Success() {
        Booking booking = new Booking(1L, showtime, "John", 2, LocalDateTime.now());
        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        String result = bookingService.cancelBooking(1);

        assertEquals("Booking cancelled", result);
        assertEquals(12, showtime.getAvailableSeats());
        verify(showtimeRepository).save(showtime);
        verify(bookingRepository).delete(booking);
    }

    @Test
    void getAllBookings() {
        when(bookingRepository.findAll()).thenReturn(List.of(new Booking()));
        assertEquals(1, bookingService.getAllBookings().size());
    }

    @Test
    void getBookingsByUser() {
        when(bookingRepository.findByUserNameIgnoreCase("John")).thenReturn(List.of(new Booking()));
        assertEquals(1, bookingService.getBookingsByUser("John").size());
    }

    @Test
    void getPopularShowtimes() {
        when(bookingRepository.findTopShowtimes(5)).thenReturn(List.of(mock(TopShowtimeProjection.class)));
        assertEquals(1, bookingService.getPopularShowtimes(5).size());
    }

    @Test
    void getPeakBookingHours() {
        when(bookingRepository.findPeakBookingHours()).thenReturn(List.of(mock(PeakBookingHourProjection.class)));
        assertEquals(1, bookingService.getPeakBookingHours().size());
    }
}
