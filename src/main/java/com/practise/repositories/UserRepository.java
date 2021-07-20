package com.practise.repositories;

import com.practise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  @Modifying
  @Query(value = "delete from user_roles where user_id= :user_id",nativeQuery = true)
  void deleteRelation(@Param("user_id") Long user_id);
}
