package com.harshi_solution.transport.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transportId;

    @Column(unique = true)
    @Length(min = 3, max = 30, message = "transportName should be min 3 and max 30")
    @NotBlank(message = "transportName is mandatory")
    private String transportName;

    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransportAndBuiltNumber> vehicles = new ArrayList<>();

    private String contactDetails;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Constructors, getters, and setters

    public Transport() {
        // Default constructor
    }

    public Transport(String transportName, String contactDetails) {
        this.transportName = transportName;
        this.contactDetails = contactDetails;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Transport [transportId=" + transportId + ", transportName=" + transportName + ", contactDetails="
                + contactDetails + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    public List<TransportAndBuiltNumber> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<TransportAndBuiltNumber> vehicles) {
        this.vehicles = vehicles;
    }

}
