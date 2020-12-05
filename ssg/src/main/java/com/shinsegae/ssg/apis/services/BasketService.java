package com.shinsegae.ssg.apis.services;

import com.shinsegae.ssg.apis.containers.BasketGetResultContainer;
import com.shinsegae.ssg.apis.daos.BasketDao;
import com.shinsegae.ssg.apis.enums.basket.AddResult;
import com.shinsegae.ssg.apis.enums.basket.GetResult;
import com.shinsegae.ssg.apis.vos.basket.AddVo;
import com.shinsegae.ssg.apis.vos.basket.BasketVo;
import com.shinsegae.ssg.apis.vos.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class BasketService {
    private final DataSource dataSource;
    private final BasketDao basketDao;

    @Autowired
    public BasketService(DataSource dataSource, BasketDao basketDao) {
        this.dataSource = dataSource;
        this.basketDao = basketDao;
    }

    public AddResult add(UserVo userVo, AddVo addVo) throws
            SQLException {
        if (addVo.getItemIndex() < 0 || addVo.getColorIndex() < 0 || addVo.getSizeIndex() < 0 || addVo.getCount() < 0) {
            return AddResult.INVALID_INPUT;
        }
        if (userVo == null) {
            return AddResult.NOT_ALLOWED;
        }
        try (Connection connection = this.dataSource.getConnection()) {
            int count = this.basketDao.selectBasket(connection, userVo, addVo);
            if (count == 0) {
                this.basketDao.insertBasket(connection, userVo, addVo);
            } else {
                this.basketDao.updateBasket(connection, userVo, addVo);
            }
        }
        return AddResult.SUCCESS;
    }

    public BasketGetResultContainer get(UserVo userVo) throws
            SQLException {
        if (userVo == null) {
            return new BasketGetResultContainer(GetResult.NOT_ALLOWED);
        }
        try (Connection connection = this.dataSource.getConnection()) {
            ArrayList<BasketVo> baskets = this.basketDao.selectBasket(connection, userVo);
            return new BasketGetResultContainer(GetResult.SUCCESS, baskets);
        }
    }
}