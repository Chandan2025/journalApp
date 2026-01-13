package com.chandanprajapati.journalApp.controller;
import com.chandanprajapati.journalApp.appcache.cache;
import com.chandanprajapati.journalApp.entity.User;
import com.chandanprajapati.journalApp.service.UserEntryServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/admin")
@Tag(name="Admin APIs", description = "Admin-only APIs for managing users and application cache")
public class AdminController {
    @Autowired
    private UserEntryServices userEntryServices;
    @Autowired
    private cache appcache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all=userEntryServices.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("create_Admin_user")
    public void createUser(@RequestBody User user){
        userEntryServices.saveAdmin(user);
    }
    @GetMapping("clear-app-cache")
    public void appClearCache(){
        appcache.init();
    }
}
