package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.exceptions.ExceptionMessages;
import com.patika.kredinbizdeservice.exceptions.KredinbizdeException;
import com.patika.kredinbizdeservice.model.Bank;
import com.patika.kredinbizdeservice.model.User;
import com.patika.kredinbizdeservice.repository.BankRepository;
import com.patika.kredinbizdeservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Scope(value = "singleton")
@RequiredArgsConstructor
@Slf4j
public class BankService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;

    public Bank save(Bank bank) {
        bank.setUpdatedDate(LocalDate.now());
        bank.setCreatedDate(LocalDate.now());
        return bankRepository.save(bank);
    }


    public User findCustomerBanks(Long bankID, Long userID) {
        Optional<Bank> bankOptional = bankRepository.findById(bankID);
        if (bankOptional.isPresent()) {
            Bank bank = bankOptional.get();
            List<User> customers = bank.getCustomers();
            return findUserinCustomers(customers, userID);
        } else {
            throw new KredinbizdeException("Bank not found");
        }
    }

    public User findUserinCustomers(List<User> customers, Long customerID) {
        Optional<User> foundUser = customers.stream()
                .filter(user -> user.getId().equals(customerID))
                .findAny();


        User returnUser = foundUser.orElseThrow(() -> new KredinbizdeException("This user is not customer of this bank."));

        if (foundUser.isPresent()) {
            returnUser = foundUser.get();
        }

        return returnUser;
    }

    public Bank addCustomer(Long bankID, String email) {
        Optional<Bank> bankOptional = bankRepository.findById(bankID);
        Bank notFoundBank = bankOptional.orElseThrow(() -> new KredinbizdeException("Bank not found."));

        if (bankOptional.isPresent()) {
            Bank bank = bankOptional.get();
            Optional<User> foundUser = userRepository.findByEmail(email);

            if (foundUser.isPresent()) {
                User user = foundUser.get();
                boolean anyMatch = bank.getCustomers().stream().anyMatch(customer -> user.getEmail().equals(customer.getEmail()));
                if (!anyMatch) {
                    bank.getCustomers().add(user);
                    return save(bank);
                } else {//if already exists, no change
                    return bank;
                }

            } else {
                throw new KredinbizdeException("No user found with this email.");
            }

        }
        return notFoundBank;
    }

    public Bank deleteCustomer(Long bankID, String email) {
        Optional<Bank> bankOptional = bankRepository.findById(bankID);
        Bank notFoundBank = bankOptional.orElseThrow(() -> new KredinbizdeException("Bank not found"));

        if (bankOptional.isPresent()) {
            Bank bank = bankOptional.get();
            Optional<User> foundUser = userRepository.findByEmail(email);

            if (foundUser.isPresent()) {
                User user = foundUser.get();
                List<User> customers = bank.getCustomers();
                boolean isCustomer = bank.getCustomers().stream().anyMatch(customer -> user.getEmail().equals(customer.getEmail()));
                if (isCustomer) {
                    customers.removeIf(customer -> user.getEmail().equals(customer.getEmail()));
                    bank.setCustomers(customers);
                    return save(bank);
                } else {
                    throw new KredinbizdeException("User is not customer of this bank.");
                }

            } else {
                throw new KredinbizdeException("User not found with this email.");
            }

        }
        return notFoundBank;
    }

    public List<User> getAllCustomers(Long bankID) {
        Optional<Bank> bankOptional = bankRepository.findById(bankID);
        if (bankOptional.isPresent()) {
            return bankOptional.get().getCustomers();
        } else {
            throw new KredinbizdeException("Bank not found with this ID.");
        }
    }
}
