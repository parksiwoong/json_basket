package com.shinsegae.ssg.apis.services;

import com.shinsegae.ssg.apis.daos.ItemDao;
import com.shinsegae.ssg.apis.enums.item.AddResult;
import com.shinsegae.ssg.apis.vos.item.AddColorVo;
import com.shinsegae.ssg.apis.vos.item.AddSizeVo;
import com.shinsegae.ssg.apis.vos.item.AddVo;
import com.shinsegae.ssg.apis.vos.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ItemService {
    private final DataSource dataSource;
    private final ItemDao itemDao;

    private static final String NAME_REGEX = "^([0-9a-zA-Z가-힣 ]{4,100})$";
    private static final String COLOR_NAME_REGEX = "^([a-zA-Z가-힣 ]{1,100})$";
    private static final String SIZE_NAME_REGEX = "^([0-9A-Z]{1,5})$";

    @Autowired
    public ItemService(DataSource dataSource, ItemDao itemDao) {
        this.dataSource = dataSource;
        this.itemDao = itemDao;
    }

    public AddResult add(UserVo userVo, AddVo addVo) throws
            SQLException {
        if (!addVo.getName().matches(NAME_REGEX) || addVo.getPrice() < 0) {
            return AddResult.INVALID_INPUT;
        }
        if (userVo == null || !userVo.isAdmin()) {
            return AddResult.NOT_ALLOWED;
        }
        try (Connection connection = this.dataSource.getConnection()) {
            this.itemDao.insertItem(connection, addVo);
        }
        return AddResult.SUCCESS;
    }

    public AddResult addColor(UserVo userVo, AddColorVo addColorVo) throws
            SQLException {
        if (addColorVo.getItemIndex() < 0 ||
                addColorVo.getVariation() < 0 ||
                !addColorVo.getColorName().matches(COLOR_NAME_REGEX)) {
            return AddResult.INVALID_INPUT;
        }
        if (userVo == null || !userVo.isAdmin()) {
            return AddResult.NOT_ALLOWED;
        }
        try (Connection connection = this.dataSource.getConnection()) {
            this.itemDao.insertItemColor(connection, addColorVo);
        }
        return AddResult.SUCCESS;
    }

    public AddResult addSize(UserVo userVo, AddSizeVo addSizeVo) throws
            SQLException {
        if (addSizeVo.getItemIndex() < 0 ||
                addSizeVo.getVariation() == Integer.MAX_VALUE ||
                !addSizeVo.getSizeName().matches(SIZE_NAME_REGEX)) {
            return AddResult.INVALID_INPUT;
        }
        if (userVo == null || !userVo.isAdmin()) {
            return AddResult.NOT_ALLOWED;
        }
        try (Connection connection = this.dataSource.getConnection()) {
            this.itemDao.insertItemSize(connection, addSizeVo);
        }
        return AddResult.SUCCESS;
    }
}