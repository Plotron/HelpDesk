package com.pg.helpdesk.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExampleDataLoaderInitializer implements InitializingBean {
    @Autowired
    UserInitializer userInitializer;

    @Autowired
    ArticleInitializer articleInitializer;

    @Autowired
    TicketInitializer ticketInitializer;

    public void afterPropertiesSet() throws Exception {
        userInitializer.load();
        articleInitializer.load();
        ticketInitializer.load();
    }
}
