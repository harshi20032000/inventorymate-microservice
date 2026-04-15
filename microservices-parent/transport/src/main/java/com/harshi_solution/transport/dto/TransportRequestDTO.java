package com.harshi_solution.transport.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class TransportRequestDTO {

    @NotBlank(message = "transportName is mandatory")
    @Size(min = 3, max = 30)
    private String transportName;

    private String contactDetails;

    @NotEmpty(message = "At least one vehicle is required")
    private List<@Valid VehicleRequestDTO> vehicles;

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

    public List<VehicleRequestDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleRequestDTO> vehicles) {
        this.vehicles = vehicles;
    }

    public TransportRequestDTO(
            @NotBlank(message = "transportName is mandatory") @Size(min = 3, max = 30) String transportName,
            String contactDetails,
            @NotEmpty(message = "At least one vehicle is required") List<@Valid VehicleRequestDTO> vehicles) {
        this.transportName = transportName;
        this.contactDetails = contactDetails;
        this.vehicles = vehicles;
    }

}
