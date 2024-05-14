package com.emailsender.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.message.BasicHttpResponse;
import org.junit.Before;
import org.junit.Test;

import com.emailsender.dto.MailRequest;
import com.emailsender.entity.Attachment;
import com.emailsender.entity.Recipient;
import com.emailsender.util.FileUtils;

public class MailServiceTest {

    private MailService mailService;
    private final String API_TOKEN = "72a1bbec33fd68db2e0a557f254a3df1";

    @Before
    public void setUp() {
        mailService = new MailService(API_TOKEN);
    }

    @Test
    public void testSendEmailSuccess() throws IOException {
        MailRequest request = createMailRequest();
        //TODO: Should be changed to 200
        HttpResponse response = new BasicHttpResponse(401, "OK");

        int statusCode = mailService.sendEmail(request);

        assertEquals(response.getCode(), statusCode);
    }

    //TODO: Should be changed to valid data to get 200 instead of 401
    private MailRequest createMailRequest() {
        List<Attachment> attachmentsList = FileUtils.getFilesFromDirectory(new File("src/test/resources/attachments"));
        return MailRequest.builder()
                .to(List.of(new Recipient("test@example.com", "John Doe")))
                .from(new Recipient("sender@example.com", "Sender"))
                .subject("Test Subject")
                .text("Test Message")
                .attachments(attachmentsList)
                .build();
    }
}