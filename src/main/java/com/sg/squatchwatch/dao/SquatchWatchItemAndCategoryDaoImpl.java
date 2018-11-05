/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.dao;

import com.sg.squatchwatch.model.Category;
import com.sg.squatchwatch.model.Item;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author omish
 */
public class SquatchWatchItemAndCategoryDaoImpl implements SquatchWatchItemAndCategoryDao {

    private JdbcTemplate jdbcTemplate;

    public static final String DELIMITER = ",";

//            private static final String SQL_INSERT_NEW_ARTICLE
//                = "INSERT INTO article "
//                + "() "
//                + "VALUES ()";
    private static final class SqlQueries {
//   _____       __   __       __     __   _____ 
//   \_   \   /\ \ \ / _\     /__\   /__\ /__   \
//    / /\/  /  \/ / \ \     /_\    / \//   / /\/
// /\/ /_   / /\  /  _\ \   //__   / _  \  / /   
// \____/   \_\ \/   \__/   \__/   \/ \_/  \/    
//                                              

        private static final String SQL_INSERT_NEW_CATEGORY
                = "INSERT INTO category "
                + "(categoryName) "
                + "VALUES (?)";

        
        //ADD imageTitle and filename
        private static final String SQL_INSERT_NEW_ITEM
                = "INSERT INTO item "
                + "(itemName, description, itemValue, featuredItem, imageTitle, filename) "
                + "VALUES (?, ?, ?, ?, ?, ?)";


        private static final String SQL_INSERT_NEW_ITEM_CATEGORY
                = "INSERT INTO itemCategory "
                + "(FK_itemId, FK_categoryId) "
                + "VALUES (?, ?)";

//    __     __     _        ___ 
//   /__\   /__\   /_\      /   \
//  / \//  /_\    //_\\    / /\ /
// / _  \ //__   /  _  \  / /_// 
// \/ \_/ \__/   \_/ \_/ /___,'  
//                              
        private static final String SQL_GET_ALL_CATEGORIES
                = "SELECT * FROM category";

        private static final String SQL_GET_ALL_ITEMS
                = "SELECT * FROM item";

        private static final String SQL_GET_CATEGORY_BY_ID
                = "SELECT * FROM category "
                + "WHERE categoryId = ?";

        private static final String SQL_GET_ITEM_BY_ID
                = "SELECT * FROM item "
                + "WHERE itemId = ?";

        private static final String SQL_GET_AN_ITEM_CATEGORIES
                = "SELECT * FROM category "
                + "JOIN itemCategory on itemCategory.FK_categoryId = category.categoryId "
                + "WHERE itemCategory.FK_itemId = ?";

        private static final String SQL_GET_FEATURED_ITEMS
                = "SELECT * FROM item "
                + "WHERE featuredItem = 1";
//            ___      ___     _     _____     __ 
//  /\ /\    / _ \    /   \   /_\   /__   \   /__\
// / / \ \  / /_)/   / /\ /  //_\\    / /\/  /_\  
// \ \_/ / / ___/   / /_//  /  _  \  / /    //__  
//  \___/  \/      /___,'   \_/ \_/  \/     \__/ 
        
        
        //Adding imageTitle and filename
        private static final String SQL_UPDATE_ITEM
                = "UPDATE item SET "
                + "itemName = ?, description = ?, itemValue = ?, featuredItem = ?, imageTitle = ?, filename = ? "
                + "WHERE itemId = ?";

        private static final String SQL_UPDATE_CATEGORY
                = "UPDATE category SET "
                + "categoryName = ? "
                + "WHERE categoryId = ?";

//     ___     __     __     __   _____     __ 
//    /   \   /__\   / /    /__\ /__   \   /__\
//   / /\ /  /_\    / /    /_\     / /\/  /_\  
//  / /_//  //__   / /___ //__    / /    //__  
// /___,'   \__/   \____/ \__/    \/     \__/  
        private static final String SQL_DELETE_ITEM
                = "DELETE FROM item WHERE itemId = ?";

        private static final String SQL_DELETE_CATEGORY
                = "DELETE FROM category WHERE categoryId = ?";

