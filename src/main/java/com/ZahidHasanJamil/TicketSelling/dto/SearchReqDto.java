package com.ZahidHasanJamil.TicketSelling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchReqDto implements Serializable {
    private static final Long serialVersionUID = 1L;

    private String ticketType;
    private String tDate;
    private String fromWhere;
    private String toWhere;
    private String price;
}
