/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.squatchwatch.controller;

import com.sg.squatchwatch.dao.SearchTerm;
import com.sg.squatchwatch.dao.SquatchWatchItemAndCategoryDao;
import com.sg.squatchwatch.model.Category;
import com.sg.squatchwatch.model.Item;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author christophercunningham
 */
@Controller
//@RequestMapping({"/Item"})
public class itemController {

    //setting the folder where the images will be saved
    public static final String pictureFolder = "swImages/";

    SquatchWatchItemAndCategoryDao dao;
    String error;

    @Inject
    public itemController(SquatchWatchItemAndCategoryDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/Item", method = RequestMethod.GET)
    public String item(Model model) {
        List<Item> itemList = dao.getAllItems();
        model.addAttribute("itemList", itemList);
        List<Category> categoryList = dao.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("error", error);
        error = null;

        return "Item/item";
    }

    //unused endpoint
    @RequestMapping(value = "/displayGetAllItems", method = RequestMethod.GET)
    public String displayItemPage(Model model) {
        // Get all the items from the DAO
        List<Item> itemList = dao.getAllItems();
        model.addAttribute("itemList", itemList);
        model.addAttribute("error", error);
        error = null;
        return "Item/item";
    }

    @RequestMapping(value = "/displayItemDetail", method = RequestMethod.GET)
    public String displayItemDetail(HttpServletRequest request, Model model) {

        String itemIdParameter = request.getParameter("itemId");

        try {
            int itemId = Integer.parseInt(itemIdParameter);

            Item item = dao.getItem(itemId);

            List<Category> categories = dao.getCategoriesByItemId(itemId);

            model.addAttribute("item", item);
            model.addAttribute("categories", categories);
        } catch (NumberFormatException ex) {
            error = "Trouble finding the item to display";
            model.addAttribute("error", error);
            error = null;
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Error displaying the item details";
            model.addAttribute("error", error);
            error = null;
        }

        return "Item/itemDetail";
    }

//    @RequestMapping(value = "/displayEditItemForm", method = RequestMethod.GET)
//    public String displayEditItemForm(HttpServletRequest request) {
//
//        return "Item/editItemForm";
//    }
    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public String deleteItem(HttpServletRequest request) {
        String itemIdParameter = request.getParameter("itemId");
        try {
            int itemId = Integer.parseInt(itemIdParameter);
            dao.deleteItem(itemId);
        } catch (NumberFormatException ex) {
            error = "Trouble finding the item to delete";
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Error deleteing the item, delete will not be saved";
        }
        return "redirect:Item";
    }

    @RequestMapping(value = "/displayEditItemForm", method = RequestMethod.GET)
    public String displayEditItemForm(HttpServletRequest request, Model model) {
        String itemIdParameter = request.getParameter("itemId");
        try {
            int itemId = Integer.parseInt(itemIdParameter);
            Item item = dao.getItem(itemId);

            List<Category> categoryList = dao.getAllCategories();
            List<Category> categories = dao.getCategoriesByItemId(itemId);

            model.addAttribute("categories", categories);

            model.addAttribute("categoryList", categoryList);

            model.addAttribute("item", item);
        } catch (NumberFormatException ex) {
            error = "Trouble finding the item to edit";
            model.addAttribute("error", error);
            error = null;
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Error displaying the item details";
            model.addAttribute("error", error);
            error = null;
        }

        return "Item/editItemForm";
    }

    @RequestMapping(value = "/displayAddItemForm", method = RequestMethod.GET)
    public String displayAddItemForm(Model model) {

        List<Category> categoryList = dao.getAllCategories();

        model.addAttribute("categoryList", categoryList);

        return "Item/addItemForm";
    }

    @RequestMapping(value = "/editItem", method = RequestMethod.POST)
    public String editItem(HttpServletRequest request) {

        Item updatedItem = new Item();
        //ID's are set auto incremented, so negative numbers should be impossible.
        // this will throw an error when redirected and print the error message;
        int itemId = -1;

        try {
            String itemIdParameter = request.getParameter("itemId");
            itemId = Integer.parseInt(itemIdParameter);

            updatedItem.setItemId(itemId);

            Item oldItemInfo = dao.getItem(itemId);

            String updatedItemName = request.getParameter("itemName");

            if (updatedItemName.equalsIgnoreCase("")) {
                updatedItem.setItemName(oldItemInfo.getItemName());
            } else {
                updatedItem.setItemName(updatedItemName);
            }

            String updatedDescription = request.getParameter("description");

            if (updatedItemName.equalsIgnoreCase("")) {
                updatedItem.setDescription(oldItemInfo.getDescription());
            } else {
                updatedItem.setDescription(updatedDescription);
            }

            String updatedValue = request.getParameter("value");
            BigDecimal itemValue = new BigDecimal(updatedValue);

            if (updatedItemName.equalsIgnoreCase("")) {
                updatedItem.setItemValue(oldItemInfo.getItemValue());
            } else {
                updatedItem.setItemValue(itemValue);
            }

            String updatedFeaturedItem = request.getParameter("featured");
            updatedItem.setFeaturedItem(Boolean.parseBoolean(updatedFeaturedItem));

//            if (updatedFeaturedItem.equalsIgnoreCase("")) {
//                updatedItem.setFeaturedItem(oldItemInfo.getFeaturedItem());
//            } else {
//                updatedItem.setFeaturedItem(Boolean.parseBoolean(updatedFeaturedItem));
//            }

            String[] categoryIdList = request.getParameterValues("categories");

            if (categoryIdList == null) {
                updatedItem.setCategories(oldItemInfo.getCategories());
            } else {
                List<Category> updatedCategoryList = new ArrayList<>();

                for (String currentCategoryId : categoryIdList) {
                    int categoryId = Integer.parseInt(currentCategoryId);
                    Category category = dao.getCategory(categoryId);
                    updatedCategoryList.add(category);
                }
                updatedItem.setCategories(updatedCategoryList);
            }
            
            updatedItem.setFilename(oldItemInfo.getFilename());
            updatedItem.setImageTitle(oldItemInfo.getImageTitle());
            

            dao.updateItem(updatedItem);
        } catch (NumberFormatException ex) {
            error = "Trouble finding the item to edit";
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Error displaying the item details";
        }

        return "redirect:displayItemDetail?itemId=" + itemId;
    }

    //UPDATED /createItem endpoint to read in fileName and path
    @RequestMapping(value = "/createItem", method = RequestMethod.POST)
    public String createItem(HttpServletRequest request,
            Model model,
            @RequestParam(value = "itemImage", required = false) MultipartFile imageFile) {

        Item item = new Item();
        try {
            item.setItemName(request.getParameter("nameInput"));
            item.setDescription(request.getParameter("descriptionInput"));
            item.setItemValue(new BigDecimal(request.getParameter("valueInput")));
            item.setFeaturedItem(Boolean.parseBoolean(request.getParameter("featured")));

            String[] listOfCategories = request.getParameterValues("categoryInput");

            if (listOfCategories == null) {
                //do nothing
            } else {
                //Creating a new category to hold the categories selected
                List<Category> categoryList = new ArrayList<>();

                for (String currentId : listOfCategories) {
                    int categoryId = Integer.parseInt(currentId);
                    Category category = dao.getCategory(categoryId);

                    categoryList.add(category);
                }

                item.setCategories(categoryList);
            }

            //getting the image Title from the input form
            String imageTitle = request.getParameter("imageTitle");

            // checking to see if an image was selected
            if (!imageFile.isEmpty()) {
                try {
                    // saving images to the pictureFolder declared at the top of the page
                    String savePath = request
                            .getSession()
                            .getServletContext()
                            .getRealPath("/") + pictureFolder;
                    File dir = new File(savePath);
                    // this will create the directory if it doesn't exist
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    // setting the filename
                    String filename = imageFile.getOriginalFilename();
                    // transfer the contents of the uploaded imageFile to 
                    // the server
                    imageFile.transferTo(new File(savePath + filename));

                    //pictureFolder +
                    item.setFilename(filename);
                    item.setImageTitle(imageTitle);

//                dao.addItem(item);
                    // redirect to the Item page
                    
                } catch (Exception e) {
                    // if exception, add the error message 
                    // to the model and return to AddItemForm
                    model.addAttribute("errorMsg", "File upload failed: "
                            + e.getMessage());
                    return "redirect:displayAddItemForm";
                }
            } else {
                // if the user didn't upload anything, add the error 
                // message to the model and return back to AddItemForm
//                model.addAttribute("errorMsg",
//                        "Please specify a non-empty file.");
//                return "displayAddItemForm";
            }

            dao.addItem(item);
            
            // in case of failure do not add item.
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Error creating an item, item will not be saved.";
        }
        return "redirect:Item";
    }

//    @RequestMapping(value = "/searchItems", method = RequestMethod.POST)
//    @ResponseBody
//    public List<Item> searchContacts(@RequestBody Map<String, String> searchMap) {
//        // Create the map of search criteria to send to the DAO
//        return "redirect:Item";
//    }
    @RequestMapping(value = "/searchItem", method = RequestMethod.POST)
    public String searchItems(HttpServletRequest request, Model model) {
        List<Category> categoryList = dao.getAllCategories();
        model.addAttribute("categoryList", categoryList);

        Map<SearchTerm, String> criteriaMap = new HashMap<>();

        try {
            String nameTerm = (request.getParameter("name"));
            if (nameTerm != null && !nameTerm.isEmpty()) {
                criteriaMap.put(SearchTerm.ITEMNAME, nameTerm);
            }
            String descTerm = (request.getParameter("description"));
            if (descTerm != null && !descTerm.isEmpty()) {
                criteriaMap.put(SearchTerm.DESCRIPTION, descTerm);
            }
            String valueTerm = (request.getParameter("value"));
            if (valueTerm != null && !valueTerm.isEmpty()) {
                criteriaMap.put(SearchTerm.ITEMVALUE, valueTerm);
            }
            String catTerm = (request.getParameter("category"));
            if (catTerm != null && !catTerm.isEmpty()) {
                criteriaMap.put(SearchTerm.CATEGORYNAME, catTerm);
            }
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            error = "Failed to perform the search, no criteria filtered.";
        }
        // need a return, leaving this for you AUstin to do what you will.
        List<Item> searchList = dao.searchItems(criteriaMap);
        model.addAttribute("itemList", searchList);
        return "Item/item";

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getfeaturedItems(Model model) {
        List<Item> featuredItems = dao.getfeaturedItems();
        model.addAttribute("featuredItems", featuredItems);
        return "index";

    }

    @RequestMapping(value = "/featured", method = RequestMethod.GET)
    public String getmyfeaturedItems(Model model) {
        List<Item> featuredItems = dao.getfeaturedItems();
        model.addAttribute("featuredItems", featuredItems);
        return "index";

    }

    @RequestMapping(value = "/displaySearchItems", method = RequestMethod.GET)
    public String displaySearch(Model model) {
        // Get all the items from the DAO
        List<Item> itemList = dao.getAllItems();
        model.addAttribute("itemList", itemList);
        return "Item/item";
    }

}
