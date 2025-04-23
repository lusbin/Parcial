package co.edu.udes.activity.backend.demo.services;

import co.edu.udes.activity.backend.demo.models.Room;
import co.edu.udes.activity.backend.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room createRoom(String type, double price, int capacity) {
        Room room = new Room();
        room.setType(type);
        room.setPrice(price);
        room.setCapacity(capacity);
        return roomRepository.save(room);
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> updateRoom(Long id, String type, double price, int capacity) {
        return roomRepository.findById(id).map(room -> {
            room.setType(type);
            room.setPrice(price);
            room.setCapacity(capacity);
            return roomRepository.save(room);
        });
    }

    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}