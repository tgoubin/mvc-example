package org.blagnac.coo.mvcexample.ex2;

import org.blagnac.coo.mvcexample.controller.MainController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Classe principale de l'application pour l'exemple 2
 */
// Parametrage de l'application SpringBoot
@SpringBootApplication(scanBasePackages = "org.blagnac.coo.mvcexample.ex2")
@ComponentScan("org.blagnac.coo.mvcexample.ex2")
public class Exemple2Application extends SpringBootServletInitializer implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(Exemple2Application.class);
	}

	/**
	 * Methode appelee au lancement de l'application (via l'interface
	 * ApplicationRunner)
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Lancement de l'application pour l'exemple 2");

		// Chargement des donnees
		MainController.loadData();
	}
}
