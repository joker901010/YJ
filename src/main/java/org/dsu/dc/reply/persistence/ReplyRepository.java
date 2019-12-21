package org.dsu.dc.reply.persistence;

import org.dsu.dc.article.domain.Article;
import org.dsu.dc.reply.domain.Reply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReplyRepository extends CrudRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r WHERE r.article = ?1 AND r.replyNo > 0 ORDER BY r.replyNo ASC")
    public List<Reply> getRepliesOfArticle(Article article);

}