package org.ssglobal.training.codes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jooq.JooqProperties;
import org.springframework.boot.system.JavaVersion;
import org.springframework.core.SpringVersion;
import org.springframework.security.core.SpringSecurityCoreVersion;

@SpringBootApplication
public class MajorrevalidaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MajorrevalidaBackendApplication.class, args);
		System.out.println(SpringVersion.getVersion());
		System.out.println(SpringSecurityCoreVersion.getVersion());
		System.out.println(JavaVersion.getJavaVersion());
	}
}
