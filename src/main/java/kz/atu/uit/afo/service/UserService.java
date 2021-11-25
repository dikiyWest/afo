package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.domain.Role;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);


        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не существует");
        } else {
            user.setUpdatedAt();
            userRepository.save(user);
        }
        return user;
    }

    public boolean addUser(User user, Map<String, String> form) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        System.out.println(user.toString());
        saveUser(user,user.getUsername(),form);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        setParameters(user, form);
        userRepository.save(user);
    }

    private void setParameters(User user, Map<String, String> form){
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        System.out.println(user.toString());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (!form.get(key).isEmpty()) {
                if (roles.contains(key)) {
                    user.getRoles().add(Role.valueOf(key));
                }
                switch (key) {
                    case ("password"):
                        user.setPassword(passwordEncoder.encode(form.get(key)));
                        break;
                    case ("active"):
                        if(form.get(key).equals("on")){
                            user.setActive(true);
                        }else {
                            user.setActive(false);
                        }
                        break;
                    case ("fio"):
                        user.setFio(form.get(key));
                        break;
                    case ("email"):
                        user.setEmail(form.get(key));
                        break;
                    case ("iin"):
                        user.setIin(form.get(key));
                        break;
                    case ("phone"):
                        user.setPhone(form.get(key));
                        break;
                    case ("placeOfWork"):
                        user.setPlaceOfWork(form.get(key));
                        break;
                    default:
                        break;
                }

            }
        }
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
