package com.ZahidHasanJamil.TicketSelling.repository;

import com.ZahidHasanJamil.TicketSelling.model.SoldTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldTicketRepository extends JpaRepository<SoldTicket, Long> {
}
