package com.fundoo.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.client.RestTemplate;

import com.fundoo.model.Note;

public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private RestTemplate restTemplate;


	public void sendNoteRemainder(Note note) throws UnsupportedEncodingException, MessagingException {
		String email = restTemplate.getForObject("http:localhost:8081/usermicroservice/getuseremail/" + note.getUserId(), String.class);
		
		//String email = userRepo.findById(note.getUser().getId()).get().getEmail();
		String subject = "Fundoo Note Remainder";
		String senderName = "Fundoo Team";
		String mailContent = "<p>This is a remainder for the note: <br><h2>"+ note.getTitle() + "</h2><br>" + note.getContent() +"</p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("${spring.mail.username}", senderName);
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(mailContent, true);

		mailSender.send(message);
	}


}
