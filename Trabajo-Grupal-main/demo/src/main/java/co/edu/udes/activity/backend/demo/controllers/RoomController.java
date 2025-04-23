package co.edu.udes.activity.backend.demo.controllers;

import co.edu.udes.activity.backend.demo.dto.RoomDTO;
import co.edu.udes.activity.backend.demo.models.Room;
import co.edu.udes.activity.backend.demo.services.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO dto) {
        Room room = roomService.createRoom(dto.getType(), dto.getPrice(), dto.getCapacity());
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(room, RoomDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(room -> ResponseEntity.ok(modelMapper.map(room, RoomDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms()
                .stream()
                .map(room -> modelMapper.map(room, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO dto) {
        return roomService.updateRoom(id, dto.getType(), dto.getPrice(), dto.getCapacity())
                .map(room -> ResponseEntity.ok(modelMapper.map(room, RoomDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        boolean deleted = roomService.deleteRoom(id);
        return deleted ?
                ResponseEntity.ok("Room deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found with ID: " + id);
    }
}