package carmenromano.capstone_project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Customer customer;


    @OneToOne
    @JoinColumn(nullable = false)
    private  Comune comune;
    @OneToOne
    @JoinColumn(nullable = false)
    private Provincia provincia; //
    @Override
    public String toString() {
        return "Indirizzo{" +
                "id=" + id +
                ", via='" + via + '\'' +
                ", civico=" + civico +
                ", cap=" + cap +
                ", comune=" + comune +
                '}';
    }
}