package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.UserBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.UserDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.UserDTO;
import lk.ijse.project.drivemaster.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.USER);
    private final EntityDTOConverter converter = new EntityDTOConverter();


    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        List<User> users = userDAO.getAll();
        List<UserDTO> customerDTOS = new ArrayList<>();
        for (User user : users) {
            customerDTOS.add(converter.getUserDTO(user));
        }
        return customerDTOS;
    }

    @Override
    public boolean saveUser(UserDTO dto) throws Exception {
        Optional<User> optionalUser = userDAO.findById(dto.getId());
        if (optionalUser.isPresent()) {
            throw new DuplicateException("Duplicate customer id");
        }
        User user = converter.getUser(dto);
        return userDAO.save(user);
    }

    @Override
    public boolean updateUser(UserDTO dto) throws Exception {
        Optional<User> optionalUser = userDAO.findById(dto.getId());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = converter.getUser(dto);
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String id) throws Exception {
        Optional<User> optionalUser = userDAO.findById(Long.valueOf(id));
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Customer not found..!");
        }
        return userDAO.delete(id);
    }

    @Override
    public UserDTO searchUser(String userName, String password) {

            try {
                User user = userDAO.search(userName, password);
                if (user != null) {
                    return converter.getUserDTO(user);
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

    }

    @Override
    public UserDTO findPassword(String userName, String email) {
        try {
            User user = userDAO.findPassword(userName, email);
            if (user != null) {
                return converter.getUserDTO(user);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }    }


    @Override
    public boolean updatePassword(Long id, String password) {
        try {
            return userDAO.updatePassword(id, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long getNextId() {
        Long lastId = userDAO.getLastId();
        if (lastId != null) {
            return lastId + 1;
        }
        return 1001L;
    }


}
