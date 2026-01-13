package com.chandanprajapati.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "config_journalapp")
public class Config_journalappEntity {
    private String key;
    private String value;
}
