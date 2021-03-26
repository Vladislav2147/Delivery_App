package com.shichko.delivery_service.model.service;

import com.shichko.delivery_service.model.entity.User;
import com.shichko.delivery_service.model.repository.RoleRepository;
import com.shichko.delivery_service.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByEmail(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        //TODO delete this
        user.setFirstName("Ivan");
        user.setSecondName("Ivanov");
        user.setPhone("+375291234567");

        user.setRoles(Collections.singleton(roleRepository.findFirstByName("ROLE_NOT_CONFIRMED")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM usr u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public void confirmUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.getRoles().add(roleRepository.findFirstByName("ROLE_USER"));
            userRepository.save(user);
        });
    }
}