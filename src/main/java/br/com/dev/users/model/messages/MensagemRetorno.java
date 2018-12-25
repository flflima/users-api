package br.com.dev.users.model.messages;

import java.io.Serializable;

/**
 * Modelo para mensagem de retorno, em caso de erro
 * 
 * @author felipe
 *
 */
public class MensagemRetorno implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3429452471113578938L;

    private String mensagem;

    public MensagemRetorno(String mensagem) {
	super();
	this.mensagem = mensagem;
    }

    public String getMensagem() {
	return mensagem;
    }

    public void setMensagem(String mensagem) {
	this.mensagem = mensagem;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MensagemRetorno other = (MensagemRetorno) obj;
	if (mensagem == null) {
	    if (other.mensagem != null)
		return false;
	} else if (!mensagem.equals(other.mensagem))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "MensagemRetorno [mensagem=" + mensagem + "]";
    }

}
