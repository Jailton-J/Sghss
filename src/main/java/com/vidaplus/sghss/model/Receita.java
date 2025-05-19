package com.vidaplus.sghss.model;

import jakarta.persistence.*;

@Entity
public class Receita {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @OneToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;
}