package com.assignment.ticket_management_service.service;

import com.assignment.ticket_management_service.entity.Movie;
import com.assignment.ticket_management_service.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }
}
