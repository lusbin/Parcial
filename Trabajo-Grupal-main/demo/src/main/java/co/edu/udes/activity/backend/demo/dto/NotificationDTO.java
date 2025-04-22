package co.edu.udes.activity.backend.demo.dto;

import co.edu.udes.activity.backend.demo.models.Messaging;
import co.edu.udes.activity.backend.demo.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {

    private long id;
    private LocalDateTime sendDate;
    private boolean read;
    private String messageType;
    private Messaging messaging;
    private User receiver;
}
