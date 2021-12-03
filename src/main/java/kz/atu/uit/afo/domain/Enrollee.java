package kz.atu.uit.afo.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
public class Enrollee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User careerСounselor;

    private String fio;



    @Length(min = 12, max = 12, message = "Введите верный иин")
    private String iin;


    private String phone;
    private String university;

    @Email
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    private Region region;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "edacation_id", referencedColumnName = "edacation_id")
    private EducationProgramm educationProgramm;

    private String note;



    @CreationTimestamp
    private LocalDateTime createdAt;

    public Enrollee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCareerСounselor() {
        return careerСounselor;
    }

    public void setCareerСounselor(User careerСounselor) {
        this.careerСounselor = careerСounselor;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public EducationProgramm getEducationProgramm() {
        return educationProgramm;
    }

    public void setEducationProgramm(EducationProgramm educationProgramm) {
        this.educationProgramm = educationProgramm;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
