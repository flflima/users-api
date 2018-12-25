package br.com.dev.users.validators;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import br.com.dev.users.exceptions.CampoInvalidoException;
import br.com.dev.users.model.User;

@Component
public class UserValidator implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4995616496001229538L;

    /**
     * Valida campos do usuário
     * 
     * @param user
     * @return boolean
     * @throws CampoInvalidoException
     */
    public boolean validarUser(final User user) throws CampoInvalidoException {
	if (user.getEmail() == null)
	    throw new CampoInvalidoException("Campo email é obrigatório");

	if (user.getPassword() == null)
	    throw new CampoInvalidoException("Campo password é obrigatório");

	return true;
    }
}
