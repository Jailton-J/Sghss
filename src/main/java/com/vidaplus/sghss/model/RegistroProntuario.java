package com.vidaplus.sghss.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class RegistroProntuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;

    @ManyToOne
    private Profissional profissional;
}
