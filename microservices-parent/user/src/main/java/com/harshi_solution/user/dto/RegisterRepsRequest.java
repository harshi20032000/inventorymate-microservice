package com.harshi_solution.user.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterRepsRequest {
    
    @NotBlank
    private String repName;

    @NotBlank
    private String repLocation;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
