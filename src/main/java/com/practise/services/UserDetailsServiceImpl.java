package com.practise.services;

import com.practise.models.Role;
import com.practise.models.User;
import com.practise.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) {
    System.out.println("loadUserByUsername of UserDetailsImpl " + username);
    Optional<User> userOptional = userRepository.findByUsername(username);
    User user =
        userOptional.orElseThrow(
            () -> new UsernameNotFoundException("No user " + "Found with username : " + username));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.getEnabled(),
        true,
        true,
        true,
        getAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
    //        return singletonList(new SimpleGrantedAuthority(roles));
  }
}
