package co.edu.udes.activity.backend.demo.controllers;

import co.edu.udes.activity.backend.demo.dto.ReservationDTO;
import co.edu.udes.activity.backend.demo.models.Reservation;
import co.edu.udes.activity.backend.demo.services.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO dto) {
        Reservation reservation = reservationService.createReservation(dto.getCustomerId(), dto.getRoomId(), dto.getStartDate(), dto.getEndDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(reservation, ReservationDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(reservation -> ResponseEntity.ok(modelMapper.map(reservation, ReservationDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public List<ReservationDTO> getReservationsByCustomer(@PathVariable Long customerId) {
        return reservationService.getReservationsByCustomer(customerId)
                .stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/room/{roomId}")
    public List<ReservationDTO> getReservationsByRoom(@PathVariable Long roomId) {
        return reservationService.getReservationsByRoom(roomId)
                .stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservationDates(@PathVariable Long id, @RequestBody ReservationDTO dto) {
        return reservationService.updateReservationDates(id, dto.getStartDate(), dto.getEndDate())
                .map(reservation -> ResponseEntity.ok(modelMapper.map(reservation, ReservationDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {
        boolean cancelled = reservationService.cancelReservation(id);
        return cancelled ?
                ResponseEntity.ok("Reservation cancelled successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found with ID: " + id);
    }

// Funcionalidad Avanzada 1: Verificar la disponibilidad de una habitación
    @GetMapping("/check-availability")
public ResponseEntity<Boolean> checkRoomAvailability(@RequestParam Long roomId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
    boolean isAvailable = reservationService.checkRoomAvailability(roomId, startDate, endDate);
    return ResponseEntity.ok(isAvailable);
}

// Funcionalidad Avanzada 2: Obtener habitaciones disponibles dentro de un rango de fechas y con capacidad específica
@GetMapping("/available-rooms")
public List<ReservationDTO> getAvailableRooms(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int capacity) {
    return reservationService.getAvailableRooms(startDate, endDate, capacity)
            .stream()
            .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
            .collect(Collectors.toList());
}

// Funcionalidad Avanzada 3: Listar reservas recientes realizadas en los últimos "X" días
@GetMapping("/recent-reservations")
public List<ReservationDTO> getRecentReservations(@RequestParam int days) {
    return reservationService.getRecentReservations(days)
            .stream()
            .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
            .collect(Collectors.toList());
}

// Funcionalidad Avanzada 4: Calcular el costo total de una reserva
@GetMapping("/reservation-cost/{reservationId}")
public ResponseEntity<Double> calculateReservationCost(@PathVariable Long reservationId) {
    double cost = reservationService.calculateReservationCost(reservationId);
    return ResponseEntity.ok(cost);
}




}