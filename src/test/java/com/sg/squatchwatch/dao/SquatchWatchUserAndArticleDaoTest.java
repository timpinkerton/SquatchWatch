/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.dao;

import com.sg.squatchwatch.model.Article;
import com.sg.squatchwatch.model.Authorities;
import com.sg.squatchwatch.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author austinmann
 */
public class SquatchWatchUserAndArticleDaoTest {
    
    SquatchWatchUserAndArticleDao dao;
    
    public SquatchWatchUserAndArticleDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");
        dao = (SquatchWatchUserAndArticleDao)ctx.getBean("squatchWatchUserAndArticleDao");
        
        cleanUp();
    }
    
    @After
    public void tearDown() {
        cleanUp();
    }

    @Test
    public void testAddUser() {
        User newUser = addNewUser();
//        dao.addUser(newUser);
        List<User> userList = dao.getAllUsers();
        assertEquals(userList.size(), 1);
    }

    @Test
    public void testDeleteUser() {
        User newUser = addNewUser();
//        dao.addUser(newUser);
        List<User> userList = dao.getAllUsers();
        assertEquals(userList.size(), 1);
        
        dao.deleteUser(newUser.getUserId());
        assertNull(dao.getUser(newUser.getUserId()));
    }

    @Test
    public void testUpdateUser() {
        User newUser = addNewUser();
//        dao.addUser(newUser);
        newUser.setUserName("JimmyJay");
        dao.updateUser(newUser);
        User fromDb = dao.getUser(newUser.getUserId());
        assertEquals(newUser, fromDb);
    }

    @Test
    public void testGetUser() {
        User user = addNewUser();
//        dao.addUser(user1);
        User user3 = dao.getUser(user.getUserId());
        assertEquals(user, user3);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = addNewUser();
//        dao.addUser(user1);
        User user2 = addNewUser();
//        dao.addUser(user2);
        
        List<User> userList = dao.getAllUsers();
        assertEquals(userList.size(), 2);
    }

    @Test
    public void testAddArticle() {
        Article newArt = addNewArticle();
        dao.addArticle(newArt);
        List<Article> artList = dao.getAllArticles();
        assertEquals(artList.size(), 1);
    }

    @Test
    public void testDeleteArticle() {
        Article newArt = addNewArticle();
        dao.addArticle(newArt);
        List<Article> artList = dao.getAllArticles();
        assertEquals(artList.size(), 1);
        
        dao.deleteArticle(newArt.getArticleId());
        assertNull(dao.getArticle(newArt.getArticleId()));
    }

    @Test
    public void testUpdateArticle() {
    }

    @Test
    public void testGetArticle() {
    }

    @Test
    public void testGetAllArticles() {
    }
    
    public void cleanUp() {
        
        dao.deleteAllAuthorities();
        
        List<User> user = dao.getAllUsers();
        user.forEach((currentUser) -> {
            dao.deleteUser(currentUser.getUserId());
        });
        
        List<Article> article = dao.getAllArticles();
        article.forEach((currentArticle) -> {
            dao.deleteArticle(currentArticle.getArticleId());
        });
    }
    
    public User addNewUser() {
        Authorities auth = new Authorities();
        auth.setAuthority("admin");
        
        User user1 = new User();
        user1.setUserName("TotallyNotABigfoot");
        user1.setPassword("password");
        user1.setEnabled(1);
        List<Authorities> authList = new ArrayList<>();
        authList.add(auth);
        user1.setPermission(authList);
//        user1.setUserId(5);
        
        dao.addUser(user1); 
        
        return user1;
    }
    
    public Article addNewArticle() {
        User user = addNewUser();
        Article art = new Article();
        art.setUser(user);
        art.setArticleDate(LocalDate.now());
        art.setContent("Generic Article Content Blah Blah Blah Blurry Bigfoot Pictures");
        art.setUserId(user.getUserId());
        return art;
    }
}
