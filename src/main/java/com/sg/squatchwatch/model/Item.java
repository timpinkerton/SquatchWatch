/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 *
 * @author austinmann
 */
@Data
public class Item {
    
    private int itemId;
    private String itemName;
    private String description;
    private BigDecimal itemValue;
    private Boolean featuredItem;
    private List<Category> categories; 
    
//    private int imageId;
    private String imageTitle;
    private String filename;
    
}
