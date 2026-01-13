package com.chandanprajapati.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class emailServices {
  @Autowired
    private JavaMailSender javaMailSender;
  public void sendEmail(String to , String subject ,String body){
    try{
        SimpleMailMessage Mail= new SimpleMailMessage();
        Mail.setFrom("chandanprajapati5658@gmail.com");
        Mail.setTo(to);
        Mail.setSubject(subject);
        Mail.setText(body);
        javaMailSender.send(Mail);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
}
