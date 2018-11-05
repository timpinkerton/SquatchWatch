/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.dao;

import com.sg.squatchwatch.model.Article;
import com.sg.squatchwatch.model.User;
import java.util.List;

/**
 *
 * @author austinmann
 */
public interface SquatchWatchUserAndArticleDao {
    
    public User addUser(User user);
    
    public void deleteUser(int id);
    
    public void updateUser(User user);
    
    public User getUser(int id);
    
    public List<User> getAllUsers();
    
    public User getUserByArticleId(int articleId); 
    
    public Article addArticle(Article article);
    
    public void deleteArticle(int id);
    
    public void updateArticle(Article article);
    
    public Article getArticle(int id);
    
    public List<Article> getAllArticles();
    
    public void deleteAllAuthorities();
    
}
