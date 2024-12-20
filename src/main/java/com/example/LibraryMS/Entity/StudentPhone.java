package com.example.LibraryMS.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_phones")
@IdClass(StudentPhone.StudentPhoneKey.class) // Specify composite key class here
public class StudentPhone {

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", insertable = false, updatable = false) // Foreign key to students
    @JsonBackReference
    private Student student;

    // Composite Key Class
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentPhoneKey implements Serializable {
        private Long studentId;
        private String phoneNumber;
    }
}
