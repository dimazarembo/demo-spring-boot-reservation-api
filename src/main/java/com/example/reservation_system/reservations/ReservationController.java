package com.example.reservation_system.reservations;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        log.info("Called getReservationById: id {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(
            @RequestParam(value = "roomId", required = false) Long roomId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        log.info("Called getAllReservations");
        var filter = new ReservationSearchFilter(roomId, userId, pageSize, pageNumber);
        return ResponseEntity.ok(reservationService.searchAllByFilter(filter));

    }


    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservationCreate) {
        log.info("Called createReservation");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(reservationCreate));

    }


    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
                                                         @Valid @RequestBody Reservation reservationToUpdate) {
        log.info("Called updateReservation id {} reservation: {}", id, reservationToUpdate);
        var updateReservation = reservationService.updateReservation(id, reservationToUpdate);
        return ResponseEntity.ok(updateReservation);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.info("Called deleteReservation id {} ", id);

        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Reservation> approveReservation(@PathVariable Long id) {
        log.info("Called approveReservation id {} ", id);
        var reservation = reservationService.approveReservation(id);
        return ResponseEntity.ok(reservation);
    }
}
