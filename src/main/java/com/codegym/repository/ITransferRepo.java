package com.codegym.repository;

import com.codegym.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransferRepo extends JpaRepository<Transfer, Long> {

}
