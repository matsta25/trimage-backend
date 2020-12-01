package com.matsta25.trimagebackend.utlis;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String getInputUuidFileName() {
        return UUID.randomUUID().toString().replace("-", "") + ".png";
    }

    public static void saveFile(String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get("static/photos");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    @Scheduled(cron = "0 00 00 * * *")
    public static void deleteFiles() {
        try {
            File f = new File("static/photos");
            FileUtils.cleanDirectory(f);
            FileUtils.forceDelete(f);
            FileUtils.forceMkdir(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("deleteFiles() - clean static/photos - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }
}
