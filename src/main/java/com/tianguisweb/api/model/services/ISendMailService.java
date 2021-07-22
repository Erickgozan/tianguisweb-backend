package com.tianguisweb.api.model.services;

public interface ISendMailService {
    void sendMail(String from,String to,String subject, String body);
}
