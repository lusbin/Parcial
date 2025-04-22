package co.edu.udes.activity.backend.demo.dto;

import co.edu.udes.activity.backend.demo.models.Permission;
import lombok.Data;

import java.util.Set;
@Data
public class RoleDTO {

    private String name;
    private String description;
    private Set<Permission> permissions;
}
