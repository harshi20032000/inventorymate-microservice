package com.harshi_solution.transport.dto;

import java.time.Instant;
import java.util.List;

public class TransportResponseDTO {

    private Long transportId;
    private String transportName;
    private String contactDetails;
    private List<VehicleResponseDTO> vehicles;
    private Instant createdAt;
    private Instant updatedAt;

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

    public List<VehicleResponseDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleResponseDTO> vehicles) {
        this.vehicles = vehicles;
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

    public TransportResponseDTO(Long transportId, String transportName, String contactDetails,
            List<VehicleResponseDTO> vehicles, Instant createdAt, Instant updatedAt) {
        this.transportId = transportId;
        this.transportName = transportName;
        this.contactDetails = contactDetails;
        this.vehicles = vehicles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public TransportResponseDTO() {
    }

}
