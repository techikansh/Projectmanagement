package com.ProjectmanagementSystem.Projectmanagement_system.Services.Project;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.Chat;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.Project;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;

    Project getProjectById(Long projectId) throws Exception;

    void deleteProject(Long projectId, Long userId) throws Exception;

    Project updateProject(Project updatedProject, Long id)  throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByProjectId (Long projectId)  throws Exception;

    List<Project> searchProjects(String keyword, User user);

}
