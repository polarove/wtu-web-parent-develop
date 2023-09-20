package cn.neorae.wtu.module.mail.impl;

import cn.neorae.wtu.module.mail.MailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;

    @Override
    public void verifyAccount(String receiver, String title, String url) throws MessagingException {
        Context context = new Context();
        context.setVariable("url", url);
        log.info("url:{}", url);
        String htmlContent = templateEngine.process("access", context);
        sendMail(receiver, title, htmlContent);
    }

    @Override
    public void recoverAccount(String receiver, String title, String url) throws MessagingException {
        Context context = new Context();
        context.setVariable("url", url);
        context.setVariable("email", receiver);
        log.info("url:{}", url);
        String htmlContent = templateEngine.process("recover", context);
        sendMail(receiver, title, htmlContent);
    }


    private void sendMail(String receiver, String title, String htmlContent) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), false);
        helper.setFrom("liuqi6602@163.com");
        helper.setTo(receiver);
        helper.setSubject(title);
        helper.setText(htmlContent, true);
        log.info("已向{}发送验证邮件", receiver);
        mailSender.send(helper.getMimeMessage());
    }
}
