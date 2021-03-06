package kz.atu.uit.afo.domain;

import kz.atu.uit.afo.domain.util.DomainHelper;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Enrollee implements DomainHelper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User careerCounselor;


    @NotEmpty(message = "ФИО не может быть пустым")
    private String fio;



    @Length(min = 12, max = 12, message = "Введите верный иин")
    private String iin;


    @NotBlank(message = "Мобильный телефон не может быть пустым")
    private String phone;

    @NotBlank(message = "Университет не может быть пустым")
    private String university;

    @NotBlank(message = "Email телефон не может быть пустым")
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
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Enrollee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCareerCounselor() {
        return careerCounselor;
    }

    public void setCareerCounselor(User careerCounselor) {
        this.careerCounselor = careerCounselor;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFormatCreatedAt(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        if (createdAt!=null){
            return formatter.format(createdAt);
        }
        return "";
    }

    @Override
    public String toString() {
        return "Enrollee{" +
                "id=" + id +
                ", careerCounselor=" + careerCounselor +
                ", fio='" + fio + '\'' +
                ", iin='" + iin + '\'' +
                ", phone='" + phone + '\'' +
                ", university='" + university + '\'' +
                ", email='" + email + '\'' +
                ", region=" + region +
                ", educationProgramm=" + educationProgramm +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public Long getIdFromHelper() {
        return id;
    }

    @Override
    public String getNameFromHelper() {
        return fio;
    }
}
