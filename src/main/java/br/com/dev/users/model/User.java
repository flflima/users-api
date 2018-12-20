package br.com.dev.users.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4046208443375325325L;

    private String name;
    private String email;
    private String password;
    private List<Phone> phones;

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

    public List<Phone> getPhones() {
	return phones;
    }

    public void setPhones(List<Phone> phones) {
	this.phones = phones;
    }

    @Override
    public String toString() {
	return "User [name=" + name + ", email=" + email + ", password=" + password + ", phones=" + phones + "]";
    }

}
