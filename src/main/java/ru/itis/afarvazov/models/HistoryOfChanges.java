package ru.itis.afarvazov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class HistoryOfChanges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    private LocalDateTime actionDate;

    @Enumerated(value = EnumType.STRING)
    private Action action;

    public enum Action {
        ADDED, CHANGED, DELETED, VIEWED
    }

}
