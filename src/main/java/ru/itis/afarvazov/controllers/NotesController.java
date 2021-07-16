package ru.itis.afarvazov.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.afarvazov.dtos.NoteDto;
import ru.itis.afarvazov.models.HistoryOfChanges;
import ru.itis.afarvazov.models.Note;
import ru.itis.afarvazov.security.details.UserDetailsImpl;
import ru.itis.afarvazov.services.HistoryService;
import ru.itis.afarvazov.services.NotesService;

import java.util.List;

import static ru.itis.afarvazov.dtos.NoteDto.from;


@RestController
public class NotesController {

    private final NotesService notesService;
    private final HistoryService historyService;

    public NotesController(NotesService notesService, HistoryService historyService) {
        this.notesService = notesService;
        this.historyService = historyService;
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteDto>> getAllNotes(@AuthenticationPrincipal UserDetailsImpl user) {
        List<Note> notes = notesService.findAll();
        historyService.save(user.getUser(), notesService.findAll(), HistoryOfChanges.Action.VIEWED);
        return ResponseEntity.ok(from(notes));
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteDto> addNote(@AuthenticationPrincipal UserDetailsImpl user,
                                           @RequestBody NoteDto noteDto) {
        Note note = notesService.addNote(noteDto);
        historyService.save(user.getUser(), note, HistoryOfChanges.Action.ADDED);
        return ResponseEntity.ok(from(note));
    }

    @PutMapping("/notes/{note-id}")
    public ResponseEntity<NoteDto> updateNote(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable("note-id") Long noteId, @RequestBody NoteDto note) {
        historyService.save(user.getUser(), Note.builder()
                .id(noteId)
                .title(note.getTitle())
                .deleted(false)
                .content(note.getContent())
                .build(), HistoryOfChanges.Action.CHANGED);
        return ResponseEntity.ok(notesService.updateNote(noteId, note));
    }

    @DeleteMapping("/notes/delete/{note-id}")
    public ResponseEntity<Long> deleteNote(@AuthenticationPrincipal UserDetailsImpl user,
                                           @PathVariable("note-id") Long noteId) {
        historyService.save(user.getUser(), notesService.findById(noteId), HistoryOfChanges.Action.DELETED);
        notesService.deleteNote(noteId);
        return ResponseEntity.ok(noteId);
    }

}
