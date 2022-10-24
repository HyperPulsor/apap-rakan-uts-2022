package id.ac.ui.cs.eaap.lab.controller;

import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.service.CovidTrackerService;
import id.ac.ui.cs.eaap.lab.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/covid")
public class CovidTrackingController {

    @Autowired
    CovidTrackerService covidTrackerService;

    @Autowired
    ListService listService;

    @GetMapping(value = "/")
    public String getAll(Model model) {
        log.info("access home");
        return "home";
    }


    @GetMapping("/add")
    public String addPatientFormPage(Model model) {

        CovidCaseModel covidCaseModel = new CovidCaseModel();
        // set default value to TODAY
        covidCaseModel.setTanggalGejalaPertama(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        model.addAttribute("covidCaseModel", covidCaseModel);
        model.addAttribute("listService", listService);

        return "case/form-add-covid-case";
    }

    @PostMapping(value = "/add", params = {"save"})
    public String addPatientSubmitPage(@ModelAttribute CovidCaseModel covidCaseModel, BindingResult result,
                                       RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/covid/add";
        }

        redirectAttrs.addFlashAttribute("success",
                String.format("Kasus baru berhasil disimpan sebagai id %d", covidCaseModel.getCaseId()));
        return "redirect:/covid/view-all";
    }

    @GetMapping("/view-all")
    public String viewAllCovidCase(Model model) {
        List<CovidCaseModel> covidCaseModelList = new ArrayList<>();
        model.addAttribute("caseList", covidCaseModelList);
        return "case/view-all-covid-case";
    }

}
