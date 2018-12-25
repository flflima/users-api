package br.com.dev.users.service;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    private static final int TIME_LIMIT_IN_MINUTES = 30;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userDAO;

    /**
     * Recupera perfil do user
     * 
     * @param id
     * @param request
     * @return {@link User}
     * @throws UserInvalidoException
     * @throws SessaoInvalidaException
     */
    public User getPerfilUser(final String id, final HttpServletRequest request)
	    throws UserInvalidoException, SessaoInvalidaException {
	final String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
	LOGGER.debug("Analisando Token");

	if (authToken != null && authToken.contains("Bearer ")) {
	    return recuperarValidarToken(id, authToken);
	} else {
	    // Caso o token não exista, retornar erro com status apropriado com a mensagem
	    // "Não autorizado".

	    LOGGER.debug("Token não localizado no HEADER");
	    throw new UserInvalidoException("Não Autorizado");
	}
    }

    /**
     * Caso o token exista, buscar o usuário pelo id passado no path e comparar se o
     * token no modelo é igual ao token passado no header.
     * 
     * @param id
     * @param authToken
     * @return {@link User}
     * @throws SessaoInvalidaException
     * @throws UserInvalidoException
     */
    private User recuperarValidarToken(final String id, final String authToken)
	    throws SessaoInvalidaException, UserInvalidoException {
	LOGGER.debug("Token localizado no HEADER");

	final Optional<User> user = this.userDAO.findById(id);

	if (user.isPresent()) {
	    LOGGER.debug("Usuário localizado");
	    final String token = authToken.replaceAll("Bearer ", "");
	    return validarTokenUsuarioESessao(user.get(), token);
	} else {
	    LOGGER.debug("Usuário com id {} não foi encontrado", id);
	    throw new UserInvalidoException("Usuário não encontrado");
	}
    }

    /**
     * Caso não seja a MENOS que 30 minutos atrás, retornar erro com status
     * apropriado com mensagem "Sessão inválida".
     * 
     * @param user
     * @param token
     * @return {@link User}
     * @throws SessaoInvalidaException
     * @throws UserInvalidoException
     */
    private User validarTokenUsuarioESessao(final User user, final String token)
	    throws SessaoInvalidaException, UserInvalidoException {
	LOGGER.debug("Validando Token recebido com Token do usuário");

	if (tokenValido(user, token)) {
	    return validarSessaoEAtualizarUsuario(user);
	} else {
	    LOGGER.debug("Token recebido no HEADER diferente do usuário localizado");
	    throw new UserInvalidoException("Não Autorizado");
	}
    }

    /**
     * Caso seja o mesmo token, verificar se o último login foi a MENOS que 30
     * minutos atrás.
     * 
     * @param user
     * @return {@link User}
     * @throws SessaoInvalidaException
     */
    @Transactional(rollbackOn = { Exception.class })
    private User validarSessaoEAtualizarUsuario(final User user) throws SessaoInvalidaException {
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

    /**
     * Compara token recebido com o token do {@link User} localizado
     * 
     * @param user
     * @param token
     * @return boolean
     */
    private boolean tokenValido(final User user, final String token) {
	return StringUtils.equals(token, user.getToken());
    }

    /**
     * Valida se o tempo atual está dentro do TIME_LIMIT_IN_MINUTES
     * 
     * @param agora
     * @param user
     * @return boolean
     */
    private boolean sessaoValida(final LocalDateTime agora, final User user) {
	final LocalDateTime lastLoginAsLocalDateTime = user.getLastLogin().toInstant().atZone(ZoneId.systemDefault())
		.toLocalDateTime();

	LOGGER.debug("Agora {}", agora);
	LOGGER.debug("Ultimo Login {}", lastLoginAsLocalDateTime);

	final Duration duration = Duration.between(agora, lastLoginAsLocalDateTime);

	LOGGER.debug("Diferença entre minutos em relação ao último login: {}", Math.abs(duration.toMinutes()));
	return Math.abs(duration.toMinutes()) <= TIME_LIMIT_IN_MINUTES;
    }
}
