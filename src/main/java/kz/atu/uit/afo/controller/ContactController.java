package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public String getContactList(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false,defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"createdAt"},direction = Sort.Direction.DESC)Pageable pageable,
            Principal principal
            ){
        System.out.println(principal.getName());

        System.out.println(user.getUsername());
        Page<Contact> page = contactService.findAll(user,pageable,filter);
        model.addAttribute("page",page);
        model.addAttribute("pageSort", contactService.getSort(page));
        model.addAttribute("url", contactService.setUrl(filter));
        model.addAttribute("filter", filter);
        return "contactList";
    }

    @GetMapping("contactAdd/{check}")
    public String addContact(
            Model model,
            @RequestParam(required = false, defaultValue = "") String iin,
            @PathVariable String check
    ) {
        if ("stay".equals(check)) {
            model.addAttribute("check", check);
        } else {
            if (contactService.checkIIN(iin)) {
                check = "checking";
                model.addAttribute("error", "Поступающий уже соществует");
                model.addAttribute("check", check);
            } else {
                check = "next";
                model.addAttribute("iin", iin);
                model.addAttribute("error", null);
                model.addAttribute("regions", contactService.getRegions());
                model.addAttribute("check", check);
            }
        }
        return "contactAdd";
    }

    @GetMapping("{contact}")
    public String editContact(
            @PathVariable Contact contact,
            Model model
    ){
        model.addAttribute("contact",contact);
        model.addAttribute("regions",contactService.getRegions());
        model.addAttribute("check","edit");
        return "contactAdd";
    }

    @PostMapping
    public String saveConcat(
            @Valid Contact contact,
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, name = "contactId") Long contactId,
            @RequestParam(required = false,defaultValue = "") String iin
            ){

        if (!contactService.contactAdd(contact, user,contactId,iin)) {
            return "contactAdd";
        }
        return "redirect:/contact";
    }

}
