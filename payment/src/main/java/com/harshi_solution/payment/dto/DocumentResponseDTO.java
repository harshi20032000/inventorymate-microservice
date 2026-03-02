package com.harshi_solution.payment.dto;

import java.time.LocalDate;

public class DocumentResponseDTO {

    private Long docId;

    private String name;

    private LocalDate documentUpdatedDate;

     public LocalDate getDocumentUpdatedDate() {
        return documentUpdatedDate;
    }

    public void setDocumentUpdatedDate(LocalDate documentUpdatedDate) {
        this.documentUpdatedDate = documentUpdatedDate;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
