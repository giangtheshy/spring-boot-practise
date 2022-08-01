package com.practise.services;

import com.practise.dto.AuthenticationResponse;
import com.practise.dto.LoginRequest;
import com.practise.dto.RegisterRequest;
import com.practise.exceptions.SpringException;
import com.practise.models.Role;
import com.practise.models.User;
import com.practise.repositories.RoleRepository;
import com.practise.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.elasticsearch.common.geo.GeoPoint;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public void register(RegisterRequest registerRequest) {
    System.out.println(registerRequest);
    User user = new User();
    if (registerRequest.getId() != null) {
      user.setId(registerRequest.getId());
      userRepository.deleteRelation(registerRequest.getId());
    }
    user.setUsername(registerRequest.getUsername());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setEnabled(true);
    user.setRoles(mapToRole(registerRequest.getRoles()));
    userRepository.save(user);
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //        String token = jwtProvider.generateToken(authentication);
    //        User user = getCurrentUser();
    User user =
        userRepository
            .findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new SpringException("Not found " + "User"));

    return AuthenticationResponse.builder()
        .username(user.getUsername())
        .roles(user.getRoles())
        .build();
  }
  public Set<Role> mapToRole(Set<Role> roles){
    return roles.stream().map(role->roleRepository.findByName(role.getName())).collect(Collectors.toSet());
  }

  public com.practise.elasticsearch.User toEsEntity(com.practise.models.User user) {
        if ( user == null ) {
            return null;
        }

        com.practise.elasticsearch.User.UserBuilder user1 = com.practise.elasticsearch.User.builder();

        double lon = 111D;
        double lat =222D;

        GeoPoint geoPoint = new GeoPoint(lat, lon);

        user1.location(geoPoint);

        user1.username( user.getUsername() );

        List<Passion> list1 = user.getPassions();
        if ( list1 != null ) {
            user1.passions( new ArrayList<Passion>( list1 ) );
        }
        user1.gender( user.getGender() );
        user1.yearOfBirth( user.getDateOfBirth().getYear() );

        return user1.build();
    }
}
