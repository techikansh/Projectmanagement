package com.ProjectmanagementSystem.Projectmanagement_system.Repositories;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Invitation findByToken(String token);

    Invitation findByEmail (String userEmail);


}
