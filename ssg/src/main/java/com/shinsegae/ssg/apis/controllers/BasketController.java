package com.shinsegae.ssg.apis.controllers;

import com.shinsegae.ssg.apis.containers.BasketGetResultContainer;
import com.shinsegae.ssg.apis.enums.basket.AddResult;
import com.shinsegae.ssg.apis.enums.basket.GetResult;
import com.shinsegae.ssg.apis.services.BasketService;
import com.shinsegae.ssg.apis.vos.basket.AddVo;
import com.shinsegae.ssg.apis.vos.basket.BasketVo;
import com.shinsegae.ssg.apis.vos.user.UserVo;
import com.shinsegae.ssg.controllers.StandardRestController;
import com.shinsegae.ssg.utilities.Constant;
import com.shinsegae.ssg.utilities.Converter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping(
        value = "/apis/basket/",
        method = {RequestMethod.GET, RequestMethod.POST},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BasketController extends StandardRestController {
    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @RequestMapping(value = "add")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(name = "item_id", defaultValue = "") String itemIndex,
                      @RequestParam(name = "color_id", defaultValue = "") String colorIndex,
                      @RequestParam(name = "size_id", defaultValue = "") String sizeIndex,
                      @RequestParam(name = "count", defaultValue = "") String count) throws
            SQLException {
        UserVo userVo = Converter.getUserVo(request.getSession());
        AddVo addVo = new AddVo(itemIndex, colorIndex, sizeIndex, count);
        AddResult addResult = this.basketService.add(userVo, addVo);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(Constant.Common.JSON_ENTRY_RESULT, addResult.name().toLowerCase());
        return jsonResponse.toString(4);
    }

    @RequestMapping(value = "get")
    public String get(HttpServletRequest request, HttpServletResponse response) throws
            SQLException {
        UserVo userVo = Converter.getUserVo(request.getSession());
        BasketGetResultContainer basketGetResultContainer = this.basketService.get(userVo);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(Constant.Common.JSON_ENTRY_RESULT, basketGetResultContainer.getGetResult().name().toLowerCase());
        if (basketGetResultContainer.getGetResult() == GetResult.SUCCESS) {
            JSONArray jsonBaskets = new JSONArray();
            for(BasketVo basket : basketGetResultContainer.getBaskets()) {
                JSONObject jsonBasket = new JSONObject();
                jsonBasket.put("itemName", basket.getName());
                jsonBasket.put("itemPrice", basket.getPrice());
                jsonBasket.put("colorName", basket.getColorName());
                jsonBasket.put("colorVariation", basket.getColorVariation());
                jsonBasket.put("sizeName", basket.getSizeName());
                jsonBasket.put("sizeVariation", basket.getSizeVariation());
                jsonBasket.put("dateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(basket.getDateTime()));
                jsonBasket.put("count", basket.getCount());
                jsonBaskets.put(jsonBasket);
            }
            jsonResponse.put("baskets", jsonBaskets);
        }
        return jsonResponse.toString(4);
    }
}