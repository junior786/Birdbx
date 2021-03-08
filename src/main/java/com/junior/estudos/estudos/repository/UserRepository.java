package com.junior.estudos.estudos.repository;

import com.junior.estudos.estudos.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
}
