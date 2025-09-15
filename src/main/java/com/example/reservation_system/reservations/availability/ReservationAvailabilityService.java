package com.example.reservation_system.reservations.availability;

import com.example.reservation_system.reservations.ReservationRepository;
import com.example.reservation_system.reservations.ReservationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationAvailabilityService {
    private final ReservationRepository repository;

    private static final Logger log = LoggerFactory.getLogger(ReservationAvailabilityService.class);


    public ReservationAvailabilityService(ReservationRepository repository) {
        this.repository = repository;
    }


    public boolean isReservationAvailable(Long roomId, LocalDate startDate, LocalDate endDate) {

        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("Start date must be 1 day earlier than end date");
        }
        List<Long> conflictingIds = repository.findConflictReservationIds(roomId, startDate, endDate, ReservationStatus.APPROVED);
        if (conflictingIds.isEmpty()) {
            return true;
        }
        log.info("Conflicting with: ids={}", conflictingIds);

        return false;
    }
}
