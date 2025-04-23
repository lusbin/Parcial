package co.edu.udes.activity.backend.demo.repositories;

import co.edu.udes.activity.backend.demo.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomerId(Long customerId);
    List<Reservation> findByRoomId(Long roomId);

    // Método para obtener reservas recientes
    List<Reservation> findByStartDateAfter(LocalDate startDate);
}