package com.bhanu.notesapp.controller;

import com.bhanu.notesapp.model.Note;
import com.bhanu.notesapp.service.NoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/notes")
public class NoteController {
    private final NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @GetMapping()
    public List<Note> getAllNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping("/random")
    public ResponseEntity<Note> getRandomNote() {
        Note note = noteService.getRandomNote();
        if (note != null) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteService.getNoteById(id);
        return note.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        Note savedNote = noteService.addNote(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }
}
