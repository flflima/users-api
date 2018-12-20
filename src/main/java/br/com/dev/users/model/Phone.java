package br.com.dev.users.model;

import java.io.Serializable;

public class Phone implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4833085193795807532L;

    private String number;
    private String ddd;

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
	if (number == null) {
	    if (other.number != null)
		return false;
	} else if (!number.equals(other.number))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Phone [number=" + number + ", ddd=" + ddd + "]";
    }

}
