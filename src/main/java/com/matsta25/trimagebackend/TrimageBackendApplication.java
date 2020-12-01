package com.matsta25.trimagebackend;

import com.matsta25.trimagebackend.utlis.RenderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class TrimageBackendApplication implements CommandLineRunner {


	Logger logger = LoggerFactory.getLogger(RenderUtil.class);

	public static void main(String[] args) {
		SpringApplication.run(TrimageBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("TrimageBackendApplication: run()");

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "go get -u github.com/fogleman/primitive");
		processBuilder.command("bash", "-c", "primitive");
		processBuilder.start();
	}
}
