/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.dao;

import com.sg.squatchwatch.model.Article;
import com.sg.squatchwatch.model.Authorities;
import com.sg.squatchwatch.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.text.DateFormatter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author omish
 */
public class SquatchWatchUserAndArticleDaoImpl implements SquatchWatchUserAndArticleDao {
    
    private JdbcTemplate jdbcTemplate;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        System.err.println("WE HAVE ARE PUTTING IN A JDBCTEMPLATE");
        this.jdbcTemplate = jdbcTemplate;
    }
//==============================================================================
//==============================================================================
// _   _               
//| | | |              
//| | | |___  ___ _ __ 
//| | | / __|/ _ \ '__|
//| |_| \__ \  __/ |   
// \___/|___/\___|_|  
//==============================================================================
//==============================================================================

    private static final String SQL_INSERT_USER
            = "INSERT INTO users "
            + "(userName, password, enabled) "
            + "VALUES (?, ?, ?)";

    private static final String SQL_INSERT_AUTH 
            = "INSERT INTO authorities "
            + "(user_id, username, authority) "
            + "VALUES (?, ?, ?)";
            
    private static final String SQL_GET_USER
            = "SELECT * FROM users "
            + "INNER JOIN authorities ON users.username = authorities.username "
            + "WHERE users.user_id = ?";
    
    private static final String SQL_UPDATE_USER
            = "UPDATE users SET userName = ?, password = ? WHERE user_id = ?";
    
    private static final String SQL_DELETE_USER
            = "DELETE FROM users WHERE user_id = ?";
    
    private static final String SQL_GET_ALL_USERS
            = "SELECT * FROM users";
    
    private static final String SQL_GET_ALL_PRIVILEDGES_FOR_USER
            = "SELECT * FROM authorities "
            + "WHERE user_id = ?";
    
    private static final String SQL_UPDATE_AUTH 
            = "UPDATE authorities SET username = ?, authority = ? WHERE user_id = ?";

    private static final String SQL_DELETE_AUTH 
            = "DELETE FROM authorities WHERE user_id = ?";
    
    private static final String SQL_ALL_DELETE_AUTH 
            = "DELETE FROM authorities";

    private static final String SQL_GET_USER_BY_ARTICLE_ID
            = "SELECT * "
            + "FROM users u "
            + "JOIN Article a on a.FK_userId = u.user_id "
            + "WHERE a.articleId = ?";
    
    private static final String SQL_DELETE_ARTICLES_FOR_USERS
            = "DELETE from Article where FK_userid = ?";

//==============================================================================
//==============================================================================
//  ___       _   _      _      
// / _ \     | | (_)    | |     
/// /_\ \_ __| |_ _  ___| | ___ 
//|  _  | '__| __| |/ __| |/ _ \
//| | | | |  | |_| | (__| |  __/
//\_| |_/_|   \__|_|\___|_|\___|
//==============================================================================
//==============================================================================
    private static final String SQL_INSERT_ARTICLE
            = "INSERT INTO Article "
            + "(articleDate, content, FK_userId) "
            + "VALUE (?, ?, ?)";
    
    private static final String SQL_GET_ARTICLE
            = "SELECT * FROM Article a "
            + "join users u on u.user_id = a.FK_userId "
            + "WHERE articleId = ?";
    
    private static final String SQL_UPDATE_ARTICLE
            = "UPDATE Article SET articleDate = ?, content = ? WHERE articleId = ?";
    
    private static final String SQL_DELETE_ARTICLE
            = "DELETE FROM Article WHERE articleId = ?";
    
    private static final String SQL_GET_ALL_ARTICLES
            = "SELECT * FROM Article a "
            + "join users u on u.user_id = a.FK_userId ";

