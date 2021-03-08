package com.junior.estudos.estudos.repository;

import com.junior.estudos.estudos.entidade.Gaiola;
import com.junior.estudos.estudos.entidade.Passaro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassaroRepository extends JpaRepository<Passaro,Long> {
    Passaro findById(long numero);

}
