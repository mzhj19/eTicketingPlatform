package com.ZahidHasanJamil.TicketSelling.repository;

import com.ZahidHasanJamil.TicketSelling.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
