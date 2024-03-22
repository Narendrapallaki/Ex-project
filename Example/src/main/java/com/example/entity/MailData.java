package com.example.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MailData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	//@ElementCollection
//	private List<String> emails;
	
	private String mails;
	public Date date;
	public String content;

}
