package com.fivepoints.springboottest;

import com.fivepoints.springboottest.entities.ERole;
import com.fivepoints.springboottest.entities.Role;
import com.fivepoints.springboottest.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringbootTestApplication implements ApplicationRunner {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTestApplication.class, args);
	}
	// this bean used to crypt the password
	    public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoderBean = applicationContext.getBean(BCryptPasswordEncoder.class);
		return passwordEncoderBean;
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Save roles
		Role superAdminRole = this.roleRepository.save(new Role(ERole.CLIENT));
	}
}
