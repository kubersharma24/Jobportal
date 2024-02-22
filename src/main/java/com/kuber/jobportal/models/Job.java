package com.kuber.jobportal.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name ="job")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employer")
    private User employer;

    @Column(name = "title")
    private String title;

    @Column(name = "company")
    private String company;

    @Column(name = "location")
    private String location ;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "job",cascade =  CascadeType.ALL)
    private List <JobApplications> jobApplications;

    public Job(int id) {
        this.id = id;
    }
}
