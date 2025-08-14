package com.assignment.ticket_management_service.service;

import com.assignment.ticket_management_service.entity.Showtime;
import com.assignment.ticket_management_service.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ShowtimeService {

    @Autowired
    ShowtimeRepository showtimeRepository;
    public Showtime addShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    public int checkAvailability(@PathVariable Long id) {
        return showtimeRepository.findById(id).map(Showtime::getAvailableSeats).orElse(0);
    }
}
