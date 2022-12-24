package sql;

import data.req_classes.ReqFuncs;

import java.sql.*;

public class Sqlite {

    Connection connection = null;

    ReqFuncs reqFuncs = new ReqFuncs();

    private Connection connectDB(){
        try
        {
            String connectionURL = "jdbc:sqlite:netrunnerSettings.db";
            this.connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connection to SQLite has been established.");
            return connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    private void  closeConnectDB() {
        try
        {
            if (!connection.isClosed()) {
                this.connection.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserSettings getSettings() throws SQLException {

        UserSettings userSettings = new UserSettings();

        String sql = "SELECT * FROM USER_SETTINGS";
        connectDB();

        ResultSet rs  = connection.createStatement().executeQuery(sql);

        while (rs.next()) {

            userSettings.setID(rs.getInt("ID"));
            userSettings.setUSERNAME(rs.getString("USERNAME"));
            userSettings.setPASSWORD(rs.getString("PASSWORD"));
            userSettings.setREMEMBER(rs.getInt("REMEMBER"));



        }
        rs.close();
        closeConnectDB();
        return userSettings;

    }

    public boolean updateSettings(UserSettings settings) throws SQLException {

        String sql = "UPDATE USER_SETTINGS SET USERNAME = ?,PASSWORD = ?,REMEMBER = ? WHERE ID = 1";
        connectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,settings.getUSERNAME());
        preparedStatement.setString(2,settings.getPASSWORD());
        preparedStatement.setInt(3,settings.getREMEMBER());


        preparedStatement.executeUpdate();
        preparedStatement.close();
        closeConnectDB();
        return true;

    }

}
