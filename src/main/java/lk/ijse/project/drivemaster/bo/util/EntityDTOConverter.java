package lk.ijse.project.drivemaster.bo.util;

import lk.ijse.project.drivemaster.dto.UserDTO;
import lk.ijse.project.drivemaster.entity.User;
import lk.ijse.project.drivemaster.enums.Role;

public class EntityDTOConverter {
    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(String.valueOf((user.getRole())));
        userDTO.setActive(user.isActive());
        return userDTO;
    }
    public User getUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setActive(dto.isActive());
        return user;
    }
    }
