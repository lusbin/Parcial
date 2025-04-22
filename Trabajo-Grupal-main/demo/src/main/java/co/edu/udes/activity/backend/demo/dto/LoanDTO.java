package co.edu.udes.activity.backend.demo.dto;

import co.edu.udes.activity.backend.demo.models.Material;
import co.edu.udes.activity.backend.demo.models.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class LoanDTO {

    private long id;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private User user;
    private Set<Material> materials;
    private String status;

}
