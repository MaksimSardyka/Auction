package com.epam.auction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	@Bean
	public Docket lotApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("lot")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.epam.auction"))
				.paths(PathSelectors.regex("/lot.*"))
				.build()
				.apiInfo(metaInfo());
	}

	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("user")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.epam.auction"))
				.paths(PathSelectors.regex("/user.*"))
				.build()
				.apiInfo(metaInfo());
	}

	@Bean
	public Docket auctionApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("auction")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.epam.auction"))
				.paths(PathSelectors.regex("/.*"))
				.build()
				.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo("Auction API", "Auction API for final project", "1.0", "Terms of Service",
				new Contact("Maxim Sardyko", "https://www.maximsardyko.com/", "maximaddress@gmail.com@gmail.com"),
				"Apache License Version 2.0", "https://www.apache.org/licesen.html");
		return apiInfo;
	}
}
