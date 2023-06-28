package Model;

import Db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public static String generateNextUserId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT User_Id FROM user ORDER BY User_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if (resultSet.next()) {
            return splitUserId(resultSet.getString(1));
        }
        return splitUserId((null));
    }

    public static String splitUserId(String currentUserId) {
        if (currentUserId != null) {
            String[] strings = currentUserId.split("U0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "U00" + id;
        }
        return "U001";
    }


    public static List<String> getUserName() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT User_Name FROM user";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(2));
        }
        return codes;
    }
}
