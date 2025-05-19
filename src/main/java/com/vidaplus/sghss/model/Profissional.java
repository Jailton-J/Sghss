package com.vidaplus.sghss.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Profissional {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String nome;

    @Email
    private String email;

    private String senha;
    private String crmOuRegistro;
    private String telefone;
    private String especialidade;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonIgnore
    private List<Consulta> consultas;
}
