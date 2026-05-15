package com.sopt.bunjang.domain.user.repository;

import com.sopt.bunjang.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
