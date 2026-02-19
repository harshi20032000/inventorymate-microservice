package com.harshi_solution.transport.dto;

import java.time.Instant;

public class VehicleResponseDTO {

    private Long id;
    private String builtNumber;
    private String driverName;
    private String driverContact;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuiltNumber() {
        return builtNumber;
    }

    public void setBuiltNumber(String builtNumber) {
        this.builtNumber = builtNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverContact() {
        return driverContact;
    }

    public void setDriverContact(String driverContact) {
        this.driverContact = driverContact;
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

    public VehicleResponseDTO(Long id, String builtNumber, String driverName, String driverContact, Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.builtNumber = builtNumber;
        this.driverName = driverName;
        this.driverContact = driverContact;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public VehicleResponseDTO() {
    }

}
