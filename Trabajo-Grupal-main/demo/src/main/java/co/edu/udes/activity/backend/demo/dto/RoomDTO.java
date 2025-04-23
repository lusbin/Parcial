package co.edu.udes.activity.backend.demo.dto;

import lombok.Data;

@Data
public class RoomDTO {
    private Long id;
    private String type;
    private double price;
    private int capacity;
}