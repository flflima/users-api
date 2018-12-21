package br.com.dev.users.controller;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dev.users.model.User;
import br.com.dev.users.repository.UserRepository;
import br.com.dev.users.service.UserService;

@RestController
@RequestMapping(path = "users")
public class UsersController implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 735349405022822627L;

    @Autowired
    private UserRepository userDAO;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody final User user) {
	try {
	    return new ResponseEntity<>(this.userService.criar(user), HttpStatus.CREATED);
	} catch (final Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    LOGGER.error("-- " + e);
	}
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
	LOGGER.debug("List all users!");
	return new ResponseEntity<>(this.userDAO.findAll(), HttpStatus.OK);
    }

}
