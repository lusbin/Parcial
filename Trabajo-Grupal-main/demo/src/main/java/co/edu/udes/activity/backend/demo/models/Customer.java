package co.edu.udes.activity.backend.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;
}