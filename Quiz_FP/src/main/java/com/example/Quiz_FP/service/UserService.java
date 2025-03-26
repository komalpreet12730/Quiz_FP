package com.example.Quiz_FP.service;


import com.example.Quiz_FP.db.UserRepository;
import com.example.Quiz_FP.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public boolean authenticate(String username, String password) {
        Optional<User> user = userRepo.findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    public boolean updateUser(Long id, User updatedUser) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setProfilePicture(updatedUser.getProfilePicture());
            user.setFavoriteCategory(updatedUser.getFavoriteCategory());
            userRepo.save(user);
            return true;
        }
        return false;
    }
}
