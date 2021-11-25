package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.domain.Role;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.RegionRepository;
import kz.atu.uit.afo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private RegionRepository regionRepository;

    @GetMapping("/registration")
    public String registration(Model model){
        Iterable<Region> region = regionRepository.findAll();
        User user = new User();
        user.setRoles(Collections.singleton(Role.Гость));
        user.setActive(true);
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("regions", region);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam("region")Region region,
                          @RequestParam Map<String, String> form){
        if(bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtill.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if(!userService.addUser(user,form)){
            model.addAttribute("usernameError","User exists!");
            return "registration";
        }

        user.setRegion(region);
        return "redirect:/login";
    }
}
