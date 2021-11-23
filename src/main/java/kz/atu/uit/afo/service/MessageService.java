package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Message;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private EntityManager em;

    public Page<Message> messageList(Pageable pageable, String filter){
        if (filter != null && !filter.isEmpty()) {
            return messageRepository.findByTag(filter,pageable);

        } else {
            return messageRepository.findAll(pageable);
        }
    }

    public Page<Message> messageListForUser(Pageable pageable, User currentUser, User author) {
    return messageRepository.findByUser(pageable,author);
    }
}
