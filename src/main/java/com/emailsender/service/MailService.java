package com.emailsender.service;

import com.emailsender.dto.MailRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MailService {

    private static final String API_URL = "https://send.api.mailtrap.io/api/send";

    private static final Logger LOGGER = Logger.getLogger(MailService.class);
    private final String apiKey;

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public MailService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public int sendEmail(MailRequest request) throws IOException {
        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.addHeader("Api-Token", apiKey);
        int statusCode;

        try {
            String requestBody = objectMapper.writeValueAsString(request);
            LOGGER.info(requestBody);
            HttpEntity httpEntity = buildHttpEntity(requestBody);
            httpPost.setEntity(httpEntity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                statusCode = response.getCode();
                if (statusCode != 200) {
                    LOGGER.warn("Failed to send email: HTTP error code " + statusCode);
                }
                String responseBody = EntityUtils.toString(response.getEntity());
                LOGGER.info("Response body: " + responseBody);
            }
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            throw new IOException("Failed to send email: " + e.getMessage());
        }
        return statusCode;
    }

    public HttpEntity buildHttpEntity(String requestBody) {
        return new StringEntity(requestBody, ContentType.APPLICATION_JSON);
    }
}