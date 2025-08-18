package com.assignment.ticket_management_service.service;

import com.assignment.ticket_management_service.entity.Movie;
import com.assignment.ticket_management_service.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addMovie() {
        Movie movie = new Movie();
        movie.setTitle("Avengers");

        when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.addMovie(movie);

        assertEquals("Avengers", result.getTitle());
        verify(movieRepository).save(movie);
    }

    @Test
    void getMovies() {
        when(movieRepository.findAll()).thenReturn(List.of(new Movie()));
        assertEquals(1, movieService.getMovies().size());
    }
}
