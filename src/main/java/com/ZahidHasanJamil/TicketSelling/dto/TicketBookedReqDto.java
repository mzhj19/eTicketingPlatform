package com.ZahidHasanJamil.TicketSelling.dto;

import com.ZahidHasanJamil.TicketSelling.model.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketBookedReqDto {
    private int ticketNo;
    private User user;
}
