/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.model;

import java.util.List;
import lombok.Data;

/**
 *
 * @author austinmann
 */
@Data
public class User {
    
    private int userId;
    private String userName;
    private List<Authorities> permission; 
    private int enabled;
    private String password;

}
