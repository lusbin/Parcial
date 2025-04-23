package co.edu.udes.activity.backend.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private double price;

    @Column(name = "capacity")
    private int capacity;

    public Room() {}
}