package br.com.dev.users.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4046208443375325325L;

    @Id
    @NotNull
    private String id;

    private String name;

    private String email;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    private String token;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Phone> phones = new ArrayList<>();

    public User() {
	super();
    }

    public User(final String email, final String password) {
	super();
	this.email = email;
	this.password = password;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }

    public Date getModified() {
	return modified;
    }

    public void setModified(Date modified) {
	this.modified = modified;
    }

    public Date getLastLogin() {
	return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
	this.lastLogin = lastLogin;
    }

    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }

    public List<Phone> getPhones() {
	return phones;
    }

    public void setPhones(List<Phone> phones) {
	this.phones = phones;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
	result = prime * result + ((modified == null) ? 0 : modified.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((phones == null) ? 0 : phones.hashCode());
	result = prime * result + ((token == null) ? 0 : token.hashCode());
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
	User other = (User) obj;
	if (created == null) {
	    if (other.created != null)
		return false;
	} else if (!created.equals(other.created))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (lastLogin == null) {
	    if (other.lastLogin != null)
		return false;
	} else if (!lastLogin.equals(other.lastLogin))
	    return false;
	if (modified == null) {
	    if (other.modified != null)
		return false;
	} else if (!modified.equals(other.modified))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	if (phones == null) {
	    if (other.phones != null)
		return false;
	} else if (!phones.equals(other.phones))
	    return false;
	if (token == null) {
	    if (other.token != null)
		return false;
	} else if (!token.equals(other.token))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", created="
		+ created + ", modified=" + modified + ", lastLogin=" + lastLogin + ", token=" + token + ", phones="
		+ phones + "]";
    }

}
