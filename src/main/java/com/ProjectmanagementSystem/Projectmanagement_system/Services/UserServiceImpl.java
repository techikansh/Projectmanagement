package com.ProjectmanagementSystem.Projectmanagement_system.Services;

import com.ProjectmanagementSystem.Projectmanagement_system.Config.JwtProvider;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.User;
import com.ProjectmanagementSystem.Projectmanagement_system.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new Exception("User not found!");
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new Exception("User not found!");
        return user.get();
    }

    @Override
    public User updatedUsersProjectSize(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepository.save(user);
    }
}
