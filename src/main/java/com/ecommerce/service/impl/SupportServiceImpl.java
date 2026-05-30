package com.ecommerce.service.impl;

import com.ecommerce.entity.SupportTicket;
import com.ecommerce.entity.SupportTicket.TicketStatus;
import com.ecommerce.entity.SupportTicket.Priority;
import com.ecommerce.entity.User;
import com.ecommerce.repository.SupportTicketRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.ISupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl 
    implements ISupportService {

    private final SupportTicketRepository 
        supportTicketRepository;
    private final UserRepository userRepository;

    @Override
    public SupportTicket createTicket(Long userId,
            String subject, String message,
            Priority priority) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        SupportTicket ticket = new SupportTicket();
        ticket.setUser(user);
        ticket.setSubject(subject);
        ticket.setMessage(message);
        ticket.setPriority(priority);
        ticket.setStatus(TicketStatus.OPEN);

        return supportTicketRepository.save(ticket);
    }

    @Override
    public List<SupportTicket> getTicketsByUserId(
            Long userId) {
        return supportTicketRepository
            .findByUserId(userId);
    }

    @Override
    public SupportTicket getTicketById(Long id) {
        return supportTicketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Ticket not found!"));
    }

    @Override
    public List<SupportTicket> getAllTickets() {
        return supportTicketRepository.findAll();
    }

    @Override
    public List<SupportTicket> getTicketsByStatus(
            TicketStatus status) {
        return supportTicketRepository
            .findByStatus(status);
    }

    @Override
    public SupportTicket replyToTicket(
            Long ticketId, String adminReply) {
        SupportTicket ticket = getTicketById(ticketId);
        ticket.setAdminReply(adminReply);
        ticket.setStatus(TicketStatus.IN_PROGRESS);
        return supportTicketRepository.save(ticket);
    }

    @Override
    public SupportTicket updateTicketStatus(
            Long ticketId, TicketStatus status) {
        SupportTicket ticket = getTicketById(ticketId);
        ticket.setStatus(status);
        return supportTicketRepository.save(ticket);
    }

    @Override
    public int getOpenTicketsCount() {
        return supportTicketRepository
            .countByStatus(TicketStatus.OPEN);
    }
}