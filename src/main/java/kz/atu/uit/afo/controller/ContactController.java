package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public String getContactList(
            @RequestParam(required = false,defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"createdAt"},direction = Sort.Direction.DESC)Pageable pageable
            ){
        Page<Contact> page = contactService.findAll(pageable,filter);
        model.addAttribute("page",page);
        return "contactList";
    }

}
