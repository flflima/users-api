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

    @Transactional(rollbackFor = { Exception.class })
    public User login(final User user) throws UserInvalidoException {

	final CriteriaBuilder builder = this.em.getCriteriaBuilder();
	final CriteriaQuery<User> query = builder.createQuery(User.class);
	final Root<User> root = query.from(User.class);

	final List<Predicate> predicates = new ArrayList<>();

	// Caso o e-mail e a senha correspondam a um usuário existente, retornar igual
	// ao endpoint de Criação.
	predicates.add(builder.equal(root.get("email"), user.getEmail()));
	predicates.add(builder.equal(root.get("password"), user.getPassword()));

	query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

	final List<User> resultList = this.em.createQuery(query.select(root)).getResultList();

	if (resultList.size() > 0) {
	    final User userRetorno = resultList.get(0);
	    userRetorno.setLastLogin(new Date());
	    return this.userDAO.save(userRetorno);
	}

	// se usuario e senha não encontrados, validar as informações

	predicates.clear();
	predicates.add(builder.equal(root.get("email"), user.getEmail()));
	query.where(builder.equal(root.get("email"), user.getEmail()));

	final List<User> resultList2 = this.em.createQuery(query.select(root)).getResultList();

	if (resultList2.size() > 0) {
	    // Caso o e-mail exista mas a senha não bata, retornar o status apropriado 401
	    // mais a mensagem "Usuário e/ou senha inválidos"
	    final User userEmailValido = resultList2.get(0);

	    if (!StringUtils.equals(user.getPassword(), userEmailValido.getPassword())) {
		// Caso o e-mail exista mas a senha não bata, retornar o status apropriado 401
		// mais a mensagem "Usuário e/ou senha inválidos"
		throw new UserInvalidoException("Usuário e/ou senha inválidos");
	    }
	} else {
	    // Caso o e-mail não exista, retornar erro com status apropriado mais a mensagem
	    // "Usuário e/ou senha inválidos"
	    throw new UserInvalidoException("Usuário e/ou senha inválidos");
	}

	return resultList.get(0);
//	
//	if (this.em.createQuery(query.select(root)).getResultList().size() > 0) {
//	    throw new EmailCadastradoException("E-mail já existente");
//	}
//	return null;
    }
}
