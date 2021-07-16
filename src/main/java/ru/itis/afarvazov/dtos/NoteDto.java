package ru.itis.afarvazov.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.afarvazov.models.Note;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDto {

    private String title;
    private String content;

    public static NoteDto from(Note note) {
        return NoteDto.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }

    public static List<NoteDto> from(List<Note> notes) {
        return notes.stream().map(NoteDto::from).collect(Collectors.toList());
    }

}
