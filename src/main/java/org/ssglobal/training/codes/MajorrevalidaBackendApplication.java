package org.ssglobal.training.codes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class MajorrevalidaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MajorrevalidaBackendApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}
}
