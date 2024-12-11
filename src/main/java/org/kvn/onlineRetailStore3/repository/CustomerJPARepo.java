package org.kvn.onlineRetailStore3.repository;

import org.kvn.onlineRetailStore3.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJPARepo extends JpaRepository<CustomerEntity, Integer> {
}
