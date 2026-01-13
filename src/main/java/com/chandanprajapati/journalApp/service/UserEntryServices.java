package com.chandanprajapati.journalApp.service;

import com.chandanprajapati.journalApp.entity.User;
import com.chandanprajapati.journalApp.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserEntryServices {

    @Autowired
    private UserEntryRepository userEntryRepository;

    // Password encoder bean
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // -------------------- SAVE / UPDATE USER --------------------
    public boolean saveNewUser(User user) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userEntryRepository.save(user);
            return true;
        } catch (Exception e) {
            log.info("hahahahhah");
            return false;
        }
    }


    public void saveAdmin(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userEntryRepository.save(user);
    }
    //
  public void saveUser(User user){
        userEntryRepository.save(user);
  }


    // -------------------- GET ALL USERS --------------------
    public List<User> getAll() {
        return userEntryRepository.findAll();
    }

    // -------------------- GET USER BY ID --------------------
    public Optional<User> findbyId(ObjectId id) {
        return userEntryRepository.findById(id);
    }

    // -------------------- DELETE USER BY ID --------------------
    public void deleteById(ObjectId id){
        userEntryRepository.deleteById(id);
    }

    // -------------------- FIND USER BY USERNAME --------------------
    public User findbyUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }
}
