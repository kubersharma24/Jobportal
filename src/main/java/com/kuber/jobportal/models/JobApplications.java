package com.kuber.jobportal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jobapplications")
@Getter @Setter
@NoArgsConstructor
public class  JobApplications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade ={CascadeType.DETACH, CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "applicant")
    private User applicant;

    @ManyToOne
    @JoinColumn(name = "job")
    private Job job;

    @Lob
    @Column(name = "resume" , columnDefinition = "LONGBLOB")
    private byte [] resume;

    @Column(name = "shortdescription")
    private String shortDescription;
}
