package carmenromano.capstone_project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String via;
    private int civico;
    private int cap;
    @OneToOne
    @JoinColumn(nullable = false, unique = false)
    private  Comune comune;
}