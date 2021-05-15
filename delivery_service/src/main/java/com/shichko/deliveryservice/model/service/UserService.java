package com.shichko.deliveryservice.model.service;

import com.shichko.deliveryservice.controller.mapper.UserMapper;
import com.shichko.deliveryservice.exception.DeliveryServiceException;
import com.shichko.deliveryservice.model.dto.StatsDto;
import com.shichko.deliveryservice.model.dto.UserDto;
import com.shichko.deliveryservice.model.entity.User;
import com.shichko.deliveryservice.model.repository.RoleRepository;
import com.shichko.deliveryservice.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
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
    @Autowired
    UserMapper mapper;

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

    public void saveUser(UserDto userDto) throws DeliveryServiceException {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new DeliveryServiceException("Passwords not equals");
        }

        User user = mapper.dtoToEntity(userDto);
        User userFromDB = userRepository.findByEmail(user.getUsername());

        if (userFromDB != null) {
            throw new DeliveryServiceException("User already exists");
        }

        user.setRoles(Collections.singleton(roleRepository.findFirstByName("ROLE_BASIC")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void grantUserRole(Long id, String role) {
        userRepository.findById(id).ifPresent(user -> {
            user.getRoles().add(roleRepository.findFirstByName(role));
            userRepository.save(user);
        });
    }
    public void revokeUserRole(Long id, String role) {
        userRepository.findById(id).ifPresent(user -> {
            user.getRoles().remove(roleRepository.findFirstByName(role));
            userRepository.save(user);
        });
    }

    public StatsDto getStats(long courierId) {
        int orderCount = getDeliveredOrdersAmount(courierId);
        int inTimeCount = getDeliveredInTimeOrdersAmount(courierId);

        StatsDto stats = new StatsDto(courierId, orderCount, inTimeCount);
        return stats;
    }

    private int getDeliveredOrdersAmount(long courierId) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("[delivered_orders_amount_by_courier_id]");
        query.registerStoredProcedureParameter("id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("count", Integer.class, ParameterMode.OUT);
        query.setParameter("id", courierId);
        query.execute();
        return (int)query.getOutputParameterValue("count");
    }

    private int getDeliveredInTimeOrdersAmount(long courierId) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("[delivered_orders_in_time_by_courier_id]");
        query.registerStoredProcedureParameter("id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("count", Integer.class, ParameterMode.OUT);
        query.setParameter("id", courierId);
        query.execute();
        return (int)query.getOutputParameterValue("count");
    }
}