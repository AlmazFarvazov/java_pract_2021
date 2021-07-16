package ru.itis.afarvazov.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itis.afarvazov.models.HistoryOfChanges;
import ru.itis.afarvazov.models.Note;
import ru.itis.afarvazov.models.User;
import ru.itis.afarvazov.repositories.HistoryRepository;
import ru.itis.afarvazov.repositories.NotesRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryServiceImpl.class);

    private final HistoryRepository historyRepository;
    private final NotesRepository notesRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository, NotesRepository notesRepository) {
        this.historyRepository = historyRepository;
        this.notesRepository = notesRepository;
    }

    @Override
    public void save(User user, Note note, HistoryOfChanges.Action action) {
        HistoryOfChanges historyOfChanges = HistoryOfChanges.builder()
                .author(user)
                .note(note)
                .action(action)
                .actionDate(LocalDateTime.now())
                .build();
        historyRepository.save(historyOfChanges);
    }

    @Override
    public void save(User user, List<Note> notes, HistoryOfChanges.Action action) {
        for (Note note : notes) {
            save(user, note, action);
        }
    }

    @Override
    @Scheduled(fixedDelay = 1 * 60 * 1000, initialDelay = 1 * 60 * 1000)
    public void deletingTask() {
        LOGGER.info("Deleting task started");
        LocalDateTime time = LocalDateTime.now().minusMinutes(1);
        List<HistoryOfChanges> history = historyRepository.findAllByActionDateAfter(time);
        List<Note> notes = notesRepository.findAllByDeletedIsFalse();
        HashSet<Note> noteHashSet = new HashSet<>(notes);
        LOGGER.info(noteHashSet.toString());
        for (HistoryOfChanges change : history) {
            noteHashSet.remove(change.getNote());
        }
        LOGGER.info(noteHashSet.toString());
        for (Note note : noteHashSet) {
            note.setDeleted(true);
            notesRepository.save(note);
        }
    }
}
