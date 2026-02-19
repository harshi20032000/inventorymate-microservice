package com.harshi_solution.user.dto;


public class RepResponseDTO {

    private Long repId;
    private String repName;
    private String repLocation;
    private String username; 

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RepResponseDTO() {}

    public RepResponseDTO(Long repId, String repName, String repLocation) {
        this.repId = repId;
        this.repName = repName;
        this.repLocation = repLocation;
    }

    public RepResponseDTO(Long repId, String repName, String repLocation, String username) {
        this.repId = repId;
        this.repName = repName;
        this.repLocation = repLocation;
        this.username=username;
    }

    public Long getRepId() {
        return repId;
    }

    public void setRepId(Long repId) {
        this.repId = repId;
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

