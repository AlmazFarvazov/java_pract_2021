package ru.itis.afarvazov.services;

import org.springframework.stereotype.Service;
import ru.itis.afarvazov.dtos.NoteDto;
import ru.itis.afarvazov.models.Note;
import ru.itis.afarvazov.repositories.NotesRepository;

import java.util.List;

import static ru.itis.afarvazov.dtos.NoteDto.from;

@Service
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;

    public NotesServiceImpl(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public Note findById(Long id) {
        return notesRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Note> findAll() {
        return notesRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Note addNote(NoteDto note) {
        return notesRepository.save(Note.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .deleted(false)
                .build());
    }

    @Override
    public void deleteNote(Long noteId) {
        Note note = notesRepository.findById(noteId).
                orElseThrow(IllegalArgumentException::new);
        note.setDeleted(true);
        notesRepository.save(note);
    }

    @Override
    public NoteDto updateNote(Long noteId, NoteDto note) {
        Note noteForUpdate = notesRepository.findById(noteId).
                orElseThrow(IllegalArgumentException::new);
        noteForUpdate.setTitle(note.getTitle());
        noteForUpdate.setContent(note.getContent());
        return from(notesRepository.save(noteForUpdate));
    }
}
