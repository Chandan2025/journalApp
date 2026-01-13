package com.chandanprajapati.journalApp.appcache;
import com.chandanprajapati.journalApp.entity.Config_journalappEntity;
import com.chandanprajapati.journalApp.repository.Config_journalappRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class cache {
    @Autowired
    private Config_journalappRepo configJournalappRepo;
     public static Map<String,String> APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE=new HashMap<>();
        List<Config_journalappEntity>all=configJournalappRepo.findAll();
        for(Config_journalappEntity configJournalappEntity:all){
            APP_CACHE.put(configJournalappEntity.getKey(),configJournalappEntity.getValue());

        }

    }

}



