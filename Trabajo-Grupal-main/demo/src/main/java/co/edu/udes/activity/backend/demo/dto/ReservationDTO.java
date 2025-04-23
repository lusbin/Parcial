package co.edu.udes.activity.backend.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationDTO {
    private Long id;
    private Long customerId;
    private Long roomId;
    private LocalDate startDate;
    private LocalDate endDate;
}