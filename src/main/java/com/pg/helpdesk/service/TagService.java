package com.pg.helpdesk.service;

import com.pg.helpdesk.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    ArticleRepository articleRepository;
}
