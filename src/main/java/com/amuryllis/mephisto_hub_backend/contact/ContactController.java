package com.amuryllis.mephisto_hub_backend.contact;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactMessageRepository repository;

    public ContactController(ContactMessageRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submit(@Valid @RequestBody ContactMessageRequest request) {
        var message = new ContactMessage(request.name(), request.email(), request.message());
        repository.save(message);
    }

    @GetMapping
    public List<ContactMessage> list() {
        return repository.findAll();
    }
}
