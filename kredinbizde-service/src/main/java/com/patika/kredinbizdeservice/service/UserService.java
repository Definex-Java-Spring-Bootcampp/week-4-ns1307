package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.exceptions.KredinbizdeException;
import com.patika.kredinbizdeservice.model.User;
import com.patika.kredinbizdeservice.producer.NotificationProducer;
import com.patika.kredinbizdeservice.producer.dto.NotificationDTO;
import com.patika.kredinbizdeservice.producer.enums.NotificationType;
import com.patika.kredinbizdeservice.repository.UserRepository;
import jakarta.transaction.Transactional;
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
public class UserService {

    private final UserRepository userRepository;

    private final NotificationProducer notificationProducer;

    //@CacheEvict(value = "users", allEntries = true)
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = KredinbizdeException.class)
    public User save(User user) {
        user.setUpdatedDate(LocalDate.now());
        user.setCreatedDate(LocalDate.now());
        User savedUser = userRepository.save(user);
        notificationProducer.sendNotification(prepareNotificationDTO(NotificationType.EMAIL, user.getEmail()));
        return savedUser;
    }


    private NotificationDTO prepareNotificationDTO(NotificationType notificationType, String email) {
        return NotificationDTO.builder()
                .message("user kaydedildi.")
                .notificationType(notificationType)
                .email(email)
                .build();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByEmail(String email) {

        Optional<User> foundUser = userRepository.findByEmail(email);

        User user = foundUser.orElseThrow(() -> new KredinbizdeException("No user found with this email."));

        if (foundUser.isPresent()) {
            user = foundUser.get();
        }

        return user;

    }

    public User update(String email, User newUser) {

        Optional<User> foundUser = userRepository.findByEmail(email);

        User user = foundUser.orElseThrow(() -> new KredinbizdeException("No user found with this email."));
        if (foundUser.isPresent()) {
            user = foundUser.get();
            if (!newUser.getName().isEmpty()) {
                user.setName(newUser.getName());
            }
            if (!newUser.getSurname().isEmpty()) {
                user.setSurname(newUser.getSurname());
            }
            if (!newUser.getPassword().isEmpty()) {
                user.setPassword(newUser.getPassword());
            }
            if (!newUser.getEmail().isEmpty()) {
                user.setEmail(newUser.getEmail());
            }
            if (newUser.getAddress() != null) {
                user.setAddress(newUser.getAddress());
            }
            user = userRepository.save(user);//save method can update object if it exists.
        }

        return user;
    }

    public User deleteByEmail(String email) {//used for deleting test users only
        Optional<User> foundUser = userRepository.findByEmail(email);

        User user = foundUser.orElseThrow(() -> new KredinbizdeException("No user found with this email."));
        if (foundUser.isPresent()) {
            user = foundUser.get();
            userRepository.delete(user);
        }
        return user;
    }
}
