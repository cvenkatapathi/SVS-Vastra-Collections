package com.svs.svscollections;

import com.svs.svscollections.model.ContactMessage;
import com.svs.svscollections.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @PostMapping("/contact/send")
    public String sendMessage(@ModelAttribute ContactMessage contactMessage) {

        contactMessageRepository.save(contactMessage);

        return "redirect:/contact?success";
    }
}