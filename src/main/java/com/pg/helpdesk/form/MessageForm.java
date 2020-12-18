package com.pg.helpdesk.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MessageForm {
    @NotNull
    String content;
}
