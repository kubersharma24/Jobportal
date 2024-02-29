package com.kuber.jobportal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="role")
    private String role;
}
