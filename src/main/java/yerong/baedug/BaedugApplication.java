package yerong.baedug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BaedugApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaedugApplication.class, args);
	}

}
