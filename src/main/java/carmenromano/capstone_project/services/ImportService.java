package carmenromano.capstone_project.services;


import carmenromano.capstone_project.entities.Comune;
import carmenromano.capstone_project.entities.Provincia;
import carmenromano.capstone_project.entities.Regione;
import carmenromano.capstone_project.repositories.ComuneRepository;
import carmenromano.capstone_project.repositories.ProvinciaRepository;
import carmenromano.capstone_project.repositories.RegioneRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

@Service
public class ImportService {

    @Autowired
    private ComuneRepository comuneRepository;
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private RegioneRepository regioneRepository;

    public void importProvince(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<Provincia> csvToBean = new CsvToBeanBuilder<Provincia>(reader)
                    .withType(Provincia.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Provincia> province = csvToBean.parse();
            provinciaRepository.saveAll(province);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void importComuni(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<Comune> csvToBean = new CsvToBeanBuilder<Comune>(reader)
                    .withType(Comune.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Comune> comuni = csvToBean.parse();
            comuneRepository.saveAll(comuni);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void importRegioni(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<Regione> csvToBean = new CsvToBeanBuilder<Regione>(reader)
                    .withType(Regione.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Regione> regioni = csvToBean.parse();
            regioneRepository.saveAll(regioni);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

