package com.emailsender.util;

import com.emailsender.entity.Attachment;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class FileUtils {
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class);

    public static List<Attachment> getFilesFromDirectory(File directory) {
        List<Attachment> attachments = new ArrayList<>();
        if (directory != null && directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isFile()) {
                    Attachment attachment = new Attachment();
                    try {
                        byte[] fileContent = Files.readAllBytes(file.toPath());
                        attachment.setContent(Base64.getEncoder().encodeToString(fileContent));
                        attachment.setFilename(file.getName());

                        String extension = getFileExtension(file.getName());
                        String mimeType = MimeTypesUtils.getMimeType(extension);
                        attachment.setType(mimeType);

                        attachment.setDisposition("attachment");
                        attachments.add(attachment);
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                } else if (file.isDirectory()) {
                    attachments.addAll(getFilesFromDirectory(file));
                }
            }
        }
        return attachments;
    }

    private static String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1);
    }
}
