package com.patika.akbankservice.service;

import com.patika.akbankservice.enums.ApplicationStatus;
import com.patika.akbankservice.model.CreditCard;
import com.patika.akbankservice.model.User;
import com.patika.akbankservice.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Scope(value = "singleton")
@RequiredArgsConstructor
@Slf4j
public class CreditCardService extends ApplicationService {

    private final CreditCardRepository creditCardRepository;

    public List<CreditCard> getAllCreditCardApplications() {
        return creditCardRepository.findAll();
    }

    public CreditCard createCreditCardApplication(CreditCard creditCard, User user) {
        creditCard.setCreatedDate(LocalDate.now());
        creditCard.setUpdatedDate(LocalDate.now());
        creditCard.setApplicationStatus(ApplicationStatus.INITIAL);
        creditCard.setUserID(String.valueOf(user.getId()));
        isUserCustomer(creditCard.getUserID());
        return creditCardRepository.save(creditCard);
    }

    public List<CreditCard> getCreditCardApplications(String userID) {
        return creditCardRepository.findCreditCardsByUserID(userID);
    }

    public List<CreditCard> getCreditCardApplicationsByEmail(String email) {
        User user=getUserByEmail(email);
        return getCreditCardApplications(String.valueOf(user.getId()));
    }

    public CreditCard createCreditCardApplicationByEmail(String email, CreditCard creditCard) {
        User user=getUserByEmail(email);
        return createCreditCardApplication(creditCard,user);

    }








}
