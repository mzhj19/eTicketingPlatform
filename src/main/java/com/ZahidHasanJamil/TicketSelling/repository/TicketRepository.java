package com.ZahidHasanJamil.TicketSelling.repository;

import com.ZahidHasanJamil.TicketSelling.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t " +
            "WHERE (:fromWhere IS NULL OR t.fromWhere = :fromWhere) " +
            "AND (:toWhere IS NULL OR t.toWhere = :toWhere) " +
            "AND (:ticketType IS NULL OR t.toWhere = :ticketType) " +
            "AND (:tDate IS NULL OR t.date = :tDate) " +
            "AND (:price IS NULL OR t.price = :price)")
    List<Ticket> findBySearchReqDtoObject(
            @Param("fromWhere") String fromWhere,
            @Param("toWhere") String toWhere,
            @Param("ticketType") String ticketType,
            @Param("tDate") String tDate,
            @Param("price") String price
    );
}
