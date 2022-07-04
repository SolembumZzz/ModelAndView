package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.DTO.CustomerDTO;
import com.codegym.model.Transfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    List<CustomerDTO> selectAllCustomersDTO();

    Optional<Customer> selectCustomerById(Long id);

    CustomerDTO findCustomerDTOView(Long id);

    CustomerDTO findCustomerDTOTransaction(Long id);

    List<CustomerDTO> findAllRecipients(Long id);

    CustomerDTO selectCustomerDTOById(Long id);

    boolean ifExists(Long id);

    void createCustomer(Customer newCustomer);

    void updateCustomer(Customer updatedCustomer);

    void suspendCustomerById(Long id);

    boolean ifEmailExists(String email);

    boolean ifEmailExistsExceptSelf(String email, Long id);
    boolean ifPhoneExists(String phone);

    boolean ifPhoneExistsExceptSelf(String phone, Long id);

    boolean ifSuspended(Long id);

    boolean deposit(Long id, BigDecimal transactionAmount);

    boolean withdraw(Long id, BigDecimal transactionAmount);

    boolean transfer(Long senderId, Long recipientId, BigDecimal fee, BigDecimal transactionAmount);

    List<CustomerDTO> selectRecipients(Long senderId);

    List<Transfer> selectAllTransfers();
}
