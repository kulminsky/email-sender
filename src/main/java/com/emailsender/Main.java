package com.emailsender;

import com.emailsender.dto.MailRequest;
import com.emailsender.entity.Attachment;
import com.emailsender.entity.Recipient;
import com.emailsender.service.MailService;
import com.emailsender.util.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(MailService.class);

    public static void main(String[] args) {

        String API_TOKEN = "72a1bbec33fd68db2e0a557f254a3df1";

        MailService mailService = new MailService(API_TOKEN);

        File resourceDirectory = new File("src/main/resources/attachments");

        List<Attachment> attachmentsList = FileUtils.getFilesFromDirectory(resourceDirectory);

        MailRequest mailRequest = MailRequest.builder()
                .to(List.of(new Recipient("mailtrap@demomailtrap2.com", "Mike")))
                .from(new Recipient("kulminsky@gmail.com", "Andrii"))
                .bcc(List.of(new Recipient("mike3@mailtrap.io", "Mike")))
                .cc(List.of(new Recipient("mike4@mailtrap.io", "Mike")))
                .subject("Test")
                .text("Hello!")
                .html("<p>Hello! How are you?</p>")
                .attachments(attachmentsList)
                .headers(Map.of("X-Message-Source", "dev.mydomain.com"))
                .custom_variables(Map.of("user_id", "45982",
                                "batch_id", "PSJ-12"))
                .category("Test API")
                .build();

        int sent = 0;
        try {
            sent = mailService.sendEmail(mailRequest);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("STATUS CODE: " + sent);
    }
}