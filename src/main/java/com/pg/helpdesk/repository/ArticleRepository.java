package com.pg.helpdesk.repository;

import com.pg.helpdesk.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByOrderByCreationDateDesc(Pageable pageable);

    List<Article> findTop5ByOrderByCreationDateDesc();

    List<Article> findTop3ByOrderByLastModifiedDateDesc();

    Page<Article> findArticlesByTitleContainingIgnoreCase(String title, Pageable pageable);
}
