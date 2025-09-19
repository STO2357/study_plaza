package sto.study_plaza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudyPlazaApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyPlazaApplication.class, args);
	}

}
