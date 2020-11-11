package com.matsta25.trimagebackend.utlis;

import jdk.swing.interop.SwingInterOpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RenderUtil {

    static Logger logger = LoggerFactory.getLogger(RenderUtil.class);

    public static void render(String fileName) throws IOException {
        logger.info("render()");
        ProcessBuilder builder = new ProcessBuilder("primitive", "-i", "photos/" + fileName, "-o", "photos/output_" + fileName , "-n", "1000", "-v");
//        ProcessBuilder builder = new ProcessBuilder("ping", "google.com");
        builder.redirectErrorStream(true);
        final Process process = builder.start();
        watchProgress(process);
    }

    private static void watchProgress(final Process process) {
        new Thread() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // TODO:
                    System.out.println("Delete image");
                }
            }
        }.start();
    }
}