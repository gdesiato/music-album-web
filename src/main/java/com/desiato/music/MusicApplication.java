package com.desiato.music;

import com.desiato.music.models.Role;
import com.desiato.music.models.User;
import com.desiato.music.repositories.RoleRepository;
import com.desiato.music.repositories.UserRepository;
import com.desiato.music.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class MusicApplication {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;


	public static void main(String[] args) {
		SpringApplication.run(MusicApplication.class, args);
	}

	@PostConstruct
	public void run() throws Exception {


		// ROLES

		Role roleAdmin;
		// check if it exists
		if (roleRepository.existsByName("ADMIN")) {
			// fetch it
			roleAdmin = roleRepository.findRoleByName("ADMIN");
		} else {
			roleAdmin = roleRepository.save(Role.builder().name("ADMIN").build());
		}

		Role roleUser;
		// check if it exists
		if (roleRepository.existsByName("USER")) {
			// fetch it
			roleUser = roleRepository.findRoleByName("USER");
		} else {
			roleUser = roleRepository.save(Role.builder().name("USER").build());
		}


		// USERS

		User userAdmin = userRepository.findByUsername("UserAdmin");
		// if it is not there we create it
		if (userAdmin == null) {
			userAdmin = userRepository.save(User.builder()
					.username("UserAdmin")
					.password(passwordEncoder.encode("useradmin")) //encoding password
					.roles(Arrays.asList(roleAdmin, roleUser))
					.build());
		}

		User userUser = userRepository.findByUsername("UserUser");
		if (userUser == null) {
			userUser = userRepository.save(User.builder()
					.username("UserUser")
					.password(passwordEncoder.encode("useruser")) //encoding password
					.roles(Collections.singletonList(roleUser))
					.build());
		}
	}

}
