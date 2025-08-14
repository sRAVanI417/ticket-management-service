package com.assignment.ticket_management_service.controller;

import com.assignment.ticket_management_service.entity.Showtime;
import com.assignment.ticket_management_service.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @PostMapping
    public Showtime addShowtime(@RequestBody Showtime showtime) {
        return showtimeService.addShowtime(showtime);
    }

    @GetMapping("/{id}/availability")
    public int checkAvailability(@PathVariable Long id) {
        return showtimeService.checkAvailability(id);
    }

}
