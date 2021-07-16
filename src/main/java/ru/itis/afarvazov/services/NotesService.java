package ru.itis.afarvazov.services;

import ru.itis.afarvazov.dtos.NoteDto;
import ru.itis.afarvazov.models.Note;

import java.util.List;

public interface NotesService {
    Note findById(Long id);
    List<Note> findAll();
    Note addNote(NoteDto note);
    void deleteNote(Long noteId);
    NoteDto updateNote(Long noteId, NoteDto note);
}
