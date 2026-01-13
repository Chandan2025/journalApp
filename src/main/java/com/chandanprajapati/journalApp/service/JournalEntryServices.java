package com.chandanprajapati.journalApp.service;
import com.chandanprajapati.journalApp.Enum.Sentiment;
import com.chandanprajapati.journalApp.entity.Journalentry;
import com.chandanprajapati.journalApp.entity.User;
import com.chandanprajapati.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryServices userEntryServices;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private emailServices email;
    @Transactional
    public void saveEntry(Journalentry journalentry, String userName){
       try{ User user= userEntryServices.findbyUserName(userName);
           journalentry.setDate(LocalDateTime.now());
        Journalentry saved = journalEntryRepository.save(journalentry);
        user.getJournalentries().add(saved);
           System.out.println("TITLE = " + journalentry.getTitle());
           System.out.println("CONTENT = " + journalentry.getContent());
        userEntryServices.saveUser(user);
        processSentimentAndEmail(saved, user);
       }
       catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    public void saveEntry(Journalentry journalentry){
        journalEntryRepository.save(journalentry);
    }

    public List<Journalentry> getAll(){
        return journalEntryRepository.findAll();
    }


    public Optional<Journalentry> findbyId(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed= false;
        try{User user= userEntryServices.findbyUserName(userName);
         removed = user.getJournalentries().removeIf(x ->x.getId().equals(id));
        if(removed) {
            userEntryServices.saveUser(user);
            journalEntryRepository.deleteById(id);
        }} catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error ocurred while deleting the entry", e);
        }
        return removed;
    }
    @Async
    public void processSentimentAndEmail(Journalentry entry, User user) {

        String emailBody =
                sentimentAnalysisService.generateEmailBody(entry.getContent(),user.getUserName());

        String subject = "Your Journal Sentiment Analysis";

        email.sendEmail(
                user.getEmail(),
                subject,
                emailBody
        );
    }


}
