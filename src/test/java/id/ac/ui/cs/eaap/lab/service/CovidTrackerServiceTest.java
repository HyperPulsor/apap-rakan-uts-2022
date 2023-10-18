package id.ac.ui.cs.eaap.lab.service;


import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.model.FakultasCase;
import id.ac.ui.cs.eaap.lab.model.LastContactModel;
import id.ac.ui.cs.eaap.lab.repository.CovidCaseDb;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CovidTrackerServiceTest {
    @Mock
    private CovidCaseDb covidCaseDb;

    @InjectMocks
    private CovidTrackerService covidTrackerService;

    @BeforeEach
    public void setUp(){
        List<CovidCaseModel> covidCaseModels = new ArrayList<>();

        covidTrackerService.setListService(new ListService());

        var case1 = new CovidCaseModel(1, "123", "azis", "suspek",
                new Date(Calendar.getInstance().getTime().getTime()), "mahasiswa",
                "FASILKOM", 0L, new ArrayList<LastContactModel>()); // fasilkom 1
        var case2 = new CovidCaseModel(2, "342", "ori", "suspek",
                new Date(Calendar.getInstance().getTime().getTime()), "mahasiswa",
                "FK", 0L, new ArrayList<LastContactModel>()); // fk 1
        var case3 = new CovidCaseModel(3, "123", "azis", "suspek",
                new Date(Calendar.getInstance().getTime().getTime()), "mahasiswa",
                "FT", 0L, new ArrayList<LastContactModel>()); // ft 1
        var case4 = new CovidCaseModel(3, "123", "bruh", "sembuh",
                new Date(Calendar.getInstance().getTime().getTime()), "mahasiswa",
                "FASILKOM", 0L, new ArrayList<LastContactModel>());

        String str = "2015-03-31";
        Date date = Date.valueOf(str);
        var case5 = new CovidCaseModel(3, "123", "dateJelek", "suspek",
                date, "mahasiswa",
                "FASILKOM", covidTrackerService.countIntervalDays(date, new Date(Calendar.getInstance().getTime().getTime())), new ArrayList<LastContactModel>());


        covidCaseModels.add(case1);
        covidCaseModels.add(case2);
        covidCaseModels.add(case3);
        covidCaseModels.add(case4);
        covidCaseModels.add(case5);

        Mockito.when(covidCaseDb.findAll()).thenReturn(covidCaseModels);
    }

    @Test
    public void testFindAll() {
        var listAll = covidTrackerService.findAll();
        assertEquals(2, listAll.size());
    }

    @Test
    public void getJumlahKasusFakultas() {
        var listFakutas = covidTrackerService.getJumlahKasusFakultas();

        var fasilkomCounter = 0;
        var fkCounter = 0;
        var ftCounter = 0;
        var fisipCounter = 0;
        var fibCounter = 0;

        for (FakultasCase fc : listFakutas){
            if (fc.getFakultas().equals("FASILKOM")){
                fasilkomCounter = fc.getJumlahKasus();
            } else if (fc.getFakultas().equals("FK")){
                fkCounter = fc.getJumlahKasus();
            } else if (fc.getFakultas().equals("FISIP")){
                fisipCounter = fc.getJumlahKasus();
            } else if (fc.getFakultas().equals("FT")) {
                ftCounter = fc.getJumlahKasus();
            } else if (fc.getFakultas().equals("FIB")) {
                fibCounter = fc.getJumlahKasus();
            }
        }

        assertEquals(1, fasilkomCounter);
        assertEquals(1, fkCounter);
        assertEquals(0, fisipCounter);
        assertEquals(1, ftCounter);
        assertEquals(0, fibCounter);
    }
}

