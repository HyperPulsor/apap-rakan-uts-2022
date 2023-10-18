package id.ac.ui.cs.eaap.lab.controller;

import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.model.LastContactModel;
import id.ac.ui.cs.eaap.lab.service.CovidTrackerService;
import id.ac.ui.cs.eaap.lab.service.ListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/covid")
public class CovidTrackingController {

    CovidTrackerService covidTrackerService;

    ListService listService;

    @GetMapping(value = "/")
    public String getAll(Model model) {
        log.info("acess home");
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
    public String addPatientSubmitPage(@ModelAttribute CovidCaseModel covidCaseModel, BindingResult result, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/covid/add";
        }
        covidCaseModel.setHariSetelahGejalaPertama(covidTrackerService.countIntervalDays(covidCaseModel.getTanggalGejalaPertama(), new Date()));
        covidTrackerService.add(covidCaseModel);
        redirectAttrs.addFlashAttribute("success", String.format("Kasus baru berhasil disimpan sebagai id %d", covidCaseModel.getCaseId()));
        return "redirect:/covid/view-all";
    }

    @GetMapping("/view-all")
    public String viewAllCovidCase(Model model) {
        List<CovidCaseModel> covidCaseModelList = covidTrackerService.findAll();
        model.addAttribute("caseList", covidCaseModelList);
        return "case/view-all-covid-case";
    }

    @GetMapping("/update/{caseId}")
    public String formUpdateCovidCase(@PathVariable("caseId") Long caseId, Model model) {
        CovidCaseModel caseSelected = covidTrackerService.findById(caseId);
        model.addAttribute("caseSelected", caseSelected);
        model.addAttribute("listService", listService);
        return "case/form-update-covid-case";
    }

    @PostMapping("/update/{caseId}")
    public String updateCovidCase(@ModelAttribute CovidCaseModel covidCaseModel, BindingResult result, Model model, @PathVariable("caseId") Long caseId) {
        if (result.hasErrors()) {
            model.addAttribute("error", "The error occurred.");
            return "redirect:/covid/update" + caseId;
        }
        covidTrackerService.update(covidCaseModel);
        model.addAttribute("success", String.format("Kasus dengan id %d berhasil diubah", covidCaseModel.getCaseId()));
        return "redirect:/covid/view-all";
    }

    @GetMapping("/detail/{caseId}")
    public String formDetailCase(@PathVariable("caseId")Long caseId, Model model){
        CovidCaseModel caseSelected = covidTrackerService.findById(caseId);
        LastContactModel lastContactModel = new LastContactModel();
        List<LastContactModel> listLastContact = caseSelected.getListContact();
        model.addAttribute("listLastContact", listLastContact);
        model.addAttribute("lastContactModel", lastContactModel);
        model.addAttribute("caseSelected", caseSelected);
        model.addAttribute("listService", listService);
        return "case/view-detail-covid-case";
    }

    @PostMapping("/detail/{caseId}")
    public String detailCase(@PathVariable("caseId") Long caseId, @ModelAttribute LastContactModel lastContactModel, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("error", "The error occurred.");
            return "redirect:/covid/detail/" + caseId;
        }
        var covidCase = covidTrackerService.findById(caseId);

        lastContactModel.setCovidCaseModel(covidCase);
        covidCase.getListContact().add(lastContactModel);

        covidTrackerService.update(covidCase);
        return "redirect:/covid/detail/" + caseId;
    }

    @GetMapping("/search")
    public String searchCase(@RequestParam(value="nama", required = false)String nama, Model model){
        List<CovidCaseModel> listCovidCase = new ArrayList<>();
        if (!nama.isEmpty()){
            listCovidCase = covidTrackerService.searchCase(nama);
        }
        model.addAttribute("caseList", listCovidCase);
        return "case/view-all-covid-case";
    }
}
