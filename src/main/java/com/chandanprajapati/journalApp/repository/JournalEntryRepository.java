package com.chandanprajapati.journalApp.repository;

import com.chandanprajapati.journalApp.entity.Journalentry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JournalEntryRepository extends MongoRepository<Journalentry, ObjectId> {


}
