package com.matsta25.trimagebackend.utlis;

import com.matsta25.trimagebackend.dto.WebSocketMessage;
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
        logger.info("RenderUtil: render()");
//        ProcessBuilder builder = new ProcessBuilder("/home/matsta25/go/bin/primitive", "-i", "static/photos/" + fileName, "-o", "static/photos/output_" + fileName, "-n", String.valueOf(numberOfShapes), "-v");
//        ProcessBuilder builder = new ProcessBuilder("/app/go/bin/primitive", "-i", "static/photos/" + fileName, "-o", "static/photos/output_" + fileName, "-n", String.valueOf(numberOfShapes), "-v");

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "/app/go/bin/primitive", "-i", "static/photos/" + fileName, "-o", "static/photos/output_" + fileName, "-n", String.valueOf(numberOfShapes), "-v");
        processBuilder.redirectErrorStream(true);
        final Process process = processBuilder.start();
        watchProgress(process, fileName);
    }

    public static void watchProgress(final Process process, String fileName) {
        new Thread(() -> {
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                    Integer result = getActualNumberOfShapes(line);
                    if (result != null) {
                        double resultFloatPercent = (double)result/numberOfShapes * 100;
                        webSocket.convertAndSend("/topic/trimage", new WebSocketMessage("PROGRESS", String.valueOf(resultFloatPercent)));
//                        webSocket.convertAndSend("/topic/trimage", new WebSocketMessage().builder().type("PROGRESS").content(String.valueOf(resultFloatPercent)));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                webSocket.convertAndSend("/topic/trimage", new WebSocketMessage("STATUS", "DONE"));
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
