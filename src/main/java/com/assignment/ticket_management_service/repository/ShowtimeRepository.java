package com.assignment.ticket_management_service.repository;

import com.assignment.ticket_management_service.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    void deleteByScreenId(Integer screenId);
}
