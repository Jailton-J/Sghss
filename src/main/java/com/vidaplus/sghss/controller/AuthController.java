package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.config.JwtService;
import com.vidaplus.sghss.dto.AuthRequest;
import com.vidaplus.sghss.dto.AuthResponse;
import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.model.Profissional;
import com.vidaplus.sghss.repository.PacienteRepository;
import com.vidaplus.sghss.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        
        // Verificar se é um paciente ou profissional
        Long id;
        String nome;
        String perfil;
        
        if ("PACIENTE".equalsIgnoreCase(request.getPerfil())) {
            Paciente paciente = pacienteRepository.findByEmail(request.getEmail());
            id = paciente.getId();
            nome = paciente.getNome();
            perfil = "PACIENTE";
        } else {
            Profissional profissional = profissionalRepository.findByEmail(request.getEmail());
            id = profissional.getId();
            nome = profissional.getNome();
            perfil = "PROFISSIONAL";
        }
        
        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwt)
                .tipo("Bearer")
                .id(id)
                .nome(nome)
                .email(request.getEmail())
                .perfil(perfil)
                .build());
    }
    
    @PostMapping("/registrar-paciente")
    public ResponseEntity<AuthResponse> registrarPaciente(@RequestBody Paciente paciente) {
        // Verificar se o email já está em uso
        if (pacienteRepository.findByEmail(paciente.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        
        // Criptografar a senha
        paciente.setSenha(passwordEncoder.encode(paciente.getSenha()));
        
        // Salvar o paciente
        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        
        // Gerar token JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(paciente.getEmail())
                .password(paciente.getSenha())
                .authorities("ROLE_PACIENTE")
                .build();
        
        String jwt = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwt)
                .tipo("Bearer")
                .id(pacienteSalvo.getId())
                .nome(pacienteSalvo.getNome())
                .email(pacienteSalvo.getEmail())
                .perfil("PACIENTE")
                .build());
    }
    
    @PostMapping("/registrar-profissional")
    public ResponseEntity<AuthResponse> registrarProfissional(@RequestBody Profissional profissional) {
        // Verificar se o email já está em uso
        if (profissionalRepository.findByEmail(profissional.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        
        // Criptografar a senha
        profissional.setSenha(passwordEncoder.encode(profissional.getSenha()));
        
        // Salvar o profissional
        Profissional profissionalSalvo = profissionalRepository.save(profissional);
        
        // Gerar token JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(profissional.getEmail())
                .password(profissional.getSenha())
                .authorities("ROLE_PROFISSIONAL")
                .build();
        
        String jwt = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwt)
                .tipo("Bearer")
                .id(profissionalSalvo.getId())
                .nome(profissionalSalvo.getNome())
                .email(profissionalSalvo.getEmail())
                .perfil("PROFISSIONAL")
                .build());
    }
}