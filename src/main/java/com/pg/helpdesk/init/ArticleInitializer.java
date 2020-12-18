package com.pg.helpdesk.init;

import com.pg.helpdesk.entity.Article;
import com.pg.helpdesk.entity.User;
import com.pg.helpdesk.repository.ArticleRepository;
import com.pg.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;

@Component
public class ArticleInitializer implements DataLoader{

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void load() {
        if (articleRepository.count() == 0) {
            User user = userRepository.findByUsername("admin");

            //1
            Article article = new Article();
            article.setTitle("Super Device 2.0 Doesn't Turn On");
            article.setContent("If your Super Device 2.0 doesn't turn on, you have to hold the power button for 5 seconds to cycle power. Afterwards, you have to reinsert the battery.");

            LocalDateTime time = LocalDateTime.now();
            article.setCreationDate(time);
            article.setLastModifiedDate(time);
            article.setLastModifiedBy(user);

            article.setTags(new HashSet<>());
            article.getTags().add("Super Device");
            article.getTags().add("power");
            article.getTags().add("dead");
            article.getTags().add("turn on");

            articleRepository.save(article);

            //2
            article = new Article();
            article.setTitle("Super Device 2.0 Makes Buzzing Sounds");
            article.setContent("If your Super Device 2.0 makes strange buzzing noises, you can try cleaning the fan. If that doesn't help, you should send it to us for repairs.");
            article.setCreationDate(time);
            article.setLastModifiedDate(time);
            article.setLastModifiedBy(user);

            article.setTags(new HashSet<>());
            article.getTags().add("Super Device");
            article.getTags().add("fan");
            article.getTags().add("noise");
            article.getTags().add("buzz");
            article.getTags().add("buzzing");
            article.getTags().add("loud");

            articleRepository.save(article);

            //3
            article = new Article();
            article.setTitle("Super Device 1.0 Displays Black Screen");
            article.setContent("If your Super Device 1.0 displays a black screen no matter the monitor or cable, the RAM is probably faulty. Since it is not possible for the user to change the installed RAM, it is advised to send the Super Device to us for repairs..");
            article.setCreationDate(time);
            article.setLastModifiedDate(time);
            article.setLastModifiedBy(user);

            article.setTags(new HashSet<>());
            article.getTags().add("Super Device");
            article.getTags().add("dead");
            article.getTags().add("turn on");
            article.getTags().add("black screen");
            article.getTags().add("no display");
            article.getTags().add("display");

            articleRepository.save(article);
        }
    }
}
