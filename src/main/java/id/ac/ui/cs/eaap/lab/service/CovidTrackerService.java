package id.ac.ui.cs.eaap.lab.service;


import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.model.FakultasCase;
import id.ac.ui.cs.eaap.lab.repository.CovidCaseDb;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Setter
@AllArgsConstructor
public class CovidTrackerService {
    private CovidCaseDb covidCaseDb;

    private ListService listService;

    public List<CovidCaseModel> findAll() {
        return covidCaseDb.findAll();
    }

    public CovidCaseModel findById(Long id) {
        for (CovidCaseModel covidCase : findAll()){
            if (covidCase.getCaseId() == id){
                return covidCase;
            }
        }
        return null;
    }

    public List<CovidCaseModel> searchCase(String nama){
        var searched = covidCaseDb.findByNamaContainingIgnoreCase(nama);
        return searched;
    }

    public List<CovidCaseModel> findActiveCases() {
        List<CovidCaseModel> listActive = new ArrayList<>();
        for (CovidCaseModel covidCase : findAll()){
            if ((covidCase.getStatus().equals("suspek") || covidCase.getStatus().equals("terkonfirmasi")) && covidCase.getHariSetelahGejalaPertama() <= 14){
                listActive.add(covidCase);
            }
        }
        return listActive;
    }

    public void add(CovidCaseModel covidCaseModel) {
        covidCaseDb.save(covidCaseModel);
    }

    public void update(CovidCaseModel covidCaseModel) {
        covidCaseDb.save(covidCaseModel);
    }

    public long countIntervalDays(Date startDate, Date currDate){
        long diff = currDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public List<FakultasCase> getJumlahKasusFakultas(){
        List<CovidCaseModel> listActiveCase = findActiveCases();
        int[] listCounter = new int[5];// fasilkom, fib, fisip, fk, ft
        for (CovidCaseModel active : listActiveCase){
            if (active.getFakultas().equals("FASILKOM")){
                listCounter[0]++;
            } else if (active.getFakultas().equals("FIB")) {
                listCounter[1]++;
            } else if (active.getFakultas().equals("FISIP")) {
                listCounter[2]++;
            } else if (active.getFakultas().equals("FK")) {
                listCounter[3]++;
            } else if (active.getFakultas().equals("FT")) {
                listCounter[4]++;
            }
        }
        List<FakultasCase> listFakultas = new ArrayList<>();
        for (String fakultas : listService.getFakultasOptionsList()){
            FakultasCase newFakultas = new FakultasCase();
            newFakultas.setFakultas(fakultas);
            if (newFakultas.getFakultas().equals("FASILKOM")){
                newFakultas.setJumlahKasus(listCounter[0]);
            } else if (newFakultas.getFakultas().equals("FIB")) {
                newFakultas.setJumlahKasus(listCounter[1]);
            } else if (newFakultas.getFakultas().equals("FISIP")) {
                newFakultas.setJumlahKasus(listCounter[2]);
            } else if (newFakultas.getFakultas().equals("FK")) {
                newFakultas.setJumlahKasus(listCounter[3]);
            } else if (newFakultas.getFakultas().equals("FT")) {
                newFakultas.setJumlahKasus(listCounter[4]);
            }
            listFakultas.add(newFakultas);
        }
        return listFakultas;
    }
}

