package br.com.dev.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dev.users.model.Phone;

public interface PhoneRepository extends JpaRepository<Phone, String> {

}
