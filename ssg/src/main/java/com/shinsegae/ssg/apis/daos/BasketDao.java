package com.shinsegae.ssg.apis.daos;

import com.shinsegae.ssg.apis.vos.basket.AddVo;
import com.shinsegae.ssg.apis.vos.basket.BasketVo;
import com.shinsegae.ssg.apis.vos.user.UserVo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class BasketDao {
    public void insertBasket(Connection connection, UserVo userVo, AddVo addVo) throws
            SQLException {
        String query = "" +
                "INSERT INTO `ssg`.`baskets` (`user_index`,\n" +
                "                             `item_index`,\n" +
                "                             `color_index`,\n" +
                "                             `size_index`,\n" +
                "                             `basket_count`)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userVo.getIndex());
            preparedStatement.setInt(2, addVo.getItemIndex());
            preparedStatement.setInt(3, addVo.getColorIndex());
            preparedStatement.setInt(4, addVo.getSizeIndex());
            preparedStatement.setInt(5, addVo.getCount());
            preparedStatement.execute();
        }
    }

    public void updateBasket(Connection connection, UserVo userVo, AddVo addVo) throws
            SQLException {
        String query = "" +
                "UPDATE `ssg`.`baskets`\n" +
                "SET `basket_count`    = `basket_count` + ?,\n" +
                "    `basket_datetime` = CURRENT_TIMESTAMP()\n" +
                "WHERE `user_index` = ?\n" +
                "  AND `item_index` = ?\n" +
                "  AND `color_index` = ?\n" +
                "  AND `size_index` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, addVo.getCount());
            preparedStatement.setInt(2, userVo.getIndex());
            preparedStatement.setInt(3, addVo.getItemIndex());
            preparedStatement.setInt(4, addVo.getColorIndex());
            preparedStatement.setInt(5, addVo.getSizeIndex());
            preparedStatement.execute();
        }
    }

    public ArrayList<BasketVo> selectBasket(Connection connection, UserVo userVo) throws
            SQLException {
        ArrayList<BasketVo> baskets = new ArrayList<>();
        String query = "" +
                "SELECT `item`.`item_name`              AS `itemName`,\n" +
                "       `item`.`item_price`             AS `itemPrice`,\n" +
                "       `color`.`color_name`            AS `colorName`,\n" +
                "       `color`.`color_price_variation` AS `colorVariation`,\n" +
                "       `size`.`size_name`              AS `sizeName`,\n" +
                "       `size`.`size_price_variation`   AS `sizeVariation`,\n" +
                "       `basket`.`basket_datetime`      AS `basketDateTime`,\n" +
                "       `basket`.`basket_count`         AS `basketCount`\n" +
                "FROM `ssg`.`baskets` AS `basket`\n" +
                "         INNER JOIN `ssg`.`items` AS `item` ON `item`.`item_index` = `basket`.`item_index`\n" +
                "         INNER JOIN `ssg`.`item_colors` AS `color` ON `color`.`item_index` = `basket`.`item_index`\n" +
                "         INNER JOIN `ssg`.`item_sizes` AS `size` ON `size`.`item_index` = `basket`.`item_index`\n" +
                "WHERE `user_index` = ?\n" +
                "GROUP BY `basket`.`basket_index`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userVo.getIndex());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BasketVo basketVo = new BasketVo(
                            resultSet.getString("itemName"),
                            resultSet.getInt("itemPrice"),
                            resultSet.getString("colorName"),
                            resultSet.getInt("colorVariation"),
                            resultSet.getString("sizeName"),
                            resultSet.getInt("sizeVariation"),
                            resultSet.getDate("basketDateTime"),
                            resultSet.getInt("basketCount")
                    );
                    baskets.add(basketVo);
                }
            }
        }
        return baskets;
    }

    public int selectBasket(Connection connection, UserVo userVo, AddVo addVo) throws
            SQLException {
        int count;
        String query = "" +
                "SELECT COUNT(`basket_index`) AS `count`\n" +
                "FROM `ssg`.`baskets`\n" +
                "WHERE `user_index` = ?\n" +
                "  AND `item_index` = ?\n" +
                "  AND `color_index` = ?\n" +
                "  AND `size_index` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userVo.getIndex());
            preparedStatement.setInt(2, addVo.getItemIndex());
            preparedStatement.setInt(3, addVo.getColorIndex());
            preparedStatement.setInt(4, addVo.getSizeIndex());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt("count");
            }
        }
        return count;
    }
}