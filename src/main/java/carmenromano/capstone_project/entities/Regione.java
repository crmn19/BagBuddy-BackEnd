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
public class Regione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CsvBindByPosition(position = 1)
    private String codiceRegione;

    @CsvBindByPosition(position = 2)
    private String denominazioneRegione;

    public Regione(String codiceRegione, String denominazioneRegione) {
        this.codiceRegione = codiceRegione;
        this.denominazioneRegione = denominazioneRegione;
    }
}

