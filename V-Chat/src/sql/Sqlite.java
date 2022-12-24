package sql;

import java.sql.*;

public class Sqlite {

    Connection connection = null;



    private Connection connectDB(){
        try
        {
            String connectionURL = "jdbc:sqlite:netrunnerServerDB.db";
            this.connection = DriverManager.getConnection(connectionURL);
            return connection;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }

    public UserSettings getSettings(String username,String password) throws SQLException {

        UserSettings userSettings = new UserSettings();

        String sql = "SELECT * FROM USER_LIST WHERE USERNAME = '"+username+"' AND PASSWORD = '"+password+"'";
        connectDB();

        ResultSet rs  = connection.createStatement().executeQuery(sql);

        while (rs.next()) {

            userSettings.setID(rs.getInt("ID"));
            userSettings.setUSERNAME(rs.getString("USERNAME"));
            userSettings.setPASSWORD(rs.getString("PASSWORD"));
            userSettings.setNAME(rs.getString("NAME"));
            userSettings.setSURNAME(rs.getString("SURNAME"));
            userSettings.setLEVEL(rs.getInt("LEVEL"));
            userSettings.setIMAGE(rs.getBytes("IMAGE"));
            userSettings.setFIRSTLOGIN(rs.getInt("FIRSTLOGIN"));


        }
        rs.close();
        closeConnectDB();
        return userSettings;

    }

    public boolean updateSettings(UserSettings settings) throws SQLException {

        String sql = "UPDATE USER_LIST SET USERNAME = ?,PASSWORD = ?,LASTLOGIN = ?,FIRSTLOGIN =?,NAME =?,SURNAME=?,IMAGE=?,LEVEL = ? WHERE USERNAME = ? AND PASSWORD = ?";
        connectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,settings.getUSERNAME());
        preparedStatement.setString(2,settings.getPASSWORD());
        preparedStatement.setString(3,settings.getLASTLOGIN());
        preparedStatement.setInt(4,settings.getFIRSTLOGIN());
        preparedStatement.setString(5,settings.getNAME());
        preparedStatement.setString(6,settings.getSURNAME());
        preparedStatement.setBytes(7,settings.getIMAGE());
        preparedStatement.setInt(8,settings.getLEVEL());

        preparedStatement.setString(9,settings.getUSERNAME());
        preparedStatement.setString(10,settings.getPASSWORD());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        closeConnectDB();
        return true;

    }

    public boolean updateUser(String username,String name,String surname,byte[] image) throws SQLException {

        String sql = "UPDATE USER_LIST SET NAME =?,SURNAME=?,IMAGE=? WHERE USERNAME = ?";
        connectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,name);
        preparedStatement.setString(2,surname);
        preparedStatement.setBytes(3,image);
        preparedStatement.setString(4,username);


        preparedStatement.executeUpdate();

        preparedStatement.close();
        closeConnectDB();
        return true;

    }

    public boolean loginControl(String username,String password) throws SQLException {

        String sql = "SELECT * FROM USER_LIST WHERE USERNAME = '"+username+"' AND PASSWORD = '"+password+"'";

        connectDB();

        ResultSet rs  = connection.createStatement().executeQuery(sql);

        String uname = "";
        String passw = "";

        while (rs.next()) {
            uname = rs.getString("USERNAME");
            passw = rs.getString("PASSWORD");
        }

        if (!uname.isEmpty() && !passw.isEmpty()) {
            return true;
        }
        else
        {
            return false;
        }

    }

}
