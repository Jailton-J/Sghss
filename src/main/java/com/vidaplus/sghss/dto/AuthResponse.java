package com.vidaplus.sghss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String tipo;
    private Long id;
    private String nome;
    private String email;
    private String perfil; // "PACIENTE" ou "PROFISSIONAL"
}