package id.ac.ui.cs.eaap.lab.service;


import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CovidTrackerService {

    public List<CovidCaseModel> findAll() {
        return new ArrayList<>();
    }

    public List<CovidCaseModel> findActiveCases() {
        return new ArrayList<>();
    }

    public void add(CovidCaseModel covidCaseModel) {
    }

    public void update(CovidCaseModel covidCaseModel) {
    }

}

