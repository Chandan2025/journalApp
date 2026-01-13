package com.chandanprajapati.journalApp.controller;

import com.chandanprajapati.journalApp.entity.Journalentry;
import com.chandanprajapati.journalApp.entity.User;
import com.chandanprajapati.journalApp.service.JournalEntryServices;
import com.chandanprajapati.journalApp.service.UserEntryServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.chandanprajapati.journalApp.service.JournalEntryServices.*;


@RestController
@RequestMapping("/journal")
@Tag(
        name = "Journal APIs",
        description = "APIs for creating, reading, updating and deleting journal entries for the logged-in user"
)
public class JournalEntryControllerV2 {

  @Autowired
  private JournalEntryServices journalEntryServices;
  @Autowired
  private UserEntryServices userEntryServices;

    @GetMapping()
    public ResponseEntity<?> getallbyuser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userEntryServices.findbyUserName(userName);
        List<Journalentry> all = user.getJournalentries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Journalentry>createEntry(@RequestBody Journalentry myentry){
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryServices.saveEntry(myentry,userName);
        return new ResponseEntity<>(myentry, HttpStatus.CREATED);
    }catch(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    }
    @GetMapping("id/{myid}")
    public ResponseEntity<Journalentry> getJournalEntrybyid(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
          User user= userEntryServices.findbyUserName(userName);
          List<Journalentry>collect=user.getJournalentries().stream().filter(x ->x.getId().equals(myid)).collect(Collectors.toList());
          if(!collect.isEmpty()) {
              Optional<Journalentry> journalentry = journalEntryServices.findbyId(myid);

              if(journalentry.isPresent()){
                  return new ResponseEntity<>(journalentry.get(),HttpStatus.OK);
              }
          }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping ("id/{myid}")
    public  ResponseEntity<?> deleteJournalEntrybyid(@PathVariable ObjectId myid ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
      boolean removed=journalEntryServices.deleteById(myid,userName);
      if(removed) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      else{
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    @PutMapping("/id/{myid}")
    public  ResponseEntity<?> updateJournalEntrybyid(@PathVariable ObjectId myid ,@RequestBody Journalentry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user= userEntryServices.findbyUserName(userName);
        List<Journalentry>collect=user.getJournalentries().stream().filter(x ->x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<Journalentry> journalentry = journalEntryServices.findbyId(myid);

            if(journalentry.isPresent()){
                Journalentry old= journalentry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryServices.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }
}
