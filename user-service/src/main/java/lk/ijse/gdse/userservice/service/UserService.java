package lk.ijse.gdse.userservice.service;

import lk.ijse.gdse.userservice.dto.UserDTO;

import java.util.List;

public interface UserService  {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(String id, UserDTO userDTO);
    void deleteUser(String id);
     boolean existsById(String id);
}
