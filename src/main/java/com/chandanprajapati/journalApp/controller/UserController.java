package com.chandanprajapati.journalApp.controller;
import com.chandanprajapati.journalApp.apiresponse.WeatherResponse;
import com.chandanprajapati.journalApp.entity.User;
import com.chandanprajapati.journalApp.repository.UserEntryRepository;
import com.chandanprajapati.journalApp.service.UserEntryServices;
import com.chandanprajapati.journalApp.service.WeatherServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name="Users APIs",description = "APIs related to logged-in user profile management")
public class UserController {

    @Autowired
    private UserEntryServices userEntryServices;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserEntryRepository userEntryRepository ;
    @Autowired
    private WeatherServices weatherServices;

    @PutMapping
    @Transactional
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userEntryServices.findbyUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userEntryServices.saveNewUser(userInDb);
        return ResponseEntity.noContent().build(); // 204
    }

    @DeleteMapping
    public ResponseEntity<?> deletebyUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherServices.getWeather("Mumbai");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting , HttpStatus.OK);
    }
}



