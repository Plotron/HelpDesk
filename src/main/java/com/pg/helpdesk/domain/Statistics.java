package com.pg.helpdesk.domain;

import lombok.Data;

@Data
public class Statistics {
    Long closedTickets;
    Long openTickets;
    Long newTickets;
}
