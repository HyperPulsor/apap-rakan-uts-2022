package id.ac.ui.cs.eaap.lab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fakutlas_case")
public class FakultasCase {
    @Id
    @Column(name = "fakultas")
    private String fakultas;

    @Column(name = "jumlah_kasus")
    private Integer jumlahKasus;
}
