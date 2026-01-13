package com.chandanprajapati.journalApp.repository;

import com.chandanprajapati.journalApp.entity.Config_journalappEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Config_journalappRepo extends MongoRepository<Config_journalappEntity, ObjectId> {

}
