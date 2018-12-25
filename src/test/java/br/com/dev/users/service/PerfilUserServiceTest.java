package br.com.dev.users.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dev.users.exceptions.CampoInvalidoException;
import br.com.dev.users.exceptions.EmailCadastradoException;
import br.com.dev.users.exceptions.SessaoInvalidaException;
import br.com.dev.users.exceptions.UserInvalidoException;
import br.com.dev.users.model.User;
import br.com.dev.users.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerfilUserServiceTest {

    @Autowired
    private PerfilUserService perfilUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userDAO;

    @Test(expected = UserInvalidoException.class)
    public void testGetPerfilUserExpectsUserInvalidoExceptionWhenNoToken()
	    throws UserInvalidoException, SessaoInvalidaException {
	HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);

	perfilUserService.getPerfilUser("1111111", httpRequest);
    }

    @Test(expected = UserInvalidoException.class)
    public void testGetPerfilUserIdInvalido() throws UserInvalidoException, SessaoInvalidaException {
	HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
	Mockito.when(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(
		"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiMjE5ZjlmOC0yYWNiLTQzMTItYTJhYS00ZTEwMzhlYzQ0NTEifQ.l25tcNiCZqetjKuQZlPKFhhw7QaLDTHEgI1d6hcsUBplmmoGmweyWKEapyqTuFzICA2SBN39bcTZV9qABbdQ");

	perfilUserService.getPerfilUser("1111111", httpRequest);
    }

    @Test(expected = UserInvalidoException.class)
    public void testGetPerfilUserComTokenInvalido()
	    throws UserInvalidoException, SessaoInvalidaException, EmailCadastradoException, CampoInvalidoException {
	User user = new User();
	user.setEmail("teste@teste1.com.br");
	user.setPassword("senha123");
	user.setPhones(new ArrayList<>());
	user = userService.criar(user);

	HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
	Mockito.when(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer FzICA2SBN39bcTZV9qABbdQ");

	perfilUserService.getPerfilUser(user.getId(), httpRequest);
    }

    @Test(expected = SessaoInvalidaException.class)
    public void testGetPerfilUserTempoSessaoInvalido()
	    throws EmailCadastradoException, CampoInvalidoException, UserInvalidoException, SessaoInvalidaException {
	User user = new User();
	user.setEmail("teste2@teste.com.br");
	user.setPassword("senha123");
	user.setPhones(new ArrayList<>());
	user = userService.criar(user);

	Calendar cal = GregorianCalendar.getInstance();
	cal.add(Calendar.HOUR, 1);
	user.setLastLogin(cal.getTime());

	user = userDAO.save(user);

	HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
	Mockito.when(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + user.getToken());

	perfilUserService.getPerfilUser(user.getId(), httpRequest);
    }

    @Test
    public void testGetPerfilUser()
	    throws EmailCadastradoException, CampoInvalidoException, UserInvalidoException, SessaoInvalidaException {
	User user = new User();
	user.setEmail("teste3@teste.com.br");
	user.setPassword("senha123");
	user.setPhones(new ArrayList<>());
	userService.criar(user);

	User userLogin = loginService.login(new User("teste3@teste.com.br", "senha123"));

	HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
	Mockito.when(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + user.getToken());

	user = perfilUserService.getPerfilUser(user.getId(), httpRequest);

	assertEquals(userLogin.getEmail(), user.getEmail());
	assertEquals(userLogin.getPassword(), user.getPassword());
	assertEquals(userLogin.getId(), user.getId());
    }

}
