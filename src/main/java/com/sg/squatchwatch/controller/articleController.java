/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.controller;

import com.sg.squatchwatch.dao.SquatchWatchUserAndArticleDao;
import com.sg.squatchwatch.model.Article;
import com.sg.squatchwatch.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author christophercunningham
 */
@Controller
public class articleController {

    SquatchWatchUserAndArticleDao dao;
    String error;

    @Inject
    public articleController(SquatchWatchUserAndArticleDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/displayGetAllArticles", method = RequestMethod.GET)
    public String displayArticlePage(Model model) {
        // Get all the articles from the DAO
        List<Article> articleList = dao.getAllArticles();
        model.addAttribute("articleList", articleList);
        return "/Article/article";
    }

    @RequestMapping(value = "/displayAddArticleForm", method = RequestMethod.GET)
    public String displayAddArticleForm(Model model) {
        // Need to add user ID to be passed (enable wrighting)
        return "Article/addArticleForm";
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.GET)
    public String deleteArticle(HttpServletRequest request, Model model) {
        String articleIdParameter = request.getParameter("articleId");
        try {
            int articleId = Integer.parseInt(articleIdParameter);
            dao.deleteArticle(articleId);
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Data doesn't exist to be deleted";
            model.addAttribute("error", error);
        }
        return "redirect:Article";
    }

    @RequestMapping(value = "/displayEditArticleForm", method = RequestMethod.GET)
    public String displayArticleEdit(HttpServletRequest request, Model model) {
        String articleIdParameter = request.getParameter("articleId");
        try {
            int articleId = Integer.parseInt(articleIdParameter);
            Article article = dao.getArticle(articleId);
            model.addAttribute("article", article);
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Article does not exist";
            model.addAttribute("error", error);
            // error gets printed, once.
            error = null;
        }

        return "Article/editArticleForm";
    }

//    @RequestMapping(value = "updateItem", method = RequestMethod.POST)
//    public String updateItem(HttpServletRequest request) {
//        Item item = new Item(request.getParameter("itemName")),
//                request.getParameter("description"),
//                new Long(request.getParameter("itemValue"));
//                
//
//       
//
//       
//        dao.updateItem(item);
//        return "redirect:displayItemPage";
//    }
    @RequestMapping(value = "/createArticle", method = RequestMethod.POST)
    public String createArticle(HttpServletRequest request, Model model) {

        Article article = new Article();
        User user = new User();

//        String articleDateString =request.getParameter("articleDate");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
//        LocalDate localDate = LocalDate.parse(articleDateString, formatter);
        LocalDate articleDate = LocalDate.now();

        try {
            article.setArticleDate(articleDate);
            article.setContent(request.getParameter("content"));
            // user = article.setUser(request.getParameter("user"));

            //User ID is currently set to 2 by default; need to get the id from 
            //  from the logged in user...
            article.setUserId(2);
            //only if the previous steps work do you add it.
            dao.addArticle(article);
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            System.out.println("Data doesn't exist to be pulled"); //todo fix this when not on a plane
            error = "Trouble getting the article to create, creation failed";
            model.addAttribute("error", error);
        }

        return "redirect:Article";
    }

    @RequestMapping(value = "/Article", method = RequestMethod.GET)
    public String displayArticles(HttpServletRequest request, Model model) {

        List<Article> articleList = dao.getAllArticles();
        
        model.addAttribute("articleList", articleList);
        model.addAttribute("error", error);
        // use the error once, then it clears.
        error = null;

        return "Article/article";
    }
    
    
    @RequestMapping(value = "/editArticle", method = RequestMethod.POST)
    public String editArticle(HttpServletRequest request) {
        
        try {
        String articleContent = request.getParameter("content"); 
        String articleIdParameter = request.getParameter("articleId"); 
        int articleId = Integer.parseInt(articleIdParameter); 

        LocalDate articleDate = LocalDate.now();
        
        Article article = dao.getArticle(articleId); 
        
        article.setContent(articleContent);
        article.setArticleDate(articleDate);
        
        dao.updateArticle(article);
        } catch (NumberFormatException ex) {
            error = "The article you wanted to edit does not exist.";
        } catch ( DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "There was a problem updating the article, changes will not be saved.";
        }
        
        return "redirect:Article"; 
    }

}
