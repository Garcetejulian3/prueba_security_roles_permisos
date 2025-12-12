package com.roles_permisos.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {

    @GetMapping("/holaseg")
    public String secHelloWorld() {
        
        return "Hola Mundo Esto es una prueba con seguridad";
    }

    @GetMapping("/holanoseg")
    public String noSecHelloWorld() {
        
        return "Hola Mundo Esto es una prueba sin seguridad";
    }

}
