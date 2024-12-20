package com.example.LibraryMS.Entity;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Dept_Name", nullable = false)
    private String dept;

    @Column(name ="Password", nullable = false)
    private String password;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name ="email",nullable = false, unique = true)
    private String email;

    @Column(name = "Batch", nullable = false)
    private String batch;

    @Column(name = "Interest", nullable = true)
    private String interest;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentPhone> phones; // Relationship with StudentPhone

    @OneToMany(mappedBy = "student")
    private List<Feedback> feedbackList;
}
