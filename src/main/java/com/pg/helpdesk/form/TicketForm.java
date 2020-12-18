package com.pg.helpdesk.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TicketForm {
    @NotNull
    String title;

    String message;
}
