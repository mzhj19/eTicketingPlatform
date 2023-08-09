package com.ZahidHasanJamil.TicketSelling.constant;

public class WebApiUrlConstants {
    public final static String API_URI_PREFIX = "/api/v1";
    public final static String PATH_VAR_ID = "/{id}";

    // User
    public final static String USER_API = API_URI_PREFIX + "/user";
    public final static String USER_REGISTER_API = USER_API + "/register";
    public final static String USER_LOGIN_API = USER_API + "/login";

    // Ticket
    public final static String TICKET_API = API_URI_PREFIX + "/ticket";
    public final static String TICKET_SAVE_API = TICKET_API + "/save";
    public final static String TICKET_EDIT_API = TICKET_API + "/edit" + PATH_VAR_ID;
    public final static String TICKET_SEARCH_API = TICKET_API + "/search";
    public final static String TICKET_BUYABLE_API = TICKET_API + "/buyable";
    public final static String TICKET_BUY_API = TICKET_API + "/buy";
    public final static String TICKET_REFUND_API = TICKET_API + "/refund";
    public final static String TICKET_REFUND_FINALIZE_API = TICKET_API + "/refund-finalize";

    // Report
    public final static String REPORT_API = API_URI_PREFIX + "/report";
    public final static String SOLD_SUMMARY_API = REPORT_API + "/sold-summary";

}
