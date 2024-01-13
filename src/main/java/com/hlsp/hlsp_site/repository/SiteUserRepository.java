package com.hlsp.hlsp_site.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hlsp.hlsp_site.model.SiteUser;

@Repository
public interface SiteUserRepository extends CrudRepository<SiteUser, Integer> {

    // Optional<SiteUser> findUserByEmailAddress(String emailAddress); 

    @Query("SELECT user FROM SiteUser user WHERE user.emailAddress = :emailAddress AND user.passwordHash = :passwordHash")
    List<SiteUser> logInAsUser(String emailAddress, byte[] passwordHash);

    @Query("SELECT user.emailAddress FROM SiteUser user WHERE user.emailAddress = :emailAddress")
    List<String> getUsersEmailByEmail(String emailAddress);

    @Query("Select user.salt FROM SiteUser user WHERE user.emailAddress = :emailAddress")
    List<byte[]> getSaltForLogin(String emailAddress);

    @Query("SELECT user FROM SiteUser user WHERE user.emailAddress = :emailAddress")
    List<SiteUser> getUserDetailsByEmail(String emailAddress);
}