        private static final String SQL_DELETE_CATEGORIES_TO_AN_ITEM
                = "DELETE FROM itemCategory "
                + "WHERE FK_itemId = ?";
        private static final String SQL_DELETE_CATEGORY_FROM_ALL_ITEMS
                = "DELETE FROM itemCategory "
                + "WHERE FK_categoryId = ?";
        private static final String SQL_DELETE_AN_ITEM_CATEGORY
                = "DELETE FROM itemCategory WHERE FK_itemId = ? AND FK_categoryId = ?";

    }
// _______    _______   _________              _______    ______     _______ 
//(       )  (  ____ \  \__   __/  |\     /|  (  ___  )  (  __  \   (  ____ \
//| () () |  | (    \/     ) (     | )   ( |  | (   ) |  | (  \  )  | (    \/
//| || || |  | (__         | |     | (___) |  | |   | |  | |   ) |  | (_____ 
//| |(_)| |  |  __)        | |     |  ___  |  | |   | |  | |   | |  (_____  )
//| |   | |  | (           | |     | (   ) |  | |   | |  | |   ) |        ) |
//| )   ( |  | (____/\     | |     | )   ( |  | (___) |  | (__/  )  /\____) |
//|/     \|  (_______/     )_(     |/     \|  (_______)  (______/   \_______)

    // methods start
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        System.err.println("WE HAVE ARE PUTTING IN A JDBCTEMPLATE");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Item addItem(Item item) {
//        name, description, value
        jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_ITEM,
                item.getItemName(),
                item.getDescription(),
                item.getItemValue(),
                item.getFeaturedItem(),
                item.getImageTitle(),
                item.getFilename()
        );
        // I add the id to the category for if we want to use it after creating it, we have it handily.
        item.setItemId(getLastInsertId());

        //need helper method to insert itemId and categoryId into itemCategory Bridge
        insertItemCategory(item);

