package id.ac.ui.cs.eaap.lab.controller;

import id.ac.ui.cs.eaap.lab.service.CovidTrackerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/covid")
public class CovidRestController {

    @Autowired
    CovidTrackerService covidTrackerService;

    @GetMapping("/all")
    public ResponseEntity getAllCovidCases() {
        log.info("api get all covid case");
        return ResponseEntity.ok("");
    }

    @GetMapping("/active")
    public ResponseEntity getActiveCovidCases() {
        log.info("api get all covid cases");
        return ResponseEntity.ok("");
    }

    @GetMapping("/statistics")
    public ResponseEntity getStatisticsCovidCases() {
        log.info("api statistics covid cases");
        return ResponseEntity.ok("");
    }


}
