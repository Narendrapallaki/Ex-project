package com.example.Controllor;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.example.entity.User;
import com.example.userService.UserService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserControllor {

	
	
	
	
	
	//<input type="file" id="file" name="file" accept=".xlsx,.pdf,.doc,.docx" title=".xlsx,.pdf,.doc,.docx">

	
	
	
	
	@Autowired
	private UserService userService;

	@GetMapping("/run-map")
	public String run(Model model, User user) {
		// model.addAttribute("user", user);
		return "test";
	}

	@GetMapping("/salary")
	public String salaryUpdate() {

		return "salaryForm";
	}

	@PostMapping("/send-email")
	// @ResponseBody
	public String sendEmail(@ModelAttribute User user,
			@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam(name = "attFile", required = false) MultipartFile[] attFile)
			throws IOException, MessagingException {

		if (!file.isEmpty() && user.getToMail() != null) {

			log.info("If block execution...");
			userService.excelFileSend(file, attFile, user);
			log.info("User controllor excelFile output {}", user);
		} else {

			log.info("else block execution.....!");

			userService.singleMailAttach(user, attFile);

			log.info("User controllor singleFile output :{}");
			log.info("single with attached file {}", attFile.length);
		}

		log.info("email in controllor {}", user.getToMail());
		log.info("file in controllor {}", file.getOriginalFilename());

		return "redirect:/success";
	}

	@PostMapping("/submit")
	public String salaryFormUpdate(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam("subject") String subject)
			throws EncryptedDocumentException, IOException, MessagingException {

		log.info(" Salary subject : {}", subject);
		log.info("salary file name :{}", file.getOriginalFilename());

		userService.salaryDescription(file, subject);
		return "redirect:/success";
	}

	@GetMapping("/success")
	public String result() {
		return "run";
	}

}