//==============================================================================
//==============================================================================
// _   _                ___  ___     _   _               _     
//| | | |               |  \/  |    | | | |             | |    
//| | | |___  ___ _ __  | .  . | ___| |_| |__   ___   __| |___ 
//| | | / __|/ _ \ '__| | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
//| |_| \__ \  __/ |    | |  | |  __/ |_| | | | (_) | (_| \__ \
// \___/|___/\___|_|    \_|  |_/\___|\__|_| |_|\___/ \__,_|___/
//==============================================================================
//==============================================================================
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User addUser(User user) {
        jdbcTemplate.update(SQL_INSERT_USER,
                user.getUserName(),
                user.getPassword(),
                1);

        user.setUserId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        
        List<Authorities> auth = user.getPermission();
        for (Authorities s : auth) {
        jdbcTemplate.update(SQL_INSERT_AUTH,
                user.getUserId(),
                user.getUserName(),
                s.getAuthority());

        }
        return user;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteUser(int id) {
        jdbcTemplate.update(SQL_DELETE_AUTH, id);
        jdbcTemplate.update(SQL_DELETE_ARTICLES_FOR_USERS, id);
        jdbcTemplate.update(SQL_DELETE_USER, id);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateUser(User user) {
        jdbcTemplate.update(SQL_UPDATE_USER,
                user.getUserName(),
                user.getPassword(),
                user.getUserId()
                );
        List<Authorities> auth = user.getPermission();
        //delete old permissions.
        jdbcTemplate.update(SQL_DELETE_AUTH, 
                user.getUserId());
        // then add new ones
        for (Authorities s : auth) {
            //(user_id, username, authority) "
        jdbcTemplate.update(SQL_INSERT_AUTH,
                user.getUserId(),
                user.getUserName(),
                s.getAuthority()
                );
        }
    }
    
    @Override
    public User getUser(int id) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_GET_USER,
                    new UserMapper(),
                    id);
            List<Authorities> auth = jdbcTemplate.query(SQL_GET_ALL_PRIVILEDGES_FOR_USER, 
                    new UserPriviledgeMapper(),
                    user.getUserId());
            user.setPermission(auth);
            return user;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<User> getAllUsers() {
        List<User> users =  jdbcTemplate.query(SQL_GET_ALL_USERS,
                new UserMapper());
        for(User u:users){
            List<Authorities> auth = jdbcTemplate.query(SQL_GET_ALL_PRIVILEDGES_FOR_USER, 
                    new UserPriviledgeMapper(),
                    u.getUserId());
            u.setPermission(auth);
        }
        return users;
    }
    
    @Override
    public User getUserByArticleId(int articleId) {
        
        try {
            return jdbcTemplate.queryForObject(SQL_GET_USER_BY_ARTICLE_ID,
                    new UserMapper(),
                    articleId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        
    }

    @Override
    public void deleteAllAuthorities() {
        jdbcTemplate.update(SQL_ALL_DELETE_AUTH);
    }
    
//==============================================================================
//==============================================================================
//  ___       _   _      _       ___  ___     _   _               _     
// / _ \     | | (_)    | |      |  \/  |    | | | |             | |    
/// /_\ \_ __| |_ _  ___| | ___  | .  . | ___| |_| |__   ___   __| |___ 
//|  _  | '__| __| |/ __| |/ _ \ | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
//| | | | |  | |_| | (__| |  __/ | |  | |  __/ |_| | | | (_) | (_| \__ \
//\_| |_/_|   \__|_|\___|_|\___| \_|  |_/\___|\__|_| |_|\___/ \__,_|___/
//==============================================================================
//==============================================================================
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Article addArticle(Article article) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate articleDate = article.getArticleDate();
        String stringDate = articleDate.format(formatter);
        
        jdbcTemplate.update(SQL_INSERT_ARTICLE,
                stringDate,
                //article.getArticleDate(),
                article.getContent(),
                article.getUserId());
        
        article.setArticleId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        return article;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteArticle(int id) {
        jdbcTemplate.update(SQL_DELETE_ARTICLE, id);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateArticle(Article article) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate articleDate = article.getArticleDate();
        String stringDate = articleDate.format(formatter);
        
        jdbcTemplate.update(SQL_UPDATE_ARTICLE,
                stringDate,
                article.getContent(),
                //                article.getUserId(),
                article.getArticleId());
    }
    
    @Override
    public Article getArticle(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_ARTICLE,
                    new ArticleMapper(),
                    id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<Article> getAllArticles() {
        return jdbcTemplate.query(SQL_GET_ALL_ARTICLES,
                new ArticleMapper());
        
    }

//==============================================================================
//==============================================================================
//___  ___                                
//|  \/  |                                
//| .  . | __ _ _ __  _ __   ___ _ __ ___ 
//| |\/| |/ _` | '_ \| '_ \ / _ \ '__/ __|
//| |  | | (_| | |_) | |_) |  __/ |  \__ \
//\_|  |_/\__,_| .__/| .__/ \___|_|  |___/
//             | |   | |                  
//             |_|   |_|   
//==============================================================================
//==============================================================================
    private static final class UserMapper implements RowMapper<User> {
        
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getInt("enabled"));
            return user;
        }
    }
    
    private static final class ArticleMapper implements RowMapper<Article> {
        
        @Override
        public Article mapRow(ResultSet rs, int i) throws SQLException {
//            User user = new User();
//            user.setUserId(rs.getInt("userId"));
//            user.setUserName(rs.getString("userName"));
//            user.setPermission(rs.getString("permission"));

            Article art = new Article();
            art.setArticleId(rs.getInt("articleId"));
            art.setArticleDate(rs.getTimestamp("articleDate").toLocalDateTime().toLocalDate());
            art.setContent(rs.getString("content"));
            art.setUserId(rs.getInt("fk_userid"));
//            art.setUser(user);

            return art;
        }
    }
    
    private static final class UserArticleMapper implements RowMapper<Article> {
        
        @Override
        public Article mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("username"));

            user.setPassword(rs.getString("password"));


            Article art = new Article();
            art.setArticleId(rs.getInt("articleId"));
            art.setArticleDate(rs.getTimestamp("articleDate").toLocalDateTime().toLocalDate());
            art.setContent(rs.getString("content"));
            art.setUser(user);
            
            return art;
        }
    }
    
    private static final class UserPriviledgeMapper implements RowMapper<Authorities> {
        
        @Override
        public Authorities mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            Authorities auth = new Authorities();
            auth.setAuthority(rs.getString("authority"));
            
            return auth;
        }
    }
}
