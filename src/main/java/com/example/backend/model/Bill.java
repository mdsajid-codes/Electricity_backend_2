package com.example.backend.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "bills", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "bill_month"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String billMonth;
    private Double amount;
    private LocalDate dueDate;
    private String status;

    private String filePath;

    @ManyToOne
    @JoinColumn (name = "user_id")
    @JsonIgnore
    private User user;
}