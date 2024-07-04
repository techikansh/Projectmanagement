package com.ProjectmanagementSystem.Projectmanagement_system.Repositories;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
