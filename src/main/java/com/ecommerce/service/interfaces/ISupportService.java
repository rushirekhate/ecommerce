package com.ecommerce.service.interfaces;

import com.ecommerce.entity.SupportTicket;
import com.ecommerce.entity.SupportTicket.TicketStatus;
import com.ecommerce.entity.SupportTicket.Priority;
import java.util.List;

public interface ISupportService {

    // User
    SupportTicket createTicket(Long userId,
                              String subject,
                              String message,
                              Priority priority);

    List<SupportTicket> getTicketsByUserId(Long userId);
    
    SupportTicket getTicketById(Long id);

    // Admin
    List<SupportTicket> getAllTickets();
    
    List<SupportTicket> getTicketsByStatus(
        TicketStatus status);
    
    SupportTicket replyToTicket(Long ticketId,
                               String adminReply);
    
    SupportTicket updateTicketStatus(Long ticketId,
                                   TicketStatus status);
    
    int getOpenTicketsCount();
}