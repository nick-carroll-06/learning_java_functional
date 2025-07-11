package carroll.nick.Functional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FunctionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunctionalApplication.class, args);
	}

	/**
	 * example creating an object via functional interface
	 * @return
	 */
	@Bean
	public CommandLineRunner clr() {
		return args -> System.out.println("hello world");
	}
}
