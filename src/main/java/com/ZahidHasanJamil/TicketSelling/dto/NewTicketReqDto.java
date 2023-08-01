package com.ZahidHasanJamil.TicketSelling.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewTicketReqDto implements Serializable {
    private static final Long serialVersionUID = 1L;

    @NotBlank(message = "TICKET TYPE IS REQUIRED")
    private String ticketType;
    @NotBlank(message = "LOCAL DATE IS REQUIRED")
    private String date;
    @NotBlank(message = "FROM WHERE IS REQUIRED")
    private String fromWhere;
    @NotBlank(message = "TO WHERE IS REQUIRED")
    private String toWhere;
    @NotBlank(message = "PRICE IS REQUIRED")
    private String price;
}
