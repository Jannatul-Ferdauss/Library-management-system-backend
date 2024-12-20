package com.example.LibraryMS.Repository;

import com.example.LibraryMS.Entity.StudentPhone;
import com.example.LibraryMS.Entity.StudentPhone.StudentPhoneKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPhoneRepository extends JpaRepository<StudentPhone, StudentPhoneKey> {
    List<StudentPhone> findByStudentId(Long studentId);
}
