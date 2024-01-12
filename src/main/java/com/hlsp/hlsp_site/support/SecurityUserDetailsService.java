// package com.hlsp.hlsp_site.support;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.hlsp.hlsp_site.model.SiteUser;
// import com.hlsp.hlsp_site.repository.SiteUserRepository;

// @Service
// public class SecurityUserDetailsService implements UserDetailsService {
    
//     @Autowired
//     private SiteUserRepository siteUserRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//         Optional<SiteUser> siteUser = siteUserRepository.findUserByEmailAddress(username);

//         //There may be more elegant ways to do this but I understand this fully
//         if(siteUser.isPresent()){
//             return siteUser.get();
//         }else{
//             throw new UsernameNotFoundException("User with matching details not found");
//         }                
//     }

//     public void createUser(UserDetails user) {
//         siteUserRepository.save((SiteUser) user); 
//      } 
// }
