package com.assignment.ticket_management_service.service;

import com.assignment.ticket_management_service.entity.Movie;
import com.assignment.ticket_management_service.entity.Screen;
import com.assignment.ticket_management_service.exception.ResourceNotFoundException;
import com.assignment.ticket_management_service.exception.ScreenRegistrationException;
import com.assignment.ticket_management_service.model.ScreenRegisterRequest;
import com.assignment.ticket_management_service.repository.MovieRepository;
import com.assignment.ticket_management_service.repository.ScreenRepository;
import com.assignment.ticket_management_service.repository.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScreenServiceTest {

    @Mock
    private ScreenRepository screenRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private ScreenService screenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerScreen_Success() {
        ScreenRegisterRequest request = new ScreenRegisterRequest(
                "Audi 1", 120, 200,
                1, LocalDate.now(), LocalDate.now().plusDays(1)
        );

        when(screenRepository.findByName("Audi 1")).thenReturn(Optional.empty());
        when(movieRepository.findById(1)).thenReturn(Optional.of(new Movie()));
        when(screenRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Screen result = screenService.registerScreen(request);

        assertEquals("Audi 1", result.getName());
        verify(screenRepository).save(any(Screen.class));
        verify(showtimeRepository).saveAll(anyList());
    }

    @Test
    void registerScreen_DuplicateName() {
        when(screenRepository.findByName("Audi 1")).thenReturn(Optional.of(new Screen()));

        ScreenRegisterRequest request = new ScreenRegisterRequest("Audi 1", 120, 200, 1, LocalDate.now(), LocalDate.now());

        assertThrows(ScreenRegistrationException.class, () -> screenService.registerScreen(request));
    }

    @Test
    void registerScreen_MovieNotFound() {
        when(screenRepository.findByName("Audi 1")).thenReturn(Optional.empty());
        when(movieRepository.findById(1)).thenReturn(Optional.empty());

        ScreenRegisterRequest request = new ScreenRegisterRequest("Audi 1", 120, 200, 1, LocalDate.now(), LocalDate.now());

        assertThrows(ResourceNotFoundException.class, () -> screenService.registerScreen(request));
    }

    @Test
    void deleteScreen_Success() {
        when(screenRepository.existsById(1)).thenReturn(true);

        String result = screenService.deleteScreen(1);

        assertEquals("Screen removed Successfully", result);
        verify(showtimeRepository).deleteByScreenId(1);
        verify(screenRepository).deleteById(1);
    }

    @Test
    void deleteScreen_NotFound() {
        when(screenRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> screenService.deleteScreen(1));
    }
}
