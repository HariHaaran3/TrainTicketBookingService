package com.example.trainbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.trainbooking.entity.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    @Query("SELECT ud FROM UserDetail ud WHERE ud.userName = :userName AND ud.firstName = :firstName AND ud.lastName = :lastName AND ud.email = :email")
    Optional<UserDetail> findByUserDetails(@Param("userName") String userName,
                                           @Param("firstName") String firstName,
                                           @Param("lastName") String lastName,
                                           @Param("email") String email);

    @Query("SELECT ud FROM UserDetail ud WHERE ud.userName = :userName")
    Optional<UserDetail> findByUserName(@Param("userName") String userName);
}