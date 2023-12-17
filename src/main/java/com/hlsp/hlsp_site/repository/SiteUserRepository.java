package com.hlsp.hlsp_site.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hlsp.hlsp_site.model.SiteUser;

@Repository
public interface SiteUserRepository extends CrudRepository<SiteUser, Integer> {

    @Query("SELECT user FROM SiteUser user WHERE user.emailAddress = :emailAddress AND user.passwordHash = :passwordHash")
    List<SiteUser> logInAsUser(String emailAddress, byte[] passwordHash);

    @Query("SELECT user.emailAddress FROM SiteUser user WHERE user.emailAddress = :emailAddress")
    List<String> checkIfEmailIsUsed(String emailAddress);

    @Query("Select user.salt FROM SiteUser user WHERE user.emailAddress = :emailAddress")
    List<byte[]> getSaltForLogin(String emailAddress);
}
