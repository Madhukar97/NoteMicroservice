package com.fundoo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoo.model.Note;
import com.fundoo.service.NoteService;
import com.fundoo.util.Response;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/notemicroservice")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping("/users/saveNote/")
	public ResponseEntity<Response> saveNote( @RequestHeader String token, @RequestBody Note note){
		Response response = noteService.saveNote(note , token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Response> deleteNote(@PathVariable int id){
		Response response = noteService.deleteNote(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/users/")
	public ResponseEntity<List<Note>> getAllNote(@RequestHeader String token){
		List<Note> notes = noteService.getAllNote(token);
		return new ResponseEntity<>(notes, HttpStatus.OK);
	}

	@PutMapping("users/")
	public ResponseEntity<Response> updateNote( @RequestHeader String token, @RequestBody Note note){
		Response response = noteService.updateNote(note , token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/note/search/{query}")
	public ResponseEntity<List<Note>> searchNotes(@RequestHeader String token, @PathVariable String query) throws IllegalArgumentException, UnsupportedEncodingException{
		List<Note> notes = noteService.searchNote(query, token);
		return new ResponseEntity<>(notes, HttpStatus.OK);
	}
	
	@GetMapping("/note/search")
	public ResponseEntity<List<Note>> getAllNoteTitles(){
		List<Note> noteTitles = noteService.fetchAllNoteTitles();
		return new ResponseEntity<List<Note>>(noteTitles, HttpStatus.OK);
	}

}
