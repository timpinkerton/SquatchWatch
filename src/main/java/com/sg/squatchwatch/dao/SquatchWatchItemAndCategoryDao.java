/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.dao;

import com.sg.squatchwatch.model.Category;
import com.sg.squatchwatch.model.Item;
import com.sg.squatchwatch.model.ItemImage;
import java.util.List;
import java.util.Map;

/**
 *
 * @author austinmann
 */
public interface SquatchWatchItemAndCategoryDao {
    
    
    //ITEM
    public Item addItem(Item item);
    
    public void deleteItem(int id);
    
    public void updateItem(Item item);
    
    public Item getItem(int id);
    
    public List<Item> getAllItems();
    
    public List<Item> searchItems(Map<SearchTerm, String> criteria);
    
    public List<Item> getfeaturedItems();
    
    
    //CATEGORY
    public Category addCategory(Category category);
    
    public void deleteCategory(int id);
    
    public void updateCategory(Category category);
    
    public Category getCategory(int id);
    
    public List<Category> getAllCategories();
    
    public List<Category> getCategoriesByItemId(int itemId); 
    
    
}
