package id.ac.ui.cs.eaap.lab.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {

    public List<String> getPeranOptionsList() {
        List<String> optionList = new ArrayList<>(List.of("mahasiswa", "dosen", "tendik", "satpam", "lainnya"));
        return optionList;
    }

    public List<String> getStatusOptionsList() {
        List<String> optionList = new ArrayList<>(List.of("suspek", "terkonfirmasi", "sembuh"));
        return optionList;
    }

    public List<String> getFakultasOptionsList() {
        List<String> optionList = new ArrayList<>(List.of("FASILKOM", "FK", "FT", "FISIP", "FIB"));
        return optionList;
    }
}
