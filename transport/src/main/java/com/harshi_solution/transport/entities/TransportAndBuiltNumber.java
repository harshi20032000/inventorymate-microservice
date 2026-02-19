package com.harshi_solution.transport.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transport_and_built_number")
public class TransportAndBuiltNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;

    @Column(nullable = false)
    private String driverName;

    @Column(nullable = false)
    private String driverContact;

    private String builtNumber;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Constructors, getters, and setters

    public TransportAndBuiltNumber() {
        // Default constructor
    }

    public TransportAndBuiltNumber(Transport transport, String builtNumber) {
        this.transport = transport;
        this.builtNumber = builtNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public String getBuiltNumber() {
        return builtNumber;
    }

    public void setBuiltNumber(String builtNumber) {
        this.builtNumber = builtNumber;
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

    @Override
    public String toString() {
        return "TransportAndBuiltNumber [id=" + id + ", transport=" + transport + ", builtNumber=" + builtNumber
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

}
