package br.com.dev.users.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dev.users.exceptions.CampoInvalidoException;
import br.com.dev.users.exceptions.EmailCadastradoException;
import br.com.dev.users.exceptions.SessaoInvalidaException;
import br.com.dev.users.exceptions.UserInvalidoException;
import br.com.dev.users.model.User;
import br.com.dev.users.model.messages.MensagemRetorno;
import br.com.dev.users.repository.UserRepository;
import br.com.dev.users.service.LoginService;
import br.com.dev.users.service.PerfilUserService;
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

    @Autowired
    private LoginService loginService;

    @Autowired
    private PerfilUserService perfilUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    /**
     * Endpoint para criação de Users.
     * 
     * @param user
     * @return {@link ResponseEntity}
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody(required = true) final User user) {
	try {
	    return new ResponseEntity<>(this.userService.criar(user), HttpStatus.CREATED);
	} catch (final EmailCadastradoException e) {
	    e.printStackTrace();
	    LOGGER.error(" " + e);
	    return new ResponseEntity<>(new MensagemRetorno(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (final CampoInvalidoException e) {
	    e.printStackTrace();
	    LOGGER.error(" " + e);
	    return new ResponseEntity<>(new MensagemRetorno(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (final Exception e) {
	    e.printStackTrace();
	    LOGGER.error(" " + e);
	    return new ResponseEntity<>(new MensagemRetorno(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    /**
     * Endpoint para o login de Users.
     * 
     * @param user
     * @return {@link ResponseEntity}
     */
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody final User user) {
	try {
	    return new ResponseEntity<>(this.loginService.login(user), HttpStatus.CREATED);
	} catch (final UserInvalidoException e) {
	    e.printStackTrace();
	    LOGGER.error(" " + e);
	    return new ResponseEntity<>(new MensagemRetorno(e.getMessage()), HttpStatus.UNAUTHORIZED);
	} catch (final Exception e) {
	    e.printStackTrace();
	    LOGGER.error(" " + e);
	    return new ResponseEntity<>(new MensagemRetorno(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    /**
     * Endpoint para visualização do perfil de user.
     * 
     * @param id
     * @param request
     * @return {@link ResponseEntity}
     * @throws {@link InterruptedException}
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> perfilUsuario(@PathVariable(value = "id", required = true) final String id,
	    final HttpServletRequest request) throws InterruptedException {
	try {
	    return new ResponseEntity<>(this.perfilUserService.getPerfilUser(id, request), HttpStatus.CREATED);
	} catch (final SessaoInvalidaException e) {
	    e.printStackTrace();
	    LOGGER.error(" " + e);
	    return new ResponseEntity<>(new MensagemRetorno(e.getMessage()), HttpStatus.UNAUTHORIZED);
	} catch (final UserInvalidoException e) {
	    e.printStackTrace();
	    LOGGER.error(" " + e);
	    return new ResponseEntity<>(new MensagemRetorno(e.getMessage()), HttpStatus.UNAUTHORIZED);
	} catch (final Exception e) {
	    e.printStackTrace();
	    LOGGER.error(" " + e);
	    return new ResponseEntity<>(new MensagemRetorno(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
	LOGGER.debug("List all users!");
	return new ResponseEntity<>(this.userDAO.findAll(), HttpStatus.OK);
    }

}
