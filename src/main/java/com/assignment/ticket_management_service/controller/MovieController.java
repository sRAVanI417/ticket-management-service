package com.assignment.ticket_management_service.controller;

import com.assignment.ticket_management_service.entity.Movie;
import com.assignment.ticket_management_service.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@CrossOrigin
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

}
