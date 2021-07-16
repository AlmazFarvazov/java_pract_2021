package ru.itis.afarvazov.services;

import ru.itis.afarvazov.models.HistoryOfChanges;
import ru.itis.afarvazov.models.Note;
import ru.itis.afarvazov.models.User;

import java.util.List;

public interface HistoryService {

    void save(User user, Note note, HistoryOfChanges.Action action);
    void save(User user, List<Note> note, HistoryOfChanges.Action action);
    void deletingTask();

}
