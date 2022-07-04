package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.DTO.CustomerDTO;
import com.codegym.model.Deposit;
import com.codegym.model.Transfer;
import com.codegym.model.Withdraw;
import com.codegym.service.ICustomerService;
import com.codegym.util.BigDecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.codegym.util.BigDecimalUtils.ONE_HUNDRED;

@Controller
@RequestMapping("/")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping
    private ModelAndView showListCustomer() {
        ModelAndView mav = new ModelAndView("/list-customer");

        List<CustomerDTO> customerList = customerService.selectAllCustomersDTO();
        mav.addObject("customerList", customerList);

        return mav;
    }

    @GetMapping("/create")
    private ModelAndView showCreateForm() {
        ModelAndView mav = new ModelAndView("/create-customer");

        mav.addObject("customerDTO", new CustomerDTO());

        return mav;
    }

    @PostMapping("/create")
    private ModelAndView createCustomer(@Validated @ModelAttribute("customerDTO") CustomerDTO customerDTO,
                                        BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("/create-customer");

        String fullName = customerDTO.getFullName().trim();
        String address = customerDTO.getAddress().trim();
        String email = customerDTO.getEmail().trim();
        String phone = customerDTO.getPhone().trim();

        mav.addObject("customerDTO", customerDTO);

        if (customerService.ifEmailExists(email))
            bindingResult.addError(new ObjectError("EmailTaken", "This email has already been taken"));
        if (customerService.ifPhoneExists(phone))
            bindingResult.addError(new ObjectError("PhoneTaken", "This phone number has already been taken"));
        if (bindingResult.hasErrors()) {
            mav.addObject("success", false);
            return mav;
        }

        try {
            Customer customer = new Customer(fullName, email, phone, address);
            customerService.createCustomer(customer);
            mav.addObject("success", true);
            mav.addObject("message", "Added successfully");
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("Unknown error", "Task failed successfully"));
        }

        return mav;
    }

    @GetMapping("/{id}/edit")
    private ModelAndView showEditForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/edit-customer");

        CustomerDTO customerDTO = customerService.selectCustomerDTOById(id);
        mav.addObject("customerDTO", customerDTO);

        return mav;
    }

    @PostMapping("/{id}/edit")
    private ModelAndView editCustomer(@PathVariable Long id,
                                      @Validated @ModelAttribute("customerDTO") CustomerDTO customerDTO,
                                      BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("/edit-customer");

        mav.addObject("customerDTO", customerDTO);

        String fullName = customerDTO.getFullName().trim();
        String phone = customerDTO.getPhone().trim();
        String email = customerDTO.getEmail().trim();
        String address = customerDTO.getAddress().trim();

        if (customerService.ifEmailExistsExceptSelf(email, id))
            bindingResult.addError(new ObjectError("EmailTaken", "This email has already been taken"));
        if (customerService.ifPhoneExistsExceptSelf(phone, id))
            bindingResult.addError(new ObjectError("PhoneTaken", "This phone number has already been taken"));
        if (bindingResult.hasErrors()) {
            mav.addObject("success", false);
            return mav;
        }
        try {
            Customer customer = new Customer(fullName, email, phone, address);
            customer.setId(id);
            customerService.updateCustomer(customer);
            mav.addObject("success", true);
            mav.addObject("messages", "Edited successfully");
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("UnknownError", "Task failed successfully"));
        }

        return  mav;
    }

    @GetMapping("/{id}/suspend")
    private ModelAndView showSuspendCustomer(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/suspend-customer");

        CustomerDTO customerDTO = customerService.selectCustomerDTOById(id);
        mav.addObject("customerDTO", customerDTO);

        return mav;
    }

    @PostMapping("/{id}/suspend")
    private ModelAndView suspendCustomer(@PathVariable Long id,
                                         @Validated @ModelAttribute("customerDTO") CustomerDTO customerDTO,
                                         BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("/suspend-customer");

        mav.addObject("customerDTO", customerDTO);

        if (customerService.ifSuspended(id)) {
            bindingResult.addError(new ObjectError("CustomerSuspended", "This customer has already been suspended"));
            mav.addObject("success", false);
            return mav;
        }
        try {
            customerService.suspendCustomerById(id);
            mav.addObject("success", true);
            mav.addObject("message", "Suspended successfully");
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("Unknown error", "Task failed successfully"));
        }

        return mav;
    }

    @GetMapping("/{id}/deposit")
    private ModelAndView showDepositForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/transaction/deposit");

        Customer customer = customerService.selectCustomerById(id).get();
        Deposit deposit = new Deposit();

        deposit.setCustomer(customer);

        mav.addObject("deposit", deposit);

        return mav;
    }

    @PostMapping("/{id}/deposit")
    private ModelAndView deposit(@PathVariable Long id,
                                 @Validated @ModelAttribute("deposit") Deposit deposit,
                                 BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("/transaction/deposit");

        Customer customer = customerService.selectCustomerById(id).get();
        deposit.setCustomer(customer);
        mav.addObject("deposit", deposit);

        BigDecimal transactionAmount = deposit.getTransactionAmount();

        if (customerService.ifSuspended(id))
            bindingResult.addError(new ObjectError("CustomerSuspended", "This customer has already been suspended"));
        if (bindingResult.hasErrors()) {
            mav.addObject("success", false);
            return mav;
        }
        try {
            customerService.deposit(id, transactionAmount);
            mav.addObject("success", true);
            mav.addObject("message", "Deposit successfully");
            customer = customerService.selectCustomerById(id).get();
            deposit.setCustomer(customer);
            mav.addObject("deposit", deposit);
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("Unknown error", "Task failed successfully"));
        }

        return mav;
    }

    @GetMapping("/{id}/withdraw")
    private ModelAndView showWithdrawForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/transaction/withdraw");

        Customer customer = customerService.selectCustomerById(id).get();
        Withdraw withdraw = new Withdraw();

        withdraw.setCustomer(customer);

        mav.addObject("withdraw", withdraw);

        return mav;
    }

    @PostMapping("/{id}/withdraw")
    private ModelAndView withdraw(@PathVariable Long id,
                                 @Validated @ModelAttribute("withdraw") Withdraw withdraw,
                                 BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("/transaction/withdraw");

        Customer customer = customerService.selectCustomerById(id).get();
        withdraw.setCustomer(customer);
        mav.addObject("withdraw", withdraw);

        BigDecimal transactionAmount = withdraw.getTransactionAmount();

        if (customerService.ifSuspended(id))
            bindingResult.addError(new ObjectError("CustomerSuspended", "This customer has already been suspended"));
        if (bindingResult.hasErrors()) {
            mav.addObject("success", false);
            return mav;
        }
        try {
            customerService.withdraw(id, transactionAmount);
            mav.addObject("success", true);
            mav.addObject("message", "Withdraw successfully");
            customer = customerService.selectCustomerById(id).get();
            withdraw.setCustomer(customer);
            mav.addObject("withdraw", withdraw);
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("Unknown error", "Task failed successfully"));
        }

        return mav;
    }

    @GetMapping("/{id}/transfer")
    private ModelAndView showTransferForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/transaction/transfer");

        Customer sender = customerService.selectCustomerById(id).get();
        List<CustomerDTO> recipients = customerService.selectRecipients(id);
        Transfer transfer = new Transfer();

        transfer.setSender(sender);

        mav.addObject("transfer", transfer);
        mav.addObject("recipients", recipients);

        return mav;
    }

    @PostMapping("{id}/transfer")
    private ModelAndView transfer(@PathVariable Long id, @RequestParam("recipientId") String rawRecipientId,
                                  @Validated @ModelAttribute("transfer") Transfer transfer,
                                  BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("/transaction/transfer");

        Long recipientId = null;
        Customer sender = customerService.selectCustomerById(id).get();
        List<CustomerDTO> recipients = customerService.selectRecipients(id);
        transfer.setSender(sender);
        mav.addObject("transfer", transfer);
        mav.addObject("recipients", recipients);
        mav.addObject("recipientId", rawRecipientId);


        BigDecimal transactionAmount = transfer.getTransactionAmount();
        BigDecimal fee = transfer.getFee();
        BigDecimal totalAmount = BigDecimalUtils.percentage(transactionAmount, fee.add(ONE_HUNDRED));

        if (totalAmount.compareTo(sender.getBalance()) > 0)
            bindingResult.addError(new ObjectError("BalanceInsufficient", "Insufficient balance."));
        try {
            recipientId = Long.parseLong(rawRecipientId);
            Customer recipient = customerService.selectCustomerById(recipientId).get();
            if (Objects.equals(recipient.getId(), id))
                throw new NoSuchElementException();
        } catch (NoSuchElementException | NumberFormatException e) {
            bindingResult.addError(new ObjectError("RecipientInvalid", "Recipient Invalid"));
        }
        if (customerService.ifSuspended(id))
            bindingResult.addError(new ObjectError("CustomerSuspended", "This customer has already been suspended"));
        if (bindingResult.hasErrors()) {
            mav.addObject("success", false);
            return mav;
        }

        try {
            customerService.transfer(id, recipientId, fee, transactionAmount);
            mav.addObject("success", true);
            mav.addObject("message", "Transfer successfully");
            sender = customerService.selectCustomerById(id).get();
            transfer.setSender(sender);
            mav.addObject("transfer", transfer);
            //recipient
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("Unknown error", "Task failed successfully"));
        }

        return mav;
    }

    @GetMapping("/transfer-history")
    private ModelAndView showTransferHistory() {
        ModelAndView mav = new ModelAndView("transaction/transfer-history");

        List<Transfer> transferList = customerService.selectAllTransfers();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Transfer transfer : transferList) {
            totalAmount.add(transfer.getTransactionAmount());
        }

        mav.addObject("transferList", transferList);
        mav.addObject("totalAmount", totalAmount);
        return mav;
    }
}
