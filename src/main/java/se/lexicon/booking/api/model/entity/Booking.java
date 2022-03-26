package se.lexicon.booking.api.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false)
    private String id;
    private LocalDateTime dateTime;
    private BigDecimal price;
    private String administratorId;
    private String vaccineType;
    private boolean vacant;
    private String patient;
    private String premises;

    public Booking(String id, LocalDateTime dateTime, BigDecimal price, String administratorId, String vaccineType, boolean vacant) {
        this.id = id;
        this.dateTime = dateTime;
        this.price = price;
        this.administratorId = administratorId;
        this.vaccineType = vaccineType;
        this.vacant = vacant;
    }

    public Booking() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(String administratorId) {
        this.administratorId = administratorId;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public boolean isVacant() {
        return vacant;
    }

    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
        setVacant(this.patient == null);
    }

    public String getPremises() {
        return premises;
    }

    public void setPremises(String premises) {
        this.premises = premises;
    }
}
