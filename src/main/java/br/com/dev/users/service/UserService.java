package br.com.dev.users.service;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dev.users.model.Phone;
import br.com.dev.users.model.User;
import br.com.dev.users.repository.UserRepository;

@Service
public class UserService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1427190256258418271L;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userDAO;

    @Transactional(rollbackFor = { Exception.class })
    public User criar(final User user) throws Exception {
	final UUID id = UUID.randomUUID();
	user.setId(id.toString());
	final Date dataCriacao = new Date();
	user.setCreated(dataCriacao);
	user.setLastLogin(dataCriacao);

	for (Phone phone : user.getPhones()) {
	    phone.setId(UUID.randomUUID().toString());
	}

	LOGGER.debug("User added!");
	LOGGER.debug("{}", user);
	
	this.userDAO.save(user);
	return user;
    }
}
