package com.dh.apiDentalClinic.controller;

import com.dh.apiDentalClinic.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "UserJwT", description = "Obtencion de datos por JWT")
@RestController
@RequestMapping("/user")
public class UserController {

    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/username")
    public String getLoggedInUsername(HttpServletRequest request) {
        // Obtener el token del encabezado de la solicitud
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            // Eliminar el prefijo "Bearer " del token
            token = token.substring(7);

            // Obtener el nombre de usuario del token utilizando JwtProvider
            return jwtProvider.getUserNameFromToken(token);
        }
        // Si no hay token o el formato no es correcto, puedes devolver un valor por defecto o lanzar una excepción según tus necesidades
        return "Usuario no encontrado";
    }
}
