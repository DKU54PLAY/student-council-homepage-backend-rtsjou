package com.rtsoju.dku_council_homepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //jpa에서 auditing을 자동으로 시켜주기 위해서.. 업데이트용도
public class DkuCouncilHomepageApplication {


//	public static final String APPLICATION_LOCATIONS =
//			"spring.config.location="
//					+ "classpath:application.properties,"
//					+ "classpath:application-smtp.properties,";

	public static void main(String[] args) {
//		new SpringApplicationBuilder(DkuCouncilHomepageApplication.class)
//				.properties(APPLICATION_LOCATIONS)
//				.run(args);
		SpringApplication.run(DkuCouncilHomepageApplication.class, args);
	}

}
