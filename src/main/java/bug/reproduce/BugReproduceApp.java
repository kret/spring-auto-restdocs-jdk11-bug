package bug.reproduce;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BugReproduceApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
			.sources(BugReproduceApp.class)
			.run(args);
	}
}
