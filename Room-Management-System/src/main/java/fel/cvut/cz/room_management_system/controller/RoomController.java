package fel.cvut.cz.room_management_system.controller;

import fel.cvut.cz.room_management_system.dto.PagedResponse;
import fel.cvut.cz.room_management_system.dto.RoomDTO;
import fel.cvut.cz.room_management_system.dto.RoomDashboardDTO;
import fel.cvut.cz.room_management_system.exceptions.NotFoundException;
import fel.cvut.cz.room_management_system.service.RoomDashboardService;
import fel.cvut.cz.room_management_system.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomDashboardService dashboardService;
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomDashboardService dashboardService, RoomService roomService) {
        this.dashboardService = dashboardService;
        this.roomService = roomService;
    }

    @GetMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResponse<RoomDashboardDTO>> getRoomsForDashboard(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(dashboardService.getRoomsForDashboard(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(roomService.getRoomById(id), HttpStatus.OK);
    }
}
