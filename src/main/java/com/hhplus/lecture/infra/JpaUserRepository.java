package com.hhplus.lecture.infra;

import com.hhplus.lecture.business.entity.Users;
import com.hhplus.lecture.business.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<Users, Long>, UserRepository {
    @Override
    default Users saveUsers(Users users) {
        return save(users);
    }

    @Override
    default Users getUser(Long userId) {
        return findById(userId).orElse(null);
    }
}
