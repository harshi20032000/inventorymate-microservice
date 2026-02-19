package com.harshi_solution.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RepRequestDTO {

    @NotBlank(message = "repName is mandatory")
    @Size(min = 3, max = 30, message = "repName should be min 3 and max 30")
    private String repName;

    @NotBlank(message = "repLocation is mandatory")
    @Size(min = 3, max = 30, message = "repLocation should be min 3 and max 30")
    private String repLocation;

    public RepRequestDTO() {}

    public RepRequestDTO(String repName, String repLocation) {
        this.repName = repName;
        this.repLocation = repLocation;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRepLocation() {
        return repLocation;
    }

    public void setRepLocation(String repLocation) {
        this.repLocation = repLocation;
    }
}

