package id.ac.ui.cs.eaap.lab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "covid_case")
@JsonIgnoreProperties(value={"listContact"})
public class CovidCaseModel implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "case_id")
    private long caseId;
    @Column(name = "nik")
    private String nik;
    @Column(name = "nama")
    private String nama;
    @Column(name = "status")
    private String status;
    @Column(name = "tanggalGejalaPertama")
    private Date tanggalGejalaPertama;
    @Column(name = "peran")
    private String peran;
    @Column(name = "fakultas")
    private String fakultas;
    @Column(name = "days")
    private Long hariSetelahGejalaPertama;
    @OneToMany(mappedBy = "covidCaseModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LastContactModel> listContact = new ArrayList<>();

}