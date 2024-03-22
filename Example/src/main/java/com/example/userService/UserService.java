package com.example.userService;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import com.example.entity.EmailData;
import com.example.entity.MailData;
import com.example.entity.SalaryFields;
import com.example.entity.User;
import com.example.excelReaderFile.ExcelReader;
import com.example.mailRepository.MailRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private MailRepository mailRepository;

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine engine;

	public void excelFileSend(MultipartFile file, MultipartFile[] attach, User user)
			throws IOException, MessagingException {

		log.info("UserService running...!");
		List<EmailData> excelData = ExcelReader.readExcelData(file);

		log.info("Excel reader result : {}", excelData);

		
		MailData mail=new MailData();
		
		List<String>mailList=new ArrayList<>();
		String exMail = "";
		String exName = "";
		
		for (EmailData emailData : excelData) {

			exMail = emailData.getEmail();

			exName = emailData.getName();
		
		mailList.add(exMail);

			log.info("After ForEach exMail :{}", exMail);
			log.info("excel exName : {}", exName);

			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(exMail);
			helper.setSubject(user.getSubject());

			helper.setText(user.getText());

			Context con = new Context();
			con.setVariable("name", exName);

			con.setVariable("text", user.getText());

			String process = engine.process("mail-template.html", con);

			helper.setText(process, true);

			for (MultipartFile mf : attach) {// this line of code is for sending multiple file at a time

				ByteArrayResource iss = new ByteArrayResource(mf.getBytes());

				helper.addAttachment(mf.getOriginalFilename(), iss);

			}
			mailSender.send(mimeMessage);
			log.info("mail sended.....!");

		}

		String[] mailArray = mailList.toArray(new String[mailList.size()]);

		String joinedString = String.join(", ", mailArray);
		 mail.setMails(joinedString);
		
		    mail.setDate(new Date(System.currentTimeMillis())); 
		    mail.setContent(user.getText());

		    log.info("MailData list info :{}",mail);
		    mailRepository.save(mail);
		    log.info("data saved.....!");
	}

	public void singleMailAttach(User user, MultipartFile[] multipartFile) throws MessagingException, IOException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(user.getToMail());

		helper.setSubject(user.getSubject());
		helper.setText(user.getText());

		String name = "All";
		Context context = new Context();
		context.setVariable("user", name);
		context.setVariable("text", user.getText());
		String process = engine.process("single-template", context);

		helper.setText(process, true);

		for (MultipartFile mul2 : multipartFile) {

			ByteArrayResource arrayResource = new ByteArrayResource(mul2.getBytes());

			helper.addAttachment(mul2.getOriginalFilename(), arrayResource);

		}

		mailSender.send(message);
		log.info("single mail sended.....!");
    

		MailData mail=new MailData();
	
		 mail.setMails(user.getToMail());
		
		    mail.setDate(new Date(System.currentTimeMillis())); 
		    mail.setContent(user.getText());

		    log.info("MailData list info :{}",mail);
		    mailRepository.save(mail);
		    log.info("data saved.....!");
	}

	public void salaryDescription(MultipartFile file, String subject)
			throws EncryptedDocumentException, IOException, MessagingException {

		List<SalaryFields> salaryFields = ExcelReader.salaryFields(file);

		String name = "";
		double salary = 0;
		String email = "";
		String monthOfYear = "";

		for (SalaryFields salary1 : salaryFields) {

			name = salary1.getName();
			salary = salary1.getSalary();
			email = salary1.getMail();
			monthOfYear = salary1.getMontOfYear();

			MimeMessage message = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(email);

			helper.setSubject(subject);

			// String name="All";
			Context context = new Context();
			context.setVariable("user", name);
			context.setVariable("monthOfYear", monthOfYear);
			context.setVariable("salary", salary);
			String process = engine.process("salDes-template", context);

			helper.setText(process, true);

			mailSender.send(message);
		}

	}

}
