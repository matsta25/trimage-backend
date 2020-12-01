package com.matsta25.trimagebackend;

import com.matsta25.trimagebackend.utlis.RenderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class TrimageBackendApplication implements CommandLineRunner {


    Logger logger = LoggerFactory.getLogger(RenderUtil.class);

    public static void main(String[] args) {
        SpringApplication.run(TrimageBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("TrimageBackendApplication: run()");

        String[] command = {"/goinstall.sh"};
        ProcessBuilder p = new ProcessBuilder(command);

        try {

            // create a process builder to send a command and a argument
            Process p2 = p.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String line;

            logger.info("Output of running " + command + " is: ");
            System.out.println("Output of running " + command + " is: ");
            while ((line = br.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "go get -u github.com/fogleman/primitive");
//		processBuilder.command("bash", "-c", "primitive");
        processBuilder.redirectErrorStream(true);
        var process = processBuilder.start();

        try (var reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        }
    }
}