        return item;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteItem(int id) {
        //delete connections in bride table to the item
        jdbcTemplate.update(SqlQueries.SQL_DELETE_CATEGORIES_TO_AN_ITEM,
                id);
        //delete item
        jdbcTemplate.update(SqlQueries.SQL_DELETE_ITEM,
                id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateItem(Item item) {
        jdbcTemplate.update(SqlQueries.SQL_UPDATE_ITEM,
                // + "name = ?, description = ?, value = ?, "
                item.getItemName(),
                item.getDescription(),
                item.getItemValue(),
                item.getFeaturedItem(),
                item.getImageTitle(),
                item.getFilename(),
                item.getItemId()
                );

        //delete connections in bride table to the item
        jdbcTemplate.update(SqlQueries.SQL_DELETE_CATEGORIES_TO_AN_ITEM,
                item.getItemId());

        insertItemCategory(item);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Item getItem(int id) {
        // queryForObject = get one (search by ID yeilds one)
        Item item = jdbcTemplate.queryForObject(SqlQueries.SQL_GET_ITEM_BY_ID,
                new ItemMapper(),
                id);
        
        
        List<Category> categories = jdbcTemplate.query(SqlQueries.SQL_GET_AN_ITEM_CATEGORIES,
                new CategoryMapper(), 
                id);  
        
        item.setCategories(categories);
        
        return item;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Item> getfeaturedItems() {
        // queryForObject = get one (search by ID yeilds one)
        List<Item> featuredItems = jdbcTemplate.query(SqlQueries.SQL_GET_FEATURED_ITEMS,
                new ItemMapper());

        return featuredItems;
    }

    @Override
    public List<Item> getAllItems() {
        // query = get list
        List<Item> items = jdbcTemplate.query(SqlQueries.SQL_GET_ALL_ITEMS,
                new ItemMapper());
        return items;
    }

    @Override
    public List<Item> searchItems(Map<SearchTerm, String> criteria) {

        StringBuilder sQuery
                = new StringBuilder("select Item.* from Item "
                        + "left join ItemCategory on ItemCategory.FK_itemId =  Item.itemId "
                        + "left join Category on ItemCategory.FK_categoryId = Category.categoryId "
                        + "where ItemValue ");

        int numParams = criteria.size();
        int paramPosition = 0;

        String[] paramVals = new String[numParams];
        Set<SearchTerm> keySet = criteria.keySet();
        Iterator<SearchTerm> iter = keySet.iterator();
        String[] currentTokens;
                String term = criteria.get(SearchTerm.ITEMVALUE);
                currentTokens = term.split(DELIMITER);

                sQuery.append(" between ").append(currentTokens[0]);
                sQuery.append(" and ").append(currentTokens[1]);
                //sQuery.append(" and ");

        while (iter.hasNext()) {
            SearchTerm currentKey = iter.next();
            String currentTerm = criteria.get(currentKey);

            if (currentKey.equals(SearchTerm.ITEMVALUE)) {
                sQuery.append("");
                paramPosition++;
            }
            else {
                sQuery.append(" and ");

                sQuery.append(currentKey);
                sQuery.append(" LIKE ");
                sQuery.append("'%");
                //sQuery.append("'").append(currentTerm).append("'");
                sQuery.append(currentTerm);
                sQuery.append("%'");
                paramVals[paramPosition] = criteria.get(currentKey);
            }
        }

        sQuery.append(" group by Item.itemId");        return jdbcTemplate.query(sQuery.toString(),
                new ItemMapper());
                //paramVals);

    }

// _______    _______   _________   _______    _______    _______    _______   _________   _______    _______ 
//(  ____ \  (  ___  )  \__   __/  (  ____ \  (  ____ \  (  ___  )  (  ____ )  \__   __/  (  ____ \  (  ____ \
//| (    \/  | (   ) |     ) (     | (    \/  | (    \/  | (   ) |  | (    )|     ) (     | (    \/  | (    \/
//| |        | (___) |     | |     | (__      | |        | |   | |  | (____)|     | |     | (__      | (_____ 
//| |        |  ___  |     | |     |  __)     | | ____   | |   | |  |     __)     | |     |  __)     (_____  )
//| |        | (   ) |     | |     | (        | | \_  )  | |   | |  | (\ (        | |     | (              ) |
//| (____/\  | )   ( |     | |     | (____/\  | (___) |  | (___) |  | ) \ \__  ___) (___  | (____/\  /\____) |
//(_______/  |/     \|     )_(     (_______/  (_______)  (_______)  |/   \__/  \_______/  (_______/  \_______)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Category addCategory(Category category) {
        jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_CATEGORY,
                category.getCategoryName()
        );
        // I add the id to the category for if we want to use it after creating it, we have it handily.
        category.setCategoryId(getLastInsertId());
        return category;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCategory(int id) {
        // pass in a category ID, then delete from bridge table anything that uses it.
        jdbcTemplate.update(SqlQueries.SQL_DELETE_CATEGORY_FROM_ALL_ITEMS,
                id);
        // not delete the non dependent-ed category
        jdbcTemplate.update(SqlQueries.SQL_DELETE_CATEGORY,
                id);
    }

    @Override
    public void updateCategory(Category category) {
        // pass in what I want the category to be (should set its ID to desired one for change)
        jdbcTemplate.update(SqlQueries.SQL_UPDATE_CATEGORY,
                category.getCategoryName(),
                category.getCategoryId());
    }

    @Override
    public Category getCategory(int id) {
        Category category = jdbcTemplate.queryForObject(SqlQueries.SQL_GET_CATEGORY_BY_ID,
                new CategoryMapper(),
                id);
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = jdbcTemplate.query(SqlQueries.SQL_GET_ALL_CATEGORIES,
                new CategoryMapper());
        return categories;
    }

    @Override
    public List<Category> getCategoriesByItemId(int itemId) {

        List<Category> categoryList = jdbcTemplate.
                query(SqlQueries.SQL_GET_AN_ITEM_CATEGORIES,
                        new CategoryMapper(),
                        itemId);

        return categoryList;
    }

//            _______    _          _______    _______    _______    _______ 
//|\     /|  (  ____ \  ( \        (  ____ )  (  ____ \  (  ____ )  (  ____ \
//| )   ( |  | (    \/  | (        | (    )|  | (    \/  | (    )|  | (    \/
//| (___) |  | (__      | |        | (____)|  | (__      | (____)|  | (_____ 
//|  ___  |  |  __)     | |        |  _____)  |  __)     |     __)  (_____  )
//| (   ) |  | (        | |        | (        | (        | (\ (           ) |
//| )   ( |  | (____/\  | (____/\  | )        | (____/\  | ) \ \__  /\____) |
//|/     \|  (_______/  (_______/  |/         (_______/  |/   \__/  \_______)
    private int getLastInsertId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    private void insertItemCategory(Item item) {
        final int itemId = item.getItemId();
        final List<Category> categories = item.getCategories();

        if (categories == null) {
            //do nothing 
        } else {
            //updating bridge table
            for (Category currentCategory : categories) {
                jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_ITEM_CATEGORY,
                        itemId,
                        currentCategory.getCategoryId());
            }
        }
    }

// _______    _______    _______    _______    _______    _______    _______ 
//(       )  (  ___  )  (  ____ )  (  ____ )  (  ____ \  (  ____ )  (  ____ \
//| () () |  | (   ) |  | (    )|  | (    )|  | (    \/  | (    )|  | (    \/
//| || || |  | (___) |  | (____)|  | (____)|  | (__      | (____)|  | (_____ 
//| |(_)| |  |  ___  |  |  _____)  |  _____)  |  __)     |     __)  (_____  )
//| |   | |  | (   ) |  | (        | (        | (        | (\ (           ) |
//| )   ( |  | )   ( |  | )        | )        | (____/\  | ) \ \__  /\____) |
//|/     \|  |/     \|  |/         |/         (_______/  |/   \__/  \_______)
    private static final class CategoryMapper implements RowMapper<Category> {

        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category cat = new Category();

            // this gets the base information from the dataTable, still need location info.
            cat.setCategoryName(rs.getString("categoryName"));
            cat.setCategoryId(rs.getInt("categoryId"));

            return cat;
        }
    }

    private static final class ItemMapper implements RowMapper<Item> {

        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            
            Item item = new Item();

            // this gets the base information from the dataTable, still need location info.
            item.setItemName(rs.getString("itemName"));
            item.setDescription(rs.getString("description"));
            int itemInt = rs.getInt("itemValue");
            BigDecimal itemDec = new BigDecimal(itemInt);
            item.setItemValue(itemDec);
            item.setFeaturedItem(rs.getBoolean("featuredItem"));
            item.setImageTitle(rs.getString("imageTitle"));
            item.setFilename(rs.getString("filename"));
            
            item.setItemId(rs.getInt("itemId"));

            return item;
        }
    }

}

