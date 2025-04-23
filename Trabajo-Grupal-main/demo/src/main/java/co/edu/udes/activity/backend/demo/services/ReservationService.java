package co.edu.udes.activity.backend.demo.services;

import co.edu.udes.activity.backend.demo.models.Reservation;
import co.edu.udes.activity.backend.demo.models.Room;
import co.edu.udes.activity.backend.demo.repositories.ReservationRepository;
import co.edu.udes.activity.backend.demo.repositories.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

  
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomService roomService;

    public Reservation createReservation(Long customerId, Long roomId, LocalDate startDate, LocalDate endDate) {
        Reservation reservation = new Reservation();
        reservation.setCustomer(customerService.getCustomerById(customerId).orElseThrow(() -> new RuntimeException("Customer not found")));
        reservation.setRoom(roomService.getRoomById(roomId).orElseThrow(() -> new RuntimeException("Room not found")));
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public List<Reservation> getReservationsByCustomer(Long customerId) {
        return reservationRepository.findByCustomerId(customerId);
    }

    public List<Reservation> getReservationsByRoom(Long roomId) {
        return reservationRepository.findByRoomId(roomId);
    }

    public Optional<Reservation> updateReservationDates(Long reservationId, LocalDate newStartDate, LocalDate newEndDate) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        reservation.ifPresent(res -> {
            res.setStartDate(newStartDate);
            res.setEndDate(newEndDate);
            reservationRepository.save(res);
        });
        return reservation;
    }

    public boolean cancelReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return true;
        }
        return false;
    }


    // Funcionalidades avanzadass
    public boolean checkRoomAvailability(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.findByRoomId(roomId);
        for (Reservation reservation : reservations) {
            if ((startDate.isBefore(reservation.getEndDate()) && endDate.isAfter(reservation.getStartDate())) || 
                (startDate.isEqual(reservation.getStartDate()) || endDate.isEqual(reservation.getEndDate()))) {
                return false; // La habitación ya está reservada en ese rango de fechas
            }
        }
        return true; 
    }

    // Obtener habitaciones disponibles en un rango de fechas 
    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate, int capacity) {
        List<Room> allRooms = roomRepository.findAll();
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : allRooms) {
            if (room.getCapacity() >= capacity && checkRoomAvailability(room.getId(), startDate, endDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Listar reservas realizadas en los últimos "X" días
    public List<Reservation> getRecentReservations(int days) {
        LocalDate startDate = LocalDate.now().minusDays(days);
        return reservationRepository.findByStartDateAfter(startDate);
    }

    // Calcular el costo total de una reserva
    public double calculateReservationCost(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            Reservation res = reservation.get();
            long daysBetween = res.getStartDate().until(res.getEndDate(), java.time.temporal.ChronoUnit.DAYS);
            double roomPrice = res.getRoom().getPrice(); // Precio de la habitación
            return roomPrice * daysBetween; // Costo total
        }
        throw new RuntimeException("Reservation not found");
    }




}