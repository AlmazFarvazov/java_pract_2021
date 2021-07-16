package ru.itis.afarvazov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.afarvazov.models.Note;

import java.util.List;

public interface NotesRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByDeletedIsFalse();
    List<Note> findAllByTitleContains(String title);
}
