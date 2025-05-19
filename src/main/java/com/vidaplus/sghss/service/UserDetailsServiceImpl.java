package com.vidaplus.sghss.service;

import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.model.Profissional;
import com.vidaplus.sghss.repository.PacienteRepository;
import com.vidaplus.sghss.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Paciente paciente = pacienteRepository.findByEmail(email);
        if (paciente != null) {
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_PACIENTE")
            );
            return new User(paciente.getEmail(), paciente.getSenha(), authorities);
        }

        Profissional profissional = profissionalRepository.findByEmail(email);
        if (profissional != null) {
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_PROFISSIONAL")
            );
            return new User(profissional.getEmail(), profissional.getSenha(), authorities);
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
    }
}