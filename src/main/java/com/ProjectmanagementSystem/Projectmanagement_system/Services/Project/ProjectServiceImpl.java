package com.ProjectmanagementSystem.Projectmanagement_system.Services.Project;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.Chat;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.Project;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.User;
import com.ProjectmanagementSystem.Projectmanagement_system.Repositories.ProjectRepository;
import com.ProjectmanagementSystem.Projectmanagement_system.Services.Chat.ChatService;
import com.ProjectmanagementSystem.Projectmanagement_system.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project newProject = new Project();

        newProject.setName(project.getName());
        newProject.setOwner(project.getOwner());
        newProject.setDescription(project.getDescription());
        newProject.setCategory(project.getCategory());
        newProject.setTags(project.getTags());
        newProject.getTeam().add(user);

        Project savedProject = projectRepository.save(newProject);

        Chat chat  = new Chat();
        chat.setProject(savedProject);

        Chat createdChat = chatService.createChat(chat);
        savedProject.setChat(createdChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);
        if (category != null) {
            projects.stream().filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        if (tag != null) {
            projects.stream().filter(project -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) throw new Exception("Project not found!");
        return project.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user  = userService.findUserById(userId);

        if (!project.getTeam().contains(user)) {
            project.getTeam().add(user);
        }
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user  = userService.findUserById(userId);
        project.getTeam().remove(user);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) {
        return projectRepository.findByNameContainingAndTeamContaining(keyword, user);
    }
}
