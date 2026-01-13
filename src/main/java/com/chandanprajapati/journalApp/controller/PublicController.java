package com.chandanprajapati.journalApp.controller;

import com.chandanprajapati.journalApp.dto.UserDTO;
import com.chandanprajapati.journalApp.entity.User;
import com.chandanprajapati.journalApp.service.UserDetailsServiceImpl;
import com.chandanprajapati.journalApp.service.UserEntryServices;
import com.chandanprajapati.journalApp.utils.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name ="login & signup APIs",description = "Publicly accessible APIs such as login, user registration, and health check")
public class PublicController {
    @Autowired
    private UserEntryServices userEntryServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("health-check")
    public String healthCheck()
    {
         return "ok";
    }

  @PostMapping("/create-user")
  public void createUser(@RequestBody UserDTO user){
        User newUser= new User();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        userEntryServices.saveNewUser(newUser);
  }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
       try {
           authenticationManager.authenticate(new
                   UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
           String jwt = jwtUtils.generateToken(userDetails.getUsername());
           return new ResponseEntity<>(jwt, HttpStatus.OK);

       }catch(Exception e){
         log.error("Exception occurred while createAuthenticationToken",e);
         return new ResponseEntity<>("Incorrect username or password ",HttpStatus.BAD_REQUEST);

       }
    }


}
