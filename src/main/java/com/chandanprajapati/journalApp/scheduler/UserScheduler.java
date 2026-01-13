package com.chandanprajapati.journalApp.scheduler;

import com.chandanprajapati.journalApp.Enum.Sentiment;
import com.chandanprajapati.journalApp.appcache.cache;
import com.chandanprajapati.journalApp.entity.Journalentry;
import com.chandanprajapati.journalApp.entity.User;
import com.chandanprajapati.journalApp.repository.UserEntryRepoImpl;
import com.chandanprajapati.journalApp.service.emailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private emailServices emailservices;
    @Autowired
    private UserEntryRepoImpl userEntryRepoImpl;

    @Autowired
    private cache appcache;

    //@Scheduled(cron = "0 0 9 * * SUN")

    public void fetchuserandsendMail(){
      List<User> users= userEntryRepoImpl.getUserSA();
        System.out.println("USERS FOUND = " + users.size());
     for(User user:users ){
         List<Journalentry>journalentries= user.getJournalentries();
         List<Sentiment> sentiments = journalentries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
         Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
         for (Sentiment sentiment : sentiments) {
             if (sentiment != null)
                 sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
         }
         Sentiment mostFrequentSentiment = null;
         int maxCount = 0;
         for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
             if (entry.getValue() > maxCount) {
                 maxCount = entry.getValue();
                 mostFrequentSentiment = entry.getKey();
             }
         }
         if (mostFrequentSentiment != null) {     

             emailservices.sendEmail(user.getEmail(),"Sentiment for last 7 days ",mostFrequentSentiment.toString());
         }
         System.out.println("Users fetched = " + users.size());
     }
 }
 @Scheduled(cron = "0 * * ? * *")
 public void clearAppcache(){
   appcache.init();
 }
}
