package fel.cvut.cz.room_management_system.controller;

import fel.cvut.cz.room_management_system.dto.BuildingDTO;
import fel.cvut.cz.room_management_system.exceptions.NotFoundException;
import fel.cvut.cz.room_management_system.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuildingDTO> getRoomsByBuildingId(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(buildingService.getAllRoomsByBuildingId(id), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BuildingDTO>> getBuildings() {
        return new ResponseEntity<>(buildingService.getAllBuildings(), HttpStatus.OK);
    }
}
