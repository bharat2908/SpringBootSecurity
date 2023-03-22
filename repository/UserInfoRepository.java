package com.Security_1.security_1.repository;

import com.Security_1.security_1.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
   Optional<UserInfo> findByName(String username);
}
