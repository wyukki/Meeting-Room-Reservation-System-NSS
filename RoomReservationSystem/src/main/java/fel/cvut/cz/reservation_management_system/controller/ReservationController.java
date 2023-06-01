package fel.cvut.cz.reservation_management_system.controller;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;
import fel.cvut.cz.reservation_management_system.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/reservations/{id}")
    public ResponseEntity<List<ReservationDto>> getRoomReservationsById(@PathVariable Long id) {
        return new ResponseEntity<>(reservationService.findAllReservationsByRoomId(id), HttpStatus.OK);
    }

}
