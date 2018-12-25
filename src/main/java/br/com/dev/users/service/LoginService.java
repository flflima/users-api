package br.com.dev.users.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dev.users.exceptions.UserInvalidoException;
import br.com.dev.users.model.User;
import br.com.dev.users.repository.UserRepository;
import br.com.dev.users.utilities.Utils;

@Service
public class LoginService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5714759465353399568L;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userDAO;

    /**
     * Valida e persiste login do user.
     * 
     * @param user
     * @return {@link User}
     * @throws UserInvalidoException
     */
    public User login(final User user) throws UserInvalidoException {
	LOGGER.debug("Iniciando login do usuário");

	User userRetorno = validarEmailPasswordExistente(user);

	if (userRetorno != null) {
	    return userRetorno;
	} else {
	    userRetorno = validarEmailExistente(user);
	    validarEmailEPassword(user, userRetorno);
	    registrarUltimoLogin(userRetorno);
	    return userRetorno;
	}

    }

    /**
     * Caso o e-mail e a senha correspondam a um usuário existente, retornar igual
     * ao endpoint de Criação.
     * 
     * @param user
     * @return {@link User}
     */
    private User validarEmailPasswordExistente(final User user) {
	LOGGER.debug("Validar email e senha");
	final CriteriaBuilder builder = this.em.getCriteriaBuilder();
	final CriteriaQuery<User> query = builder.createQuery(User.class);
	final Root<User> root = query.from(User.class);
	final List<Predicate> predicates = new ArrayList<>();

	predicates.add(builder.equal(root.get("email"), user.getEmail()));
	predicates.add(builder.equal(root.get("password"), Utils.encrypt(user.getPassword())));
	query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

	final List<User> resultList = this.em.createQuery(query.select(root)).getResultList();

	if (resultList.size() > 0) {
	    LOGGER.debug("Email e senha válidos");
	    return registrarUltimoLogin(resultList.get(0));
	} else {
	    return null;
	}
    }

    /**
     * Caso o e-mail não exista, retornar erro com status apropriado mais a mensagem
     * "Usuário e/ou senha inválidos"
     * 
     * @param user
     * @return {@link User}
     * @throws UserInvalidoException
     */
    private User validarEmailExistente(final User user) throws UserInvalidoException {
	LOGGER.debug("Validar e-mail");
	final CriteriaBuilder builder = this.em.getCriteriaBuilder();
	final CriteriaQuery<User> query = builder.createQuery(User.class);
	final Root<User> root = query.from(User.class);
	final List<Predicate> predicates = new ArrayList<>();

	predicates.add(builder.equal(root.get("email"), user.getEmail()));
	query.where(builder.equal(root.get("email"), user.getEmail()));

	final List<User> resultList = this.em.createQuery(query.select(root)).getResultList();

	if (resultList.size() > 0) {
	    LOGGER.debug("Email válido");
	    return resultList.get(0);
	} else {
	    // E-mail não existe
	    LOGGER.error("Email não localizado");
	    throw new UserInvalidoException("Usuário e/ou senha inválidos");
	}
    }

    /**
     * Caso o e-mail exista mas a senha não bata, retornar o status apropriado 401
     * mais a mensagem "Usuário e/ou senha inválidos"
     * 
     * @param user
     * @param userRetorno
     * @throws UserInvalidoException
     */
    private void validarEmailEPassword(final User user, User userRetorno) throws UserInvalidoException {
	if (!StringUtils.equals(Utils.encrypt(user.getPassword()), userRetorno.getPassword())) {
	    LOGGER.error("Email válido, senha inválida");
	    throw new UserInvalidoException("Usuário e/ou senha inválidos");
	}

	LOGGER.debug("Email e senha válidos");
    }

    /**
     * Registra o último login do user
     * 
     * @param user
     * @return {@link User}
     */
    @Transactional(rollbackFor = { Exception.class })
    private User registrarUltimoLogin(final User user) {
	LOGGER.debug("Registrar data de último login");
	user.setLastLogin(new Date());
	return this.userDAO.save(user);
    }
}
