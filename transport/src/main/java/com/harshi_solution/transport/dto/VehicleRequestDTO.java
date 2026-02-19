package com.harshi_solution.transport.dto;

import jakarta.validation.constraints.NotBlank;

public class VehicleRequestDTO {

    @NotBlank(message = "builtNumber is mandatory")
    private String builtNumber;

    @NotBlank(message = "driverName is mandatory")
    private String driverName;

    @NotBlank(message = "driverContact is mandatory")
    private String driverContact;

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

    public VehicleRequestDTO(@NotBlank(message = "builtNumber is mandatory") String builtNumber,
            @NotBlank(message = "driverName is mandatory") String driverName,
            @NotBlank(message = "driverContact is mandatory") String driverContact) {
        this.builtNumber = builtNumber;
        this.driverName = driverName;
        this.driverContact = driverContact;
    }

}
