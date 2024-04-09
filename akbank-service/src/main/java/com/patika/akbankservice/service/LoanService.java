package com.patika.akbankservice.service;

import com.patika.akbankservice.model.Loan;
import com.patika.akbankservice.model.User;
import com.patika.akbankservice.enums.ApplicationStatus;
import com.patika.akbankservice.repository.LoanRepository;
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
public class LoanService extends   ApplicationService{

    private final LoanRepository loanRepository;

    public List<Loan> getAllLoanApplications() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoanApplications(String userID) {
        return loanRepository.findLoansByUserID(userID);
    }

    public List<Loan> getLoanApplicationsByEmail(String email) {
        User user=getUserByEmail(email);
        return getLoanApplications(String.valueOf(user.getId()));
    }

    public Loan createLoanApplication(Loan loan, User user) {
        loan.setCreatedDate(LocalDate.now());
        loan.setUpdatedDate(LocalDate.now());
        loan.setApplicationStatus(ApplicationStatus.INITIAL);
        loan.setUserID(String.valueOf(user.getId()));
        isUserCustomer(loan.getUserID());
        return loanRepository.save(loan);
    }

    public Loan createLoanApplicationByEmail(String email, Loan loan) {
        User user=getUserByEmail(email);
        return createLoanApplication(loan,user);
    }

}
