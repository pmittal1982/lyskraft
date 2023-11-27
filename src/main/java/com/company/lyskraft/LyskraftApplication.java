package com.company.lyskraft;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

@SpringBootApplication
public class LyskraftApplication {

	public static void main(String[] args) {

		SpringApplication.run(LyskraftApplication.class, args).getBean(DispatcherServlet.class)
				.setThreadContextInheritable(true);
	}

	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials
				.fromStream(new ClassPathResource("metaltradingplatform-firebase.json").getInputStream());
		FirebaseOptions firebaseOptions = FirebaseOptions
				.builder()
				.setCredentials(googleCredentials)
				.build();
		FirebaseApp app;
		if(FirebaseApp.getApps().isEmpty()) {
			app = FirebaseApp.initializeApp(firebaseOptions, "metalTradingPlatform");
		} else {
			app = FirebaseApp.getApps().get(0);
		}
		return FirebaseMessaging.getInstance(app);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}