package ru.itis.afarvazov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.afarvazov.models.HistoryOfChanges;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryOfChanges, Long> {

    List<HistoryOfChanges> findAllByActionDateAfter(LocalDateTime after);

}
