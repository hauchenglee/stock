package com.stock.main.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class MailSender {

    final String username = Constants.MAIL_USER_NAME;
    final String password = Constants.MAIL_PASSWORD;
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private List<String> receivers = null;
    private String subject = null;
    private String content = null;
    private Properties props = null;
    private Session session = null;
    private String htmlContent = null;
    private Date sendDate = null;

    public MailSender() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        props.put("mail.smtp.localhost", "localhost");
        props.put("mail.smtp.ssl.enable", "true");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
//		session.setDebug(true);  //if send mail have issue, use this check
    }

    public static void main(String[] args) {
        new MailSender();
    }

    public void setReceiver(List<String> receiverList) {
        this.receivers = receiverList;
    }

    public void setSubject(String strSubject) {
        this.subject = strSubject;
    }

    public void setContent(String strContent) {
        this.content = strContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public void setMailContent(String strSubject, String strContent) {
        this.subject = strSubject;
        this.content = strContent;
    }

    public void sendSimpleMail(List<String> receiverList, String strSubject, String strContent) {
        sendSimpleMail(receiverList, strSubject, strContent, null);
    }

    public void sendSimpleMail(List<String> receiverList, String strSubject, String strContent, Date sendDate) {
        this.receivers = receiverList;
        this.subject = strSubject;
        this.content = strContent;
        this.sendDate = sendDate;
        sendMail();
    }

    public void sendHTMLMail(List<String> receiverList, String strSubject, String htmlContent) {
        sendHTMLMail(receiverList, strSubject, htmlContent, null);
    }

    public void sendHTMLMail(List<String> receiverList, String strSubject, String htmlContent, Date sendDate) {
        this.receivers = receiverList;
        this.subject = strSubject;
        this.htmlContent = htmlContent;
        this.sendDate = sendDate;
        sendMail();
    }

    public void sendMail() {
        new Thread(() -> {
            try {
                log.info("before message");
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                InternetAddress[] iAddressArray = new InternetAddress[receivers.size()];
                for (int i = 0; i < receivers.size(); i++) {
                    iAddressArray[i] = new InternetAddress(receivers.get(i));
                }

                message.setRecipients(Message.RecipientType.TO, iAddressArray);
                message.setSubject(subject);
                if (!StringUtils.isNullOrEmpty(content)) {
                    message.setText(content);
                }

                if (!StringUtils.isNullOrEmpty(htmlContent)) {
                    message.setContent(htmlContent, "text/html;charset=UTF-8");
                }

                if (sendDate != null) {
                    message.setSentDate(sendDate);
                }

                Transport.send(message);
            } catch (MessagingException e) {
                log.error("MessagingException: ", e);
            }
        }).start();
    }
}
