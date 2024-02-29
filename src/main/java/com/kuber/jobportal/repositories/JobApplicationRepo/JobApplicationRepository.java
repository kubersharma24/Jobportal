package com.kuber.jobportal.repositories.JobApplicationRepo;

import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.JobApplications;
import com.kuber.jobportal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplications , Integer > {
    public List<JobApplications> findAllByJob(Job job);
    @Query("SELECT CASE WHEN COUNT(ja) > 0 THEN true ELSE false END FROM JobApplications ja " +
            "WHERE ja.job = :job AND ja.applicant = :applicant")
    boolean existsByJobAndApplicant(@Param("job") Job job, @Param("applicant") User applicant);
}
