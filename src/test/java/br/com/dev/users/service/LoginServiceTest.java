package br.com.dev.users.service;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dev.users.exceptions.CampoInvalidoException;
import br.com.dev.users.exceptions.EmailCadastradoException;
import br.com.dev.users.exceptions.UserInvalidoException;
import br.com.dev.users.model.User;
import br.com.dev.users.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userDAO;

    @Test(expected = UserInvalidoException.class)
    public void testLoginExpectUserInvalidoExceptionWhenEmailEPasswordInexistentes() throws UserInvalidoException {
	User user = new User();
	user.setEmail("teste@teste.com");
	user.setPassword("senha123");
	loginService.login(user);
    }

    @Test(expected = UserInvalidoException.class)
    public void testLoginExpectUserInvalidoExceptionWhenEmailInexistente()
	    throws UserInvalidoException, EmailCadastradoException, CampoInvalidoException {
	User user = new User();
	user.setEmail("teste@teste.com");
	user.setPassword("senha123");
	userService.criar(user);

	User userLogin = new User();
	userLogin.setEmail("testeaa@teste.com");
	userLogin.setPassword("senha123456");
	loginService.login(userLogin);
    }

    @Test(expected = UserInvalidoException.class)
    public void testLoginExpectUserInvalidoExceptionWhenEmailOkEPasswordErrada() throws UserInvalidoException {
	User userLogin = new User();
	userLogin.setEmail("teste@teste.com");
	userLogin.setPassword("senha123456");
	loginService.login(userLogin);
    }

    @Test
    public void testLoginUser() throws UserInvalidoException {
	User userLogin = new User();
	userLogin.setEmail("teste@teste.com");
	userLogin.setPassword("senha123");
	
	userLogin = loginService.login(userLogin);
	
	assertEquals(userDAO.findById(userLogin.getId()).get().getEmail(), userLogin.getEmail());
    }

}
