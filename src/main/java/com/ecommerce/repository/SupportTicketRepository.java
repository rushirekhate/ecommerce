package com.ecommerce.repository;

import com.ecommerce.entity.SupportTicket;
import com.ecommerce.entity.SupportTicket.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SupportTicketRepository 
    extends JpaRepository<SupportTicket, Long> {

    List<SupportTicket> findByUserId(Long userId);
    
    List<SupportTicket> findByStatus(TicketStatus status);
    
    List<SupportTicket> findByUserIdAndStatus(
        Long userId, TicketStatus status);
    
    int countByStatus(TicketStatus status);
}