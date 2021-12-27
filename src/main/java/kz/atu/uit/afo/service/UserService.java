package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.domain.Role;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.UserRepository;
import kz.atu.uit.afo.service.reportService.UserExcelReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public boolean addUser(User user, Map<String, String> form, Region region) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setRegion(region);
        user.setRoles(new HashSet<>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return true;
    }

    public Page<User> findAll(Pageable pageable, String filter) {
        if (filter != null && !filter.isEmpty()) {
            Page<User> user = userRepository.findByIinContaining(filter, pageable);
            if (user == null || user.isEmpty()) {
                user = userRepository.findByFioContaining(filter, pageable);
            }
            return user;
        } else {
            return userRepository.findAll(pageable);
        }

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public String setUrl(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return "/user?filter=" + filter + "&";
        } else {
            return "/user?";
        }

    }


    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        setParameters(user, form);
        userRepository.save(user);
    }

    private void setParameters(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
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

    public String getSort(Page<User> page) {
        return page.getSort().toString().replace(": ", ",");
    }

    public void getReportExcel(String dateMin, String dateMax, HttpServletResponse response) throws IOException {
        List<User> listUsers;
        LocalDate datePartMax;

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        LocalTime time = LocalTime.parse("00:00:00");

        if (dateMin.equals("") && dateMax.equals("")) {
            listUsers = userRepository.findAll();

        } else {
            if (dateMax.equals("") || dateMax == null) {
                datePartMax = LocalDate.now();
            } else {
                datePartMax = LocalDate.parse(dateMax);
            }
            LocalDate datePartMin = LocalDate.parse(dateMin);
            listUsers = userRepository.findByCreatedAtBetween(LocalDateTime.of(datePartMin, time), LocalDateTime.of(datePartMax, time));
        }


        UserExcelReporter excelExporter = new UserExcelReporter(listUsers);
        excelExporter.export(response);
    }
}
