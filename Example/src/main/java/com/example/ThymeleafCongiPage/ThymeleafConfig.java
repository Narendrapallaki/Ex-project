package com.example.ThymeleafCongiPage;

import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
@Configuration
public class ThymeleafConfig {

	
	public TemplateEngine mailProperties() {
		TemplateEngine te = new TemplateEngine();
		te.setTemplateResolver(getDetails());
		return te;
	}

	public ITemplateResolver getDetails() {
		ClassLoaderTemplateResolver cltr = new ClassLoaderTemplateResolver();
		cltr.setPrefix("/templates/");
		cltr.setSuffix(".html");
		cltr.setTemplateMode("HTML");
		cltr.setCharacterEncoding("UTF-8");
		return cltr;
	}
}
