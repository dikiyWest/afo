package kz.atu.uit.afo.domain;

import kz.atu.uit.afo.domain.util.DomainHelper;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Activity implements DomainHelper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @NotEmpty(message = "Дата не может быть пустой")
    private LocalDateTime dateActivity;

    @NotEmpty(message = "Название мероприятия не может быть пустым")
    private String nameActivity;

    @NotEmpty(message = "Формат не может быть пустым")
    private String formatActivity;

    @NotEmpty(message = "Место не может быть пустым")
    private String  placeActivity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    private Region region;

    @NotEmpty(message = "Число не может быть пустым")
    @Min(value = 0,message = "Число больше 0")
    private int countPeople;


    private String fileName;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact contact;





    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Activity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateActivity() {
        return dateActivity;
    }

    public void setDateActivity(LocalDateTime dateActivity) {
        this.dateActivity = dateActivity;
    }

    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public String getFormatActivity() {
        return formatActivity;
    }

    public void setFormatActivity(String formatActivity) {
        this.formatActivity = formatActivity;
    }

    public String getPlaceActivity() {
        return placeActivity;
    }

    public void setPlaceActivity(String placeActivity) {
        this.placeActivity = placeActivity;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(int countPeople) {
        this.countPeople = countPeople;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }



    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", author=" + author +
                ", dateActivity=" + dateActivity +
                ", nameActivity='" + nameActivity + '\'' +
                ", formatActivity='" + formatActivity + '\'' +
                ", placeActivity='" + placeActivity + '\'' +
                ", region=" + region +
                ", countPeople=" + countPeople +
                ", fileName='" + fileName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }


    @Override
    public Long getIdFromHelper() {
        return id;
    }

    @Override
    public String getNameFromHelper() {
        return nameActivity;
    }
}
