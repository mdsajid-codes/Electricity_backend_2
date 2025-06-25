package com.example.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b from Bill b WHERE b.user.username = :username AND b.billMonth = :month")
    Optional<Bill> findByUsernameAndMonth(@Param ("username") String username, @Param ("month") String month);

    List<Bill> findByUserUsername(String username);
}
