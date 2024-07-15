package carmenromano.capstone_project.entities;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    UUID id;

    @CsvBindByPosition(position = 1)
    private String sigla;

    @CsvBindByPosition(position = 2)
    private String name;

    @CsvBindByPosition(position = 0)
    private String regione;
}
