package com.chandanprajapati.journalApp.repository;

import com.chandanprajapati.journalApp.entity.Journalentry;
import com.chandanprajapati.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<User, ObjectId> {

User findByUserName(String userName);
void deleteByUserName(String username);
}
