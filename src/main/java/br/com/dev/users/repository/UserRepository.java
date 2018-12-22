package br.com.dev.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dev.users.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
