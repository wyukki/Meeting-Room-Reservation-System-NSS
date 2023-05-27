package fel.cvut.cz.room_management_system.service.impl;

import fel.cvut.cz.room_management_system.dto.PagedResponse;
import fel.cvut.cz.room_management_system.dto.RoomDashboardDTO;
import fel.cvut.cz.room_management_system.entity.Room;
import fel.cvut.cz.room_management_system.mapper.RoomDashboardMapper;
import fel.cvut.cz.room_management_system.repository.RoomRepository;
import fel.cvut.cz.room_management_system.service.RoomDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomDashboardServiceImpl implements RoomDashboardService {
    private final RoomRepository repository;
    private final RoomDashboardMapper mapper;

    @Autowired
    public RoomDashboardServiceImpl(RoomRepository repository, RoomDashboardMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PagedResponse<RoomDashboardDTO> getRoomsForDashboard(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Room> pagedResult = repository.findAll(paging);

        PagedResponse<RoomDashboardDTO> response = new PagedResponse<>();

        List<RoomDashboardDTO> roomsForDashboard = new ArrayList<>();

        if (pagedResult.hasContent()) {
            roomsForDashboard = pagedResult.getContent().stream().map(mapper::roomToDashboardDTO).collect(Collectors.toList());
        }

        response.setObjectList(roomsForDashboard);
        response.setCurrentPageNumber(pageNo);
        response.setTotalPageCount(pagedResult.getTotalPages());

        return response;
    }
}
