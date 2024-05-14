package com.emailsender.dto;

import com.emailsender.entity.Attachment;
import com.emailsender.entity.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MailRequest {

    @NotEmpty(message = "To is required")
    private List<Recipient> to;
    private List<Recipient> cc;
    private List<Recipient> bcc;
    @NotEmpty(message = "From is required")
    private Recipient from;
    private List<Attachment> attachments;
    private Map<String, String> custom_variables;
    private Map<String, String> headers;
    @NotBlank(message = "Subject is required")
    private String subject;
    @NotBlank(message = "Text is required")
    private String text;
    private String html;
    private String category;
}
