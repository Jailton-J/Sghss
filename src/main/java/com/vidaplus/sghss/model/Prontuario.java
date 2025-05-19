package com.vidaplus.sghss.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Prontuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL)
    private List<RegistroProntuario> registros;
}
