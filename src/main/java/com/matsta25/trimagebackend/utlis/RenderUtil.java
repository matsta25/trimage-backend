package com.matsta25.trimagebackend.utlis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Service
public class RenderUtil {
    public static final int numberOfShapes = 100;

    @Autowired
    private static SimpMessagingTemplate webSocket;

    public RenderUtil(SimpMessagingTemplate webSocket) {
        RenderUtil.webSocket = webSocket;
    }

    static Logger logger = LoggerFactory.getLogger(RenderUtil.class);

    public static void render(String fileName) throws IOException {
        logger.info("render()");
        ProcessBuilder builder = new ProcessBuilder("/home/matsta25/go/bin/primitive", "-i", "photos/" + fileName, "-o", "photos/output_" + fileName, "-n", String.valueOf(numberOfShapes), "-v");
        // ProcessBuilder builder = new ProcessBuilder("ping", "google.com");
        builder.redirectErrorStream(true);
        final Process process = builder.start();
        watchProgress(process, fileName);
    }

    public static void watchProgress(final Process process, String fileName) {
        new Thread(() -> {
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    Integer result = getActualNumberOfShapes(line);
                    if (result != null) {
                        double resultFloatPercent = (double)result/numberOfShapes * 100;
                        webSocket.convertAndSend("/topic/chat", String.valueOf(resultFloatPercent));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // FileUtil.deleteFile(fileName);
            }
        }).start();
    }

    private static Integer getActualNumberOfShapes(String line) {
        int numberEnd = line.indexOf(":");
        if (numberEnd != -1) {
            return Integer.parseInt(line.substring(0, numberEnd));
        }
        return null;
    }
}
