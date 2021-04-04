package starterApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration

@ComponentScan(basePackages = { "com.project.controller", "com.project.security", "com.project.reservation",
		"com.project.database", "com.project.user", "com.project.setup", "com.project.lookup" })

@ComponentScan(basePackages={"com.project.security", "com.project.reservation" , "com.project.database", "com.project.user", "com.project.setup" , "com.project.lookup", "com.project.cancelTrain", "com.project.ticketCancellation"})
@SpringBootApplication
public class StartupApplication {

	public static void main(String args[]) {
		SpringApplication.run(StartupApplication.class, args);
	}

}
