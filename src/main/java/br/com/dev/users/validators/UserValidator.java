package br.com.dev.users.validators;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.dev.users.exceptions.CampoInvalidoException;
import br.com.dev.users.model.User;

@Service
public class UserValidator implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4995616496001229538L;

    public boolean validarUser(final User user) throws CampoInvalidoException {
	if (user.getEmail() == null)
	    throw new CampoInvalidoException("Campo email é obrigatório");

	if (user.getPassword() == null)
	    throw new CampoInvalidoException("Campo password é obrigatório");

	return true;
    }
}
