package com.assignment.ticket_management_service.repository;

import com.assignment.ticket_management_service.entity.Screen;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {

    Optional<Screen> findByName(@NonNull String name);
}
