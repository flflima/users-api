package br.com.dev.users.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Phone implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4833085193795807532L;

    @Id
//    @GeneratedValue
//    private Long id;
    private String id;

    private String number;

    private String ddd;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getDdd() {
	return ddd;
    }

    public void setDdd(String ddd) {
	this.ddd = ddd;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((ddd == null) ? 0 : ddd.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((number == null) ? 0 : number.hashCode());
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
	Phone other = (Phone) obj;
	if (ddd == null) {
	    if (other.ddd != null)
		return false;
	} else if (!ddd.equals(other.ddd))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (number == null) {
	    if (other.number != null)
		return false;
	} else if (!number.equals(other.number))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Phone [id=" + id + ", number=" + number + ", ddd=" + ddd + "]";
    }

}
