package kz.atu.uit.afo.domain;

import javax.persistence.*;

@Entity
public class EducationProgramm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edacation_id")
    private Long id;

    private String nameEducation;

    public EducationProgramm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEducation() {
        return nameEducation;
    }

    public void setNameEducation(String nameEducation) {
        this.nameEducation = nameEducation;
    }
}
