package com.frank.repository.mysql;

import com.frank.entity.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Modifying
    @Query(value = "update user_info set phone = ?1 where id = ?2", nativeQuery = true)
    int updatePhone(Long phone, Long id);

    User findByPhone(Long phone);

}
