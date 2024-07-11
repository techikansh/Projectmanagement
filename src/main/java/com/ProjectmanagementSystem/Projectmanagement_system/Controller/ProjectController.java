package com.ProjectmanagementSystem.Projectmanagement_system.Controller;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.Chat;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.Project;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.User;
import com.ProjectmanagementSystem.Projectmanagement_system.Response.MessageResponse;
import com.ProjectmanagementSystem.Projectmanagement_system.Services.Project.ProjectService;
import com.ProjectmanagementSystem.Projectmanagement_system.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;

    @GetMapping()
    public ResponseEntity<List<Project>> getAllProjects (@RequestParam(required = false) String category,
                                                         @RequestParam(required = false) String tag,
                                                         @RequestHeader("Authorization")  String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> project= projectService.getProjectByTeam(user, category, tag);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectbyId (@PathVariable Long projectId,
                                                   @RequestHeader("Authorization") String jwt) throws Exception{

        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Project> createProject (@RequestBody Project project,
                                                  @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject (@RequestBody Project project,
                                                  @PathVariable Long projectId,
                                                  @RequestHeader("Authorization") String jwt) throws Exception{

        Project updatedProject = projectService.updateProject(project, projectId);
        return new ResponseEntity<> (updatedProject,HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable Long projectId,
                                                         @RequestHeader("Authorization") String jwt) throws Exception{

        projectService.deleteProject(projectId, userService.findUserProfileByJwt(jwt).getId());
        MessageResponse messageResponse = new MessageResponse("Project successfully deleted!!");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects (@RequestBody(required = false) String keyword,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Chat> getChatByProjectId (@PathVariable Long projectId,
                                                    @RequestHeader("Authorization") String jwt) throws Exception{
        Chat chat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<> (chat, HttpStatus.OK);
    }

}
