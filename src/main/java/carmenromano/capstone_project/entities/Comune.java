package carmenromano.capstone_project.entities;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comune {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @CsvBindByPosition(position = 0)
    private String codiceProvincia;

    @NotNull
    @CsvBindByPosition(position = 1)
    private String codiceComune;

    @NotNull
    @CsvBindByPosition(position = 2)
    private String name;

    @NotNull
    @CsvBindByPosition(position = 3)
    private String provincia;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provinciaEntity;

    public Comune(String codiceProvincia, String codiceComune, String name, String provincia) {
        this.codiceProvincia = codiceProvincia;
        this.codiceComune = codiceComune;
        this.name = name;
        this.provincia = provincia;
    }
}
