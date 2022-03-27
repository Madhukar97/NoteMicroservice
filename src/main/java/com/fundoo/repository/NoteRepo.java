package com.fundoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fundoo.model.Note;

@Repository
public interface NoteRepo extends JpaRepository<Note, Integer> {

	List<Note> findAllByUserId(int userId);


}
