package com.hzkans.crm.modules.wechat.message;

import java.util.List;

/**
 * @author jc
 * @description
 * @create 2018/12/12
 */
public class NewsMessage extends BaseMessage {

    private String ArticleCount;
    private List<Article> Articles;
    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(String articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
