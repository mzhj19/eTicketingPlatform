package com.ZahidHasanJamil.TicketSelling.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Setter
@Getter
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
