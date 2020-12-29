package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailingList {

    public static void main(String[] args) {
        // create a list of emails
        List<String> emails = new ArrayList<String>();
        emails.add("h4mdy1234@gmail.com");
        emails.add("bourouniahamdi7@gmail.com");
//        emails.add("email3@email.com");
//        emails.add("email4@email.com");
//        emails.add("email5@email.com");

        // email subject
        String subject = "Test Email";

        // message which is to be sent
        String message = "Test Email Message";

        // send the email to multiple recipients
        mailingList(subject, emails, message);
    }

    public static void mailingList(final String subject, final List<String> emailToAddresses,
                                   final String emailBodyText) {

        // from email address
        final String username = "3laiag.fsegn@gmail.com";

        // make sure you put your correct password
        final String password = "3laiag2020";

        // smtp email server
        final String smtpHost = "smtp.googlemail.com";

        // We will put some properties for smtp configurations
        Properties props = new Properties();

        // do not change - start
        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.host", smtpHost);
        // props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        // do not change - end

        // we authentcate using your email and password and on successful
        // we create the session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String emails = null;

        try {
            // we create new message
            Message message = new MimeMessage(session);

            // set the from 'email address'
            message.setFrom(new InternetAddress(username));

            // set email subject
            message.setSubject(subject);

            // set email message
            // this will send html mail to the intended recipients
            // if you do not want to send html mail then you do not need to wrap the message
            // inside html tags
            String content = "<html>\n<body>\n";
            content += emailBodyText + "\n";
            content += "\n";
            content += "</body>\n</html>";
            message.setContent(content, "text/html");

            // form all emails in a comma separated string
            StringBuilder sb = new StringBuilder();

            int i = 0;
            for (String email : emailToAddresses) {
                sb.append(email);
                i++;
                if (emailToAddresses.size() > i) {
                    sb.append(", ");
                }
            }

            emails = sb.toString();

            // set 'to email address'
            // you can set also CC or TO for recipient type
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(sb.toString()));

            System.out.println("Sending Email to " + emails + " from " + username + " with Subject - " + subject);

            // send the email
            Transport.send(message);

            System.out.println("Email successfully sent to " + emails);
        } catch (MessagingException e) {
            System.out.println("Email sending failed to " + emails);
            System.out.println(e);
        }
    }
}
