package cn.neorae.wtu.module.mail;

import jakarta.mail.MessagingException;

public interface MailService {

    void verifyAccount(String receiver, String title, String url) throws MessagingException;

    void recoverAccount(String receiver, String title, String url) throws MessagingException;
}
