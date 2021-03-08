package com.junior.estudos.estudos.repository;

import com.junior.estudos.estudos.entidade.Roletb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Roletb, String> {

}
