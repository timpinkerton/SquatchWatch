/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.dao;

import com.sg.squatchwatch.model.Category;
import com.sg.squatchwatch.model.Item;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.math.BigDecimal;
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
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author omish
 */
public class SquatchWatchItemAndCategoryDaoImplTest {
    //TODO item needs to be float, decimal, ect, not long;
    // maybe add an id to update? pas sin the item and updateID?
    // fix exceptions, doing general ones because I dont know what it throws.

    SquatchWatchItemAndCategoryDao dao;
//    = new SquatchWatchItemAndCategoryDaoImpl()

    public SquatchWatchItemAndCategoryDaoImplTest() {

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
        dao = (SquatchWatchItemAndCategoryDao)ctx.getBean("Dao");

        cleanUp();
    }

    @After
    public void tearDown() {
        cleanUp();
    }

    public void cleanUp() {

        List<Item> item = dao.getAllItems();
        item.forEach((currentItem) -> {
            dao.deleteItem(currentItem.getItemId());
        });

        List<Category> category = dao.getAllCategories();
        category.forEach((currentCategory) -> {
            dao.deleteCategory(currentCategory.getCategoryId());
        });
    }

    @Test
    public void testSetJdbcTemplate() {
    }


    public Item makeItem() {
        
        
        List<Category> categories = new ArrayList<>();  
        
        Item item = new Item();
        item.setItemName("Camera X");
        item.setDescription("It captures high quality pictures even during night");
        item.setItemValue(new BigDecimal("1100"));
        item.setCategories(categories);
        item.setFeaturedItem(FALSE);
        item.setImageTitle("Photo of the Camera");
        item.setFilename("/foldername/somewhere");
        

        return item;
    }
    
    public Category makeCategory() {
        Category cat = new Category();
        cat.setCategoryName("Trap");

        return cat;
    }

    @Test
    public void testAddItem() {
        Item item = makeItem();
        item = dao.addItem(item);

        assertEquals(item, dao.getItem(item.getItemId()));
    }

    @Test (expected = EmptyResultDataAccessException.class)
    public void testDeleteItem() {
        // previous test return an item.  Use that to save code :D damn its genious;
        Item item = makeItem();
        item = dao.addItem(item);
        assertEquals(item, dao.getItem(item.getItemId()));


        dao.deleteItem(item.getItemId());
        assertNull(dao.getItem(item.getItemId()));
    }

    @Test
    public void testUpdateItem() {
        
        List<Category> categories = new ArrayList<>(); 
        
        Item item = makeItem();
        dao.addItem(item);

        Item item2 = new Item();
        item2.setItemName("Camera Y");
        item2.setDescription("Good camera but it's broke");
        item2.setItemValue(new BigDecimal("1200"));
        item2.setCategories(categories);
        item2.setFeaturedItem(TRUE);
        item2.setImageTitle("picture of the camera");
        item2.setFilename("folder/location/here");
        
        item2.setItemId(item.getItemId());

        dao.updateItem(item2);
        assertEquals(item2, dao.getItem(item.getItemId()));
    }

    @Test
    public void testGetItem() {
        Item item = makeItem();
        item = dao.addItem(item);
        
        dao.getItem(item.getItemId());
        assertEquals(item, dao.getItem(item.getItemId()));
    }

    @Test
    public void testGetAllItems() {
        // get 1 item
        
        Item item = makeItem();
        dao.addItem(item);

        // make a second to test pulling 2;
        Item item2 = new Item();
        item2.setItemName("Camera X");
        item2.setDescription("Good camera but it's broke");
        item2.setItemValue(new BigDecimal(1100));
        item2.setFeaturedItem(FALSE);
        item2.setImageTitle("picture of the camera");
        item2.setFilename("folder/location/here");
        dao.addItem(item2);

        //can pull multiple;
        assertEquals(2, dao.getAllItems().size());
    }

    @Test
    public void testAddCategory() {
        Category cat = makeCategory();

        cat = dao.addCategory(cat);
        
        assertEquals(cat, dao.getCategory(cat.getCategoryId()));
    }

    @Test (expected = EmptyResultDataAccessException.class)
    public void testDeleteCategory() {
        Category cat = makeCategory();
        cat = dao.addCategory(cat);
        assertEquals(cat, dao.getCategory(cat.getCategoryId()));

        dao.deleteCategory(cat.getCategoryId());
        assertNull(dao.getCategory(cat.getCategoryId()));
    }

    @Test
    public void testUpdateCategory() {
        Category cat = makeCategory();

        Category cat2 = new Category();
        cat2.setCategoryName("Camera");
        cat2.setCategoryId(cat.getCategoryId());
        dao.updateCategory(cat);
    }

    @Test
    public void testGetCategory() {
        Category cat = makeCategory();
        dao.addCategory(cat);

        assertEquals(cat, dao.getCategory(cat.getCategoryId()));
    }

    @Test
    public void testGetAllCategories() {
        //Category cat = makeCategory();
        
        Category cat = new Category();
        cat.setCategoryName("Trap");
        dao.addCategory(cat);
        
        Category cat2 = new Category();
        cat2.setCategoryName("Hair");
        dao.addCategory(cat2);

        assertEquals(2, dao.getAllCategories().size());
    }


}
