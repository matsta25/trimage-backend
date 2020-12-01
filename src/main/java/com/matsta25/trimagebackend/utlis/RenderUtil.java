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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.matsta25.trimagebackend.TrimageBackendApplication.IS_PRODUCTION;

@Service
public class RenderUtil {
    public static int numberOfShapesTotal;

    @Autowired
    private static SimpMessagingTemplate webSocket;

    public RenderUtil(SimpMessagingTemplate webSocket) {
        RenderUtil.webSocket = webSocket;
    }

    static Logger logger = LoggerFactory.getLogger(RenderUtil.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void render(String fileName, String numberOfShapes, String mode) throws IOException {
        logger.info("RenderUtil: render()");

        numberOfShapesTotal = Integer.parseInt(numberOfShapes);

        if (IS_PRODUCTION) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "/app/go/bin/primitive -i static/photos/" + fileName + " -o static/photos/output_" + fileName + " -n " + String.valueOf(numberOfShapes) + " -m " + mode + " -v");
            processBuilder.redirectErrorStream(true);
            final Process process = processBuilder.start();
            watchProgress(process, fileName);
        } else {
            ProcessBuilder processBuilder = new ProcessBuilder("/home/matsta25/go/bin/primitive", "-i", "static/photos/" + fileName, "-o", "static/photos/output_" + fileName, "-n", String.valueOf(numberOfShapes),"-m", mode, "-v");
            processBuilder.redirectErrorStream(true);
            final Process process = processBuilder.start();
            watchProgress(process, fileName);
        }
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
                        double resultFloatPercent = (double) result / numberOfShapesTotal * 100;
                        webSocket.convertAndSend("/topic/trimage", new WebSocketMessage("PROGRESS", String.valueOf(round(resultFloatPercent,1))));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                webSocket.convertAndSend("/topic/trimage", new WebSocketMessage("STATUS", "DONE"));
                logger.info("Done task: - {}", dateTimeFormatter.format(LocalDateTime.now()) );
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

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
