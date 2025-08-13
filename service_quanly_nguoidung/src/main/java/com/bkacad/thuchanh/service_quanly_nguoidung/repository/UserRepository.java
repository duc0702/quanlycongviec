package com.bkacad.thuchanh.service_quanly_nguoidung.repository;
import com.bkacad.thuchanh.service_quanly_nguoidung.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
   Optional<User>  findByEmail(String email);

    Optional<User>  findByUsername(String userName);
}
