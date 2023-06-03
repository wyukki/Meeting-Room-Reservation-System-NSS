package fel.cvut.cz.reservation_management_system.service.impl;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;
import fel.cvut.cz.reservation_management_system.dto.ReservationRequest;
import fel.cvut.cz.reservation_management_system.dto.ReservationWithNameDto;
import fel.cvut.cz.reservation_management_system.entity.Reservation;
import fel.cvut.cz.reservation_management_system.enums.RecurrentEnum;
import fel.cvut.cz.reservation_management_system.repository.ReservationRepository;
import fel.cvut.cz.reservation_management_system.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ReservationServiceImplTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void getAllReservationsByRoomId_testOk() {
        Long roomId = 14L;

        List<ReservationWithNameDto> reservations = reservationService.getAllReservationsByRoomId(roomId);

        Assertions.assertNotNull(reservations);
        Assertions.assertEquals(11, reservations.size());
    }

    @Test
    public void deleteReservation_testOk() {
        Long reservationId = 1L;
        reservationService.deleteReservation(reservationId);

        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        Assertions.assertEquals(Optional.empty(), reservation);
    }

    @Test
    public void getReservationsByUserId_testOk() {
        Long userId = 1L;

        List<ReservationDto> reservations = reservationService.getReservationByUserId(userId);

        Assertions.assertNotNull(reservations);
        Assertions.assertEquals(3, reservations.size());
    }

    @Test
    public void createReservation_testOk() {
        boolean reservationCreated = reservationService.createReservation(new ReservationRequest(
                1L,
                14L,
                "danilrom@fel.cvut.cz",
                ZonedDateTime.now().plusMonths(3),
                ZonedDateTime.now().plusMonths(3).plusHours(1),
                RecurrentEnum.DAY));

        Assertions.assertTrue(reservationCreated);
    }

}