package com.bhanu.notesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bhanu.notesapp.model.Note;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query(value = "SELECT * FROM note ORDER BY RAND() LIMIT 1", nativeQuery = true)// native query for random note
    Note findRandomNote();
}
