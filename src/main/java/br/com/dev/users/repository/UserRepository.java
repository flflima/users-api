package br.com.dev.users.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.dev.users.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
