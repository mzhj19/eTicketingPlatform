package com.ZahidHasanJamil.TicketSelling.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketType;
    private String seller;
    private String sellerEmail;
    private String buyer;
    private String date;
    private String fromWhere;
    private String toWhere;
    private String price;
    private boolean sellStatus;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}
