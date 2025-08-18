package com.assignment.ticket_management_service.service;

import com.assignment.ticket_management_service.entity.Showtime;
import com.assignment.ticket_management_service.repository.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ShowtimeServiceTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private ShowtimeService showtimeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addShowtime() {
        Showtime showtime = new Showtime();
        showtime.setAvailableSeats(100);

        when(showtimeRepository.save(showtime)).thenReturn(showtime);

        Showtime result = showtimeService.addShowtime(showtime);

        assertEquals(100, result.getAvailableSeats());
        verify(showtimeRepository).save(showtime);
    }

    @Test
    void checkAvailability_Found() {
        Showtime showtime = new Showtime();
        showtime.setAvailableSeats(50);

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));

        int result = showtimeService.checkAvailability(1L);

        assertEquals(50, result);
    }

    @Test
    void checkAvailability_NotFound() {
        when(showtimeRepository.findById(1L)).thenReturn(Optional.empty());

        int result = showtimeService.checkAvailability(1L);

        assertEquals(0, result);
    }
}
