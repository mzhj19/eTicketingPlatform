package com.ZahidHasanJamil.TicketSelling.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SoldTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketType;
    private LocalDateTime soldDate;
    private String amountInTk;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
