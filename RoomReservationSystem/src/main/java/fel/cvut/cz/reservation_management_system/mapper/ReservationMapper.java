package fel.cvut.cz.reservation_management_system.mapper;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;
import fel.cvut.cz.reservation_management_system.entity.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDto entityToDto(Reservation reservation);

    Reservation dtoToEntity(ReservationDto reservationDto);
}