//  _______    _______    _______    _______   _________   _______ 
//(  ____ \  (  ____ )  (  ____ \  (  ___  )  \__   __/  (  ____ \
//| (    \/  | (    )|  | (    \/  | (   ) |     ) (     | (    \/
//| |        | (____)|  | (__      | (___) |     | |     | (__    
//| |        |     __)  |  __)     |  ___  |     | |     |  __)   
//| |        | (\ (     | (        | (   ) |     | |     | (      
//| (____/\  | ) \ \__  | (____/\  | )   ( |     | |     | (____/\
//(_______/  |/   \__/  (_______/  |/     \|     )_(     (_______/
// _______    _______    _______    ______  
//(  ____ )  (  ____ \  (  ___  )  (  __  \ 
//| (    )|  | (    \/  | (   ) |  | (  \  )
//| (____)|  | (__      | (___) |  | |   ) |
//|     __)  |  __)     |  ___  |  | |   | |
//| (\ (     | (        | (   ) |  | |   ) |
//| ) \ \__  | (____/\  | )   ( |  | (__/  )
//|/   \__/  (_______/  |/     \|  (______/  
//    
//            _______    ______     _______   _________   _______ 
//|\     /|  (  ____ )  (  __  \   (  ___  )  \__   __/  (  ____ \
//| )   ( |  | (    )|  | (  \  )  | (   ) |     ) (     | (    \/
//| |   | |  | (____)|  | |   ) |  | (___) |     | |     | (__    
//| |   | |  |  _____)  | |   | |  |  ___  |     | |     |  __)   
//| |   | |  | (        | |   ) |  | (   ) |     | |     | (      
//| (___) |  | )        | (__/  )  | )   ( |     | |     | (____/\
//(_______)  |/         (______/   |/     \|     )_(     (_______/
// ______     _______    _          _______   _________   _______ 
//(  __  \   (  ____ \  ( \        (  ____ \  \__   __/  (  ____ \
//| (  \  )  | (    \/  | (        | (    \/     ) (     | (    \/
//| |   ) |  | (__      | |        | (__         | |     | (__    
//| |   | |  |  __)     | |        |  __)        | |     |  __)   
//| |   ) |  | (        | |        | (           | |     | (      
//| (__/  )  | (____/\  | (____/\  | (____/\     | |     | (____/\
//(______/   (_______/  (_______/  (_______/     )_(     (_______/
