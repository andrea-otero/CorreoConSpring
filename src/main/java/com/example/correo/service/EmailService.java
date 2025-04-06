package com.example.correo.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendEmailWithTemplate(String to, String subject, Map<String, Object> model)
            throws MessagingException, IOException, TemplateException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Carga la plantilla
        Template template = freemarkerConfig.getTemplate("registro-email.ftl");

        // Procesa la plantilla con datos
        StringWriter stringWriter = new StringWriter();
        template.process(model, stringWriter);
        String html = stringWriter.toString();

        // Configura el correo
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("tareaprueba078@gmail.com"); 
        helper.setText(html, true); // true = contenido HTML

        emailSender.send(message);
    }
}