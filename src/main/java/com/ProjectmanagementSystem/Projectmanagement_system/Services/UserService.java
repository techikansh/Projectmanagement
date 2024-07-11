package com.ProjectmanagementSystem.Projectmanagement_system.Services;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail (String email) throws Exception;

    User findUserById (Long userId) throws Exception;

    User updatedUsersProjectSize (User user, int number) throws Exception;
}
