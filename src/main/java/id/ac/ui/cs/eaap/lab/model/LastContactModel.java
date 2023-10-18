package id.ac.ui.cs.eaap.lab.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "case_contact")
public class LastContactModel implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "case_contact_id")
    private long caseContactId;
    @Column(name = "nama")
    private String nama;
    @Column(name = "keterangan")
    private String keterangan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_covid_case", referencedColumnName = "case_id")
    private CovidCaseModel covidCaseModel;

}