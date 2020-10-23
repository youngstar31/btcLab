package com.btclab.demobtc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class DemoBtcApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoBtcApplication.class);

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			lastBitcoinPrice();
		};

	}

	@Scheduled(fixedRate = 30000)
	public void lastBitcoinPrice(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PriceList[]> responseEntity = restTemplate.getForEntity("https://api.n.exchange/en/api/v1/price/BTCLTC/latest/?market_code=nex",PriceList[].class);
		log.info("The Price List: " + Arrays.toString(responseEntity.getBody()));
	}


	public static void main(String[] args) {

		SpringApplication.run(DemoBtcApplication.class, args);




	}

}
