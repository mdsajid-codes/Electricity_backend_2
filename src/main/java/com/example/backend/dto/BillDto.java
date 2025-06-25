package com.example.backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    
    private String username;
    private String billMonth;
    private Double amount;
    private LocalDate dueDate;
    private String status;
    
}
