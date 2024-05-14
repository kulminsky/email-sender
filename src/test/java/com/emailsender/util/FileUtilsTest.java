package com.emailsender.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.io.File;
import java.util.List;
import com.emailsender.entity.Attachment;

public class FileUtilsTest {

    @Test
    public void testGetFilesFromDirectory() {
        File directory = new File("src/test/resources/attachments");

        List<Attachment> attachments = FileUtils.getFilesFromDirectory(directory);

        assertNotNull(attachments);
        assertEquals(2, attachments.size());
        assertEquals("test.html", attachments.get(0).getFilename());
        assertEquals("text/html", attachments.get(0).getType());
        assertNotNull(attachments.get(0).getContent());
        assertEquals("test.txt", attachments.get(1).getFilename());
        assertEquals("text/plain", attachments.get(1).getType());
        assertNotNull(attachments.get(1).getContent());
    }

    @Test
    public void testGetFilesFromNonexistentDirectory() {
        File directory = new File("nonexistent/directory");

        List<Attachment> attachments = FileUtils.getFilesFromDirectory(directory);

        assertNotNull(attachments);
        assertTrue(attachments.isEmpty());
    }
}
