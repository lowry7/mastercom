package cn.mastercom.backstage.residentuserquery;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class MainApplication implements CommandLineRunner{
	private Logger logger = LoggerFactory.getLogger(MainApplication.class);

	@Bean
	public CountDownLatch countDownLatch() {
		return new CountDownLatch(1);
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Dubbo Provider start now.................");
	}
}
