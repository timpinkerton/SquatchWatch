/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.controller;

import com.sg.squatchwatch.dao.SquatchWatchUserAndArticleDao;
import com.sg.squatchwatch.model.Authorities;
import com.sg.squatchwatch.model.Item;
import com.sg.squatchwatch.model.User;
import java.util.ArrayList;
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
public class UserController {

    SquatchWatchUserAndArticleDao dao;
    String error;

    @Inject
    public UserController(SquatchWatchUserAndArticleDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public String displayLoginPage(HttpServletRequest request) {
        return "User/login";
    }

    @RequestMapping(value = "/User", method = RequestMethod.GET)
    public String displayUserPage(HttpServletRequest request, Model model) {

        List<User> userList = dao.getAllUsers();

        model.addAttribute("userList", userList);
        model.addAttribute("error", error);

        return "User/user";
    }

    @RequestMapping(value = "/displayGetAllUsers", method = RequestMethod.GET)
    public String displayUsersPage(Model model) {
        // Get all the items from the DAO
        List<User> userList = dao.getAllUsers();
        model.addAttribute("userList", userList);
        return "Item/item";
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String displayUserDetail(HttpServletRequest request) {

        User updatedUser = new User();

        try {
            String userIdParameter = request.getParameter("userId");
            int userId = Integer.parseInt(userIdParameter);

            updatedUser.setUserId(userId);

            User oldUserInfo = dao.getUser(userId);

            String updatedUserName = request.getParameter("userName");

            if (updatedUserName.equalsIgnoreCase("")) {
                updatedUser.setUserName(oldUserInfo.getUserName());
            } else {
                updatedUser.setUserName(updatedUserName);
            }
            
            String updatedPassword = request.getParameter("password");
            
            if (updatedPassword.equalsIgnoreCase("")) {
                updatedUser.setUserName(oldUserInfo.getPassword());
            } else {
                updatedUser.setUserName(updatedPassword);
            }

            String updatedPermission = request.getParameter("permission");
            List<Authorities> permissions = new ArrayList<>();

            if (updatedPermission == null) {
                updatedUser.setPermission(oldUserInfo.getPermission());
            } else {
                //if permission is not user, add it solo
                if (!updatedPermission.equalsIgnoreCase("ROLE_ADMIN")) {
                    Authorities a = new Authorities();
                    a.setAuthority(updatedPermission);
                    permissions.add(a);
                } else {
                    //if user is admin, add all.
                    Authorities a = new Authorities();
                    Authorities b = new Authorities();
                    Authorities c = new Authorities();
                    a.setAuthority("ROLE_ADMIN");
                    b.setAuthority("ROLE_COLLECTORS");
                    c.setAuthority("ROLE_STAFF");
                    permissions.add(a);
                    permissions.add(b);
                    permissions.add(c);
                }

                updatedUser.setPermission(permissions);
            }

            dao.updateUser(updatedUser);
        } catch (NumberFormatException ex) {
            error = "The user you wanted to edit does not exist.";
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "There was a problem updating the user, changes will not be saved.";
        }

        return "redirect:User";
    }

    @RequestMapping(value = "/displayEditUserForm", method = RequestMethod.GET)
    public String displayEditUserForm(HttpServletRequest request, Model model) {

        String userIdParameter = request.getParameter("userId");
        try {
            int userId = Integer.parseInt(userIdParameter);
            User user = dao.getUser(userId);
            model.addAttribute("user", user);
        } catch (NumberFormatException ex) {
            error = "The uder you wanted to edit does not exist.";
            model.addAttribute("error", error);
            error = null;
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "There was a problem updating the user, changes will not be saved.";
            model.addAttribute("error", error);
            error = null;
        }

        return "User/editUserForm";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(HttpServletRequest request) {
        try {
            String userIdParameter = request.getParameter("userId");
            int userId = Integer.parseInt(userIdParameter);
            dao.deleteUser(userId);
        } catch (NumberFormatException ex) {
            error = "The user you wanted to delete does not exist.";
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "There was a problem deleting the user, deletion will not be saved.";
        }
        return "redirect:User";
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
//    @RequestMapping(value = "/displayAddItemForm", method = RequestMethod.GET)
//    public String displayAddItemForm(HttpServletRequest request) {
//
//        return "Item/addItemForm";
//    }
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request) {

        User user = new User();

        user.setUserName(request.getParameter("userName"));

        user.setPassword(request.getParameter("password"));

        String updatedPermission = request.getParameter("permission");
        List<Authorities> permissions = new ArrayList<>();
        //if permission is not user, add it solo
        if (!updatedPermission.equalsIgnoreCase("ROLE_ADMIN")) {
                    Authorities a = new Authorities();
                    a.setAuthority(updatedPermission);
                    permissions.add(a);
                } else {
                    //if user is admin, add all.
                    Authorities a = new Authorities();
                    Authorities b = new Authorities();
                    Authorities c = new Authorities();
                    a.setAuthority("ROLE_ADMIN");
                    b.setAuthority("ROLE_COLLECTORS");
                    c.setAuthority("ROLE_STAFF");
                    permissions.add(a);
                    permissions.add(b);
                    permissions.add(c);
                }

        user.setPermission(permissions);

        try {
            dao.addUser(user);
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Error creating a user, user will not be saved.";
        }

        return "redirect:User";
    }

}
