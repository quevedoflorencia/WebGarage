package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class TestEmailController {

    private EmailService emailService;

    @Autowired
    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/sendEmail")
    public String sendTestEmail() {
        emailService.sendSimpleMessage("mail@mail.com", "Prueba de Envío de Correo", "Este es un correo de prueba para verificar la funcionalidad.");
        return "correoEnviado";
    }
}
