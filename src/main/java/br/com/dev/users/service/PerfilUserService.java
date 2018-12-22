package br.com.dev.users.service;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dev.users.exceptions.SessaoInvalidaException;
import br.com.dev.users.exceptions.UserInvalidoException;
import br.com.dev.users.model.User;
import br.com.dev.users.repository.UserRepository;

@Service
public class PerfilUserService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4765603329284092748L;

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfilUserService.class);

    private static final String AUTHORIZATION_HEADER = "authorization";

    private static final int TIME_LIMIT_IN_MINUTES = 30;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userDAO;

    public User getPerfilUser(final String id, final HttpServletRequest request)
	    throws UserInvalidoException, SessaoInvalidaException {
	final String authToken = request.getHeader(AUTHORIZATION_HEADER);
	LOGGER.debug("Validando Token");

	if (authToken != null && authToken.contains("Bearer ")) {
	    // Caso o token exista, buscar o usuário pelo id passado no path e comparar se o
	    // token no modelo é igual ao token passado no header.

	    LOGGER.debug("Token localizado no HEADER");

	    final User user = this.userDAO.getOne(id);
	    LOGGER.debug("Usuário localizado");

	    final String token = authToken.replaceAll("Bearer ", "");

	    return validarTokenUsuarioESessao(user, token);
	} else {
	    // Caso o token não exista, retornar erro com status apropriado com a mensagem
	    // "Não autorizado".

	    LOGGER.debug("Token não localizado no HEADER");
	    throw new UserInvalidoException("Não Autorizado");
	}
    }

    private User validarTokenUsuarioESessao(final User user, final String token)
	    throws SessaoInvalidaException, UserInvalidoException {
	LOGGER.debug("Validando Token recebido com Token do usuário");

	if (tokenValido(user, token)) {
	    return validarSessaoEAtualizarUsuario(user);
	} else {
	    // Caso não seja a MENOS que 30 minutos atrás, retornar erro com
	    // status apropriado com mensagem "Sessão inválida".

	    LOGGER.debug("Token recebido no HEADER diferente do usuário localizado");
	    throw new UserInvalidoException("Não Autorizado");
	}
    }

    @Transactional(rollbackOn = { Exception.class })
    private User validarSessaoEAtualizarUsuario(final User user) throws SessaoInvalidaException {
	// Caso seja o mesmo token, verificar se o último login foi a MENOS que 30
	// minutos atrás.

	LOGGER.debug("Token recebido e do usuário estão válidos");
	final LocalDateTime agora = LocalDateTime.now();

	if (sessaoValida(agora, user)) {
	    // Caso tudo esteja ok, retornar o usuário no mesmo formato do retorno do Login.
	    LOGGER.debug("Token, usuário e sessão válidos!");

	    user.setLastLogin(Date.from(agora.atZone(ZoneId.systemDefault()).toInstant()));
	    return this.userDAO.save(user);
	} else {
	    LOGGER.debug("Tempo limite da sessão expirado: {}", TIME_LIMIT_IN_MINUTES);
	    throw new SessaoInvalidaException("Sessão inválida");
	}
    }

    private boolean tokenValido(final User user, final String token) {
	return StringUtils.equals(token, user.getToken());
    }

    private boolean sessaoValida(final LocalDateTime agora, final User user) {
	final LocalDateTime lastLoginAsLocalDateTime = user.getLastLogin().toInstant().atZone(ZoneId.systemDefault())
		.toLocalDateTime();

	final Duration duration = Duration.between(agora, lastLoginAsLocalDateTime);
	return Math.abs(duration.toMinutes()) <= TIME_LIMIT_IN_MINUTES;
    }
}
