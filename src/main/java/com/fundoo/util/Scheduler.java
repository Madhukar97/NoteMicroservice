package com.fundoo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.fundoo.repository.NoteRepo;
import com.fundoo.service.NoteService;

public class Scheduler {

	@Autowired
	private NoteRepo noteRepo;

	@Autowired
	private NoteService noteService;

	@Scheduled(cron = "20 * * * * ?")
	public void emptyTrashedNotes() throws Exception {
		noteService.emptyTrash();
	}

	@Scheduled(cron = "5 * * * * ?")
	public void sendNoteReminder() {
		noteService.sendNoteReminderMail();
	}

}
