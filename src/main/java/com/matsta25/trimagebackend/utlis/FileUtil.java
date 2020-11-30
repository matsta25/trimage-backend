package com.matsta25.trimagebackend.utlis;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

public class FileUtil {
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

    public static void deleteFile(String fileName) {

        try {
            Files.deleteIfExists(Paths.get("static/photos/" + fileName));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        System.out.println("Deletion successful.");
    }

}
