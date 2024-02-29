package com.kuber.jobportal.repositories.JobRepo;

import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findAllByEmployer(User user);

    void deleteAllByEmployer(User user);

//    @Query(nativeQuery = true, value = "select * from Jobs j where j.employerid=1")
//    List<Job> findAlljobsByEmail(String email);
}
