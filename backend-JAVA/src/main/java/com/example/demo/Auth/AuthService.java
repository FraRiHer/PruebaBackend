package com.example.demo.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.example.demo.Jwt.JwtService;
import com.example.demo.entities.Empleado;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        Empleado empleado = user.getEmpleado();
        String token = jwtService.getToken(user);

        return AuthResponse.builder()
            .token(token)
            .establecimientoId(empleado.getEstablecimiento().getId())
            .establecimientoNombre(empleado.getEstablecimiento().getNombre())
            .build();
    }

   /*  public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .role(Role.USER)
            .build();

        userRepository.save(user);
        
        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    } */

}
