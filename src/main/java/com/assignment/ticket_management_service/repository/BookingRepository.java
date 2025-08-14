package com.assignment.ticket_management_service.repository;

import com.assignment.ticket_management_service.entity.Booking;
import com.assignment.ticket_management_service.projections.PeakBookingHourProjection;
import com.assignment.ticket_management_service.projections.TopShowtimeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserNameIgnoreCase(String username);

    @Query(value = "SELECT b.showtime_id AS showtimeId, COUNT(b.id) AS totalBookings " +
            "FROM booking b " +
            "GROUP BY b.showtime_id " +
            "ORDER BY totalBookings DESC " +
            "LIMIT :limit",
            nativeQuery = true)
    List<TopShowtimeProjection> findTopShowtimes(int limit);


    @Query(value = "SELECT EXTRACT(HOUR FROM booked_at) AS hour, COUNT(id) AS noOfBookings " +
            "FROM booking " +
            "GROUP BY hour " +
            "ORDER BY count DESC",
            nativeQuery = true)
    List<PeakBookingHourProjection> findPeakBookingHours();

}
