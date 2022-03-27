package com.fundoo.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fundoo.model.Note;
import com.fundoo.repository.NoteRepo;
import com.fundoo.util.Response;

@Service
public class NoteService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private NoteRepo noteRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private Response response;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ElasticSearchService elasticSearchService;


	public Response saveNote(Note note, String token) {
		int userId = restTemplate.getForObject("http://localhost:8081/usermicroservice/getuserid/" + token, Integer.class);
		note.setUserId(userId);
		Note savedEntity =  noteRepo.save(note);

		//System.out.println(savedEntity);
		//savedEntity.setUser(null);
		elasticSearchService.createNote(savedEntity);
		response.setStatusCode(200);
		response.setStatusMessage("Note is saved successfully..!");
		response.setToken(token);
		return response;
	}

	public Response deleteNote(int id) {
		Note validNote = noteRepo.findById(id).get();
		noteRepo.delete(validNote);
		elasticSearchService.deleteNote(validNote);
		response.setStatusCode(200);
		response.setStatusMessage("Note is deleted successfully..!");
		response.setToken(validNote);
		return response;
	}

	public List<Note> getAllNote(String token) {
		int userId = restTemplate.getForObject("http://localhost:8081/usermicroservice/getuserid/" + token, Integer.class);

		List<Note> notes = noteRepo.findAllByUserId(userId);
		
		return notes;
	}

	public Response updateNote(Note note, String token) {
		int userId = restTemplate.getForObject("http://localhost:8081/usermicroservice/getuserid/" + token, Integer.class);
		note.setUserId(userId);
		
		Note updatedEntity = noteRepo.save(note);
		elasticSearchService.updateNote(updatedEntity);
		response.setStatusCode(200);
		response.setStatusMessage("Note is updated successfully..!");
		response.setToken(note);
		return response;
	}

	public List<Note> searchNote(String query, String token) throws IllegalArgumentException, UnsupportedEncodingException{
		return elasticSearchService.searchNotes(query, token);
		//return elasticSearchService.searchData();
	}

	public void emptyTrash() {
		List<Note> notesList = noteRepo.findAll();
		notesList.stream().filter(p -> p.isInTrash()).forEach(note -> {
			System.out.println("Note deleted with id: " + note.getId());
			noteRepo.delete(note);});
	}

	public void sendNoteReminderMail() {
		List<Note> notesList = noteRepo.findAll();
		Date currentDate = new Date();
		notesList.stream().forEach(note -> {
			if( note.getReminder() != null && currentDate.getTime() >= note.getReminder().getTime()) {
				System.out.println("Note reminder mail sent to user email");
				try {
					emailService.sendNoteRemainder(note);
					note.setReminder(null);
					noteRepo.save(note);
				} catch (UnsupportedEncodingException | MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public List<Note> fetchAllNoteTitles() {
		return elasticSearchService.getAllTitles();
	}

}
