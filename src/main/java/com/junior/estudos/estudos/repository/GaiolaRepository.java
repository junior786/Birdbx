package com.junior.estudos.estudos.repository;

import com.junior.estudos.estudos.entidade.Gaiola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface GaiolaRepository extends JpaRepository<Gaiola, Long> {


}
