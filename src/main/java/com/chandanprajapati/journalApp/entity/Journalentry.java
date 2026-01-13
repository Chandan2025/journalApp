package com.chandanprajapati.journalApp.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
public class Journalentry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private LocalDateTime date;
    private String content;
}
