package com.example.LibraryMS.Repository;

import com.example.LibraryMS.Entity.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, Long> {

    @Query("SELECT t FROM Thesis t WHERE LOWER(t.title) = LOWER(:title)")
    Optional<Thesis> findByTitleIgnoreCase(@Param("title") String title);

    List<Thesis> findByTopic(String topic);
}
