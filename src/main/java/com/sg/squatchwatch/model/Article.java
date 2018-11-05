/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.model;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author austinmann
 */
@Data
public class Article {
    
    private int articleId;
    private LocalDate articleDate;
    private String content;
    
    private int userId; 
    
    private User user;
}
