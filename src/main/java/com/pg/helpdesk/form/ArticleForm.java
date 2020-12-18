package com.pg.helpdesk.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleForm {
    @NotNull
    String title;

    @NotNull
    String content;

    //TODO: tags
}
