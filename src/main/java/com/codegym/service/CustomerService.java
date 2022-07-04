package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.DTO.CustomerDTO;
import com.codegym.model.Transfer;
import com.codegym.repository.ITransferRepo;
import org.springframework.stereotype.Service;
import com.codegym.repository.ICustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService {
    @Autowired
    ICustomerRepo customerRepo;
    @Autowired
    ITransferRepo transferRepo;

    @Override
    public List<CustomerDTO> selectAllCustomersDTO() {
        return customerRepo.findAllCustomersDTO();
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customerRepo.findById(id);
    }

    @Override
    public CustomerDTO findCustomerDTOView(Long id) {
        return customerRepo.findCustomerDTOView(id);
    }

    @Override
    public CustomerDTO findCustomerDTOTransaction(Long id) {
        return customerRepo.findCustomerDTOTransaction(id);
    }

    @Override
    public List<CustomerDTO> findAllRecipients(Long id) {
        return customerRepo.findAllRecipients(id);
    }

    @Override
    public CustomerDTO selectCustomerDTOById(Long id) {
        return customerRepo.findCustomerDTOView(id);
    }

    @Override
    public boolean ifExists(Long id) {
        return selectCustomerById(id).isPresent();
    }

    @Override
    public void createCustomer(Customer newCustomer) {
        customerRepo.save(newCustomer);
    }

    @Override
    public void updateCustomer(Customer updatedCustomer) {
        customerRepo.save(updatedCustomer);
    }

    @Override
    public void suspendCustomerById(Long id) {
        customerRepo.suspendCustomer(id);
    }

    @Override
    public boolean ifSuspended(Long id) {
        return customerRepo.findCustomerDTOView(id).isDeleted();
    }

    @Override
    public boolean ifEmailExists(String email) {
        return customerRepo.existsByEmail(email);
    }

    @Override
    public boolean ifPhoneExists(String phone) {
        return customerRepo.existsByPhone(phone);
    }

    @Override
    public boolean ifEmailExistsExceptSelf(String email, Long id) {
        return customerRepo.existsByEmailAndIdIsNot(email, id);
    }

    @Override
    public boolean ifPhoneExistsExceptSelf(String phone, Long id) {
        return customerRepo.existsByPhoneAndIdIsNot(phone, id);
    }

    @Override
    public boolean deposit(Long id, BigDecimal transactionAmount) {
        return customerRepo.deposit(id, transactionAmount);
    }

    @Override
    public boolean withdraw(Long id, BigDecimal transactionAmount) {
        return customerRepo.withdraw(id, transactionAmount);
    }

    @Override
    public boolean transfer(Long senderId, Long recipientId, BigDecimal fee, BigDecimal transactionAmount) {
        return customerRepo.transfer(senderId, recipientId, fee, transactionAmount);
    }

    @Override
    public List<CustomerDTO> selectRecipients(Long senderId) {
        List<CustomerDTO> recipients = selectAllCustomersDTO();
        recipients.removeIf(customerDTO -> (Objects.equals(customerDTO.getId(), senderId)));
        return recipients;
    }

    @Override
    public List<Transfer> selectAllTransfers() {
        return transferRepo.findAll();
    }
}
