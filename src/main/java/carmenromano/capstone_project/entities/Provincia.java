package carmenromano.capstone_project.entities;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CsvBindByPosition(position = 0)
    private String codiceRegione;
    @CsvBindByPosition(position = 1)
    private String sigla;

    @CsvBindByPosition(position = 2)
    private String name;



    public Provincia(String codiceRegione, String sigla, String name, String tipologia, int numeroComuni, double superficie, String codiceSovracomunale) {
        this.codiceRegione = codiceRegione;
        this.sigla = sigla;
        this.name = name;
    }
}
