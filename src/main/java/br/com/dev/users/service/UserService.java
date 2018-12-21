package br.com.dev.users.service;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dev.users.EmailCadastradoException;
import br.com.dev.users.model.Phone;
import br.com.dev.users.model.User;
import br.com.dev.users.repository.UserRepository;
import br.com.dev.users.utilities.Utils;

@Service
public class UserService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1427190256258418271L;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userDAO;

    public User criar(final User user) throws EmailCadastradoException {
	validarEmailJaCadastrado(user);
	return persistirUsuario(user);
    }

    @Transactional(rollbackFor = { Exception.class })
    private User persistirUsuario(final User user) {
	final User userPopulado = popularUser(user);

	LOGGER.debug("User {} added!", userPopulado);

	return this.userDAO.save(userPopulado);
    }

    private User popularUser(final User user) {
	LOGGER.debug("Gerando ID para User");
	user.setId(Utils.getUUID());

	LOGGER.debug("Populando campos do user");
	final Date data = new Date();
	user.setCreated(data);
	user.setLastLogin(data);
	user.setToken(Utils.getJWTToken(user));

	LOGGER.debug("Gerando ID");
	for (Phone phone : user.getPhones()) {
	    phone.setId(Utils.getUUID());
	}

	return user;
    }

    private void validarEmailJaCadastrado(final User user) throws EmailCadastradoException {
	LOGGER.debug("Validando se e-mail '{}' já está cadastrado", user.getEmail());

	final CriteriaBuilder builder = this.em.getCriteriaBuilder();
	final CriteriaQuery<User> query = builder.createQuery(User.class);
	final Root<User> root = query.from(User.class);
	query.where(builder.equal(root.get("email"), user.getEmail()));

	if (this.em.createQuery(query.select(root)).getResultList().size() > 0) {
	    throw new EmailCadastradoException("E-mail já existente");
	}
    }
}
