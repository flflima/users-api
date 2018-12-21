package br.com.dev.users.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.dev.users.model.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

}
