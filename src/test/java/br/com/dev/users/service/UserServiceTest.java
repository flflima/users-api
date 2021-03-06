package br.com.dev.users.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dev.users.exceptions.CampoInvalidoException;
import br.com.dev.users.exceptions.EmailCadastradoException;
import br.com.dev.users.model.User;
import br.com.dev.users.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userDAO;
    
    @Test(expected = CampoInvalidoException.class)
    public void testCriarExpectedCampoInvalidoException() throws EmailCadastradoException, CampoInvalidoException {
	User user = new User();
	userService.criar(user);
    }
    
    @Test(expected = EmailCadastradoException.class)
    public void testEmailJaCadastrado() throws EmailCadastradoException, CampoInvalidoException {
	User user1 = new User();
	user1.setEmail("teste@teste.com.br");
	user1.setPassword("senha123");
	user1.setPhones(new ArrayList<>());

	User user2 = new User();
	user2.setEmail("teste@teste.com.br");
	user2.setPassword("senha123");
	user2.setPhones(new ArrayList<>());
	
	userService.criar(user1);
	
	userService.criar(user2);
    }
    
    @Test
    public void testCriarUser() throws EmailCadastradoException, CampoInvalidoException {
	User user = new User();
	user.setEmail("teste@teste2.com.br");
	user.setPassword("senha123");
	user.setPhones(new ArrayList<>());
	
	user = userService.criar(user);
	User user2 = userDAO.findById(user.getId()).get();
	
	assertEquals(user2.getId(), user.getId());
	assertEquals(user2.getEmail(), user.getEmail());
	assertEquals(user2.getPassword(), user.getPassword());
    }

}
