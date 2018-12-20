package br.com.dev.users.controller;

import java.io.Serializable;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dev.users.model.User;

@RestController
@RequestMapping(path = "users")
public class UsersController implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 735349405022822627L;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody final User user) {
	LOGGER.debug("User added!");
	LOGGER.debug("{}", user);
	return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
	LOGGER.debug("List all users!");
	return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    
}
