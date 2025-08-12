package com.bhanu.notesapp.service;
import java.util.*;
import com.bhanu.notesapp.model.Note;
import com.bhanu.notesapp.repository.NoteRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note addNote(Note note) {
        return noteRepository.save(note);
    }
    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }
    public Optional<Note> getNoteById(Long id){
        return noteRepository.findById(id);
    }
    public Note getRandomNote(){
        return noteRepository.findRandomNote();
    }
}
