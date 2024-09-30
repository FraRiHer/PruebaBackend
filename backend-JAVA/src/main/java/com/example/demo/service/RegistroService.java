package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Auth.AuthResponse;
import com.example.demo.Auth.RegisterRequest;
import com.example.demo.Jwt.JwtService;
import com.example.demo.entities.Empleado;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repository.EmpleadoRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RegistroService {
     @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Intentar buscar el empleado por ID (cédula)
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(request.getEmpleado().getId());

        Empleado empleado = empleadoOpt.orElseGet(() -> {
            // Si no existe, crear un nuevo Empleado
            return empleadoRepository.save(Empleado.builder()
                    .id(request.getEmpleado().getId())
                    .nombre(request.getEmpleado().getNombre())
                    .apellido(request.getEmpleado().getApellido())
                    .telefono(request.getEmpleado().getTelefono())
                    .correo(request.getEmpleado().getCorreo())
                    .bonifica(request.getEmpleado().isBonifica())
                    .fechaDeNacimiento(request.getEmpleado().getFechaDeNacimiento())
                    .build());
        });

        // Crear y guardar el objeto User asociado al empleado
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) 
                .role(Role.valueOf(request.getRol().toUpperCase())) // Asumiendo que Role es un enum y USER es un valor de este
                .empleado(empleado)
                .photoId(request.getPhotoId())
                .build();

        userRepository.save(user);
        
        // Generar token de autenticación
        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
    
}
