package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.UserRole;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByCode(String code);

}
