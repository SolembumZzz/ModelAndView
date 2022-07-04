package com.codegym.repository;


import com.codegym.model.Customer;
import com.codegym.model.DTO.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ICustomerRepo extends JpaRepository<Customer, Long> {

    @Query("SELECT NEW com.codegym.model.DTO.CustomerDTO (c.id, c.fullName, c.email, c.phone, c.address, c.balance, c.deleted) " +
            "FROM Customer AS c " +
            "WHERE c.deleted = false")
    List<CustomerDTO> findAllCustomersDTO();

    @Query("SELECT NEW com.codegym.model.DTO.CustomerDTO (c.id, c.fullName, c.email, c.balance, c.deleted) " +
            "FROM Customer AS c WHERE c.id=:id")
    CustomerDTO findCustomerDTOTransaction(@Param("id") Long id);

    @Query("SELECT NEW com.codegym.model.DTO.CustomerDTO (c.id, c.fullName, c.email, c.phone, c.address, c.deleted) " +
            "FROM Customer AS c WHERE c.id=:id")
    CustomerDTO findCustomerDTOView(@Param("id") Long id);

    @Query("SELECT NEW com.codegym.model.DTO.CustomerDTO (c.id, c.fullName) " +
            "FROM Customer AS c " +
            "WHERE c.id<>?1 AND c.deleted = false")
    List<CustomerDTO> findAllRecipients(Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdIsNot(String email, Long id);

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdIsNot(String phone, Long id);

    @Modifying
    @Query("UPDATE Customer AS c " +
            "SET c.deleted = true WHERE c.id=?1")
    void suspendCustomer(Long id);

    @Modifying
    @Procedure("sp_deposit")
    boolean deposit(Long id, BigDecimal transactionAmount);

    @Modifying
    @Procedure("sp_withdraw")
    boolean withdraw(Long id, BigDecimal transactionAmount);

    @Modifying
    @Procedure("sp_transfer")
    boolean transfer(Long senderId, Long recipientId, BigDecimal fee, BigDecimal transactionAmount);
}
