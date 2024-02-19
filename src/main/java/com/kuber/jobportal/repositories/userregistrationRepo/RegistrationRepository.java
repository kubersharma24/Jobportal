package com.kuber.jobportal.repositories.userregistrationRepos;

import com.kuber.jobportal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<User, String>{
}
