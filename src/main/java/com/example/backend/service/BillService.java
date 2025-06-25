package com.example.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.Repository.BillRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.dto.BillDto;
import com.example.backend.model.Bill;
import com.example.backend.model.User;

@Service
public class BillService {
    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private BillRepository billRepository;

    public Bill addBill(BillDto dto){
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(()-> new RuntimeException("Username not found"));

        Bill bill = new Bill();

        bill.setUser(user);
        bill.setBillMonth(dto.getBillMonth());
        bill.setAmount(dto.getAmount());
        bill.setDueDate(dto.getDueDate());
        bill.setStatus(dto.getStatus());

        return billRepository.save(bill);
    }

    public Bill getBillByUsernameAndMonth(String username, String billMonth){
        return billRepository.findByUsernameAndMonth(username, billMonth).orElseThrow(()-> new RuntimeException("Bill Not Found!"));
    }

    public List<Bill> getBillUsername(String username){
        return billRepository.findByUserUsername(username);
    }

    public Bill save(Bill bill){
        return billRepository.save(bill);
    }
}
