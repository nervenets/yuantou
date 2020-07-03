package com.nervenets.general.service;

import java.io.IOException;

public interface MailService {
    void exceptionEmailSend(String fromName, String title, String content, Exception ex) throws IOException;

    void sendExceptions(String fromName, String title, String exceptions, String filePath);

    void sendEmail(String fromName, String[] tos, String title, String content, String filePath);
}
