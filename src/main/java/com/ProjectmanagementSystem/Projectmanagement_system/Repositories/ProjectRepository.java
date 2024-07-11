package com.ProjectmanagementSystem.Projectmanagement_system.Repositories;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.Project;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOwner(User user);

    @Query("SELECT p FROM project p join p.team t WHERE t=:user")
    List<Project> findProjectByTeam (@Param("user") User user);

    List<Project> findByNameContainingAndTeamContaining (String partialName, User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
