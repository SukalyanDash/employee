package com.employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Entity.Tax;

@Repository
public interface TaxRepo extends JpaRepository<Tax, Long>{

}
