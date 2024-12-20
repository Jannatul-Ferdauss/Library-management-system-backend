package com.example.LibraryMS.Repository;

import com.example.LibraryMS.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);

    boolean existsByEmail(String mail);
}
