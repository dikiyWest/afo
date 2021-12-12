package kz.atu.uit.afo.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTask;

    private String nameTask;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User careerСounselor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact contact;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enrollee_id")
    private Enrollee enrollee;

    private boolean isActve;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTask() {
        return dateTask;
    }

    public void setDateTask(LocalDateTime dateTask) {
        this.dateTask = dateTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCareerСounselor() {
        return careerСounselor;
    }

    public void setCareerСounselor(User careerСounselor) {
        this.careerСounselor = careerСounselor;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Enrollee getEnrollee() {
        return enrollee;
    }

    public void setEnrollee(Enrollee enrollee) {
        this.enrollee = enrollee;
    }

    public boolean isActve() {
        return isActve;
    }

    public void setActve(boolean actve) {
        isActve = actve;
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

    public Object getType(String type){
        if(type.equals("activity"))
            return activity;
        if(type.equals("contact"))
            return contact;
        if(type.equals("enrollee"))
            return enrollee;
        return null;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", dateTask=" + dateTask +
                ", nameTask='" + nameTask + '\'' +
                ", description='" + description + '\'' +
                ", careerСounselor=" + careerСounselor +
                ", activity=" + activity +
                ", contact=" + contact +
                ", enrollee=" + enrollee +
                ", isActve=" + isActve +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
