package kz.atu.uit.afo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class AtuFrontOfficeApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Almaty"));
	}

	public static void main(String[] args) {
		SpringApplication.run(AtuFrontOfficeApplication.class, args);
	}

}
