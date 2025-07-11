package com.example.LibraryMS.Repository;


import com.example.LibraryMS.Entity.E_Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface E_ResourceRepository extends JpaRepository<E_Resource, Long> {
    @Query("SELECT e FROM E_Resource e WHERE LOWER(e.title) = LOWER(:title)")
    Optional<E_Resource> findByTitleIgnoreCase(@Param("title") String title);

}
