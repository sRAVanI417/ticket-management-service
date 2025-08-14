package com.assignment.ticket_management_service.model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenRegisterRequest {

    @NonNull
    private String name;

    @NonNull
    private Integer seatingCapacity;

    @NonNull
    private Integer price;

    @NonNull
    private Integer movieId;

    @NonNull
    private LocalDate movieStartDate;

    @NonNull
    private LocalDate movieEndDate;
}
