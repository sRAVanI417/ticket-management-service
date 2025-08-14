package com.assignment.ticket_management_service.service;

import com.assignment.ticket_management_service.entity.Movie;
import com.assignment.ticket_management_service.entity.Screen;
import com.assignment.ticket_management_service.entity.Showtime;
import com.assignment.ticket_management_service.exception.ResourceNotFoundException;
import com.assignment.ticket_management_service.exception.ScreenRegistrationException;
import com.assignment.ticket_management_service.model.ScreenRegisterRequest;
import com.assignment.ticket_management_service.repository.MovieRepository;
import com.assignment.ticket_management_service.repository.ScreenRepository;
import com.assignment.ticket_management_service.repository.ShowtimeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;

    // A predefined list of daily show times. This could be made configurable in the future.
    private static final List<LocalTime> DAILY_SHOW_TIMES = List.of(
            LocalTime.of(10, 0),
            LocalTime.of(14, 0),
            LocalTime.of(18, 0),
            LocalTime.of(22, 0)
    );

    public ScreenService(ScreenRepository screenRepository,
                         MovieRepository movieRepository,
                         ShowtimeRepository showtimeRepository) {
        this.screenRepository = screenRepository;
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
    }

    @Transactional
    public Screen registerScreen(@NonNull ScreenRegisterRequest screenRegisterRequest) {

        // 1. Validate that a screen with the same name doesn't already exist.
        screenRepository.findByName(screenRegisterRequest.getName()).ifPresent(screen -> {
            throw new ScreenRegistrationException("A screen with the name '" + screenRegisterRequest.getName() + "' already exists.");
        });

        // 2. Validate that the movie exists.
        Movie movie = movieRepository.findById(screenRegisterRequest.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + screenRegisterRequest.getMovieId()));

        // 3. Create and save the new Screen entity.
        Screen newScreen = Screen.builder()
                .name(screenRegisterRequest.getName())
                .seatingCapacity(screenRegisterRequest.getSeatingCapacity())
                .price(screenRegisterRequest.getPrice())
                .build();
        Screen savedScreen = screenRepository.save(newScreen);

        // 4. Generate and save Showtime records for the entire duration.
        generateShowtimesForScreen(savedScreen, movie, screenRegisterRequest.getMovieStartDate(), screenRegisterRequest.getMovieEndDate());

        // 5. Return the newly created screen.
        return savedScreen;
    }

    private void generateShowtimesForScreen(Screen screen, Movie movie, LocalDate startDate, LocalDate endDate) {
        List<Showtime> showtimesToCreate = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            for (LocalTime showTime : DAILY_SHOW_TIMES) {
                Showtime showtime = new Showtime();
                showtime.setScreen(screen);
                showtime.setMovie(movie);
                showtime.setStartTime(LocalDateTime.of(currentDate, showTime));
                showtime.setAvailableSeats(screen.getSeatingCapacity());
                showtimesToCreate.add(showtime);
            }
            currentDate = currentDate.plusDays(1);
        }

        if (!showtimesToCreate.isEmpty()) {
            showtimeRepository.saveAll(showtimesToCreate);
        }
    }

    @Transactional
    public String deleteScreen(Integer screenId) {
        // 1. Verify the screen exists before attempting to delete.
        if (!screenRepository.existsById(screenId)) {
            throw new ResourceNotFoundException("Cannot delete. Screen not found with ID: " + screenId);
        }

        // 2. Delete all showtimes associated with this screen.
        showtimeRepository.deleteByScreenId(screenId);

        // 3. Finally, delete the screen itself.
        screenRepository.deleteById(screenId);
        return "Screen removed Successfully";
    }
}
