package com.example.correo.controller;

import com.example.correo.dto.Registro;
import com.example.correo.service.EmailService;
import jakarta.mail.MessagingException;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/crearCuenta")
    public String registrarCuenta(@RequestBody Registro registro) {
        try {
            // Modelo con los datos que irá a la plantilla
            Map<String, Object> model = new HashMap<>();
            model.put("nombre", registro.getNombre());
            model.put("correo", registro.getCorreo());

            emailService.sendEmailWithTemplate(registro.getCorreo(), "Bienvenido", model);

            return "Registro exitoso, se ha enviado un correo de bienvenida.";
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            return "Error al enviar el correo.";
        }
    }
}