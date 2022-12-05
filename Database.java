import java.sql.*;

public class Database {
    public static void main(String args[]) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            ResultSet ResultSet = conn.getMetaData().getCatalogs();
            Statement stmt = conn.createStatement();
            Integer dbCheck = 0;
            while (ResultSet.next()) {
                String dbNames = ResultSet.getString(1);
                if (dbNames.equals("auction_system")) {
                    dbCheck = 1;
                    break;
                }
            }
            if (dbCheck == 0) {
                String createDb = "CREATE DATABASE auction_system";
                stmt.executeUpdate(createDb);
            }
            conn.close();
            String url = "jdbc:mysql://localhost:3306/auction_system";
            Connection connection = DriverManager.getConnection(url, "root", "");

            ResultSet tables = connection.getMetaData().getTables("auction_system", null, "%", args);

            Integer createUsers = 0;
            Integer createAdmin = 0;
            Integer createBid = 0;
            Integer createAuction = 0;
            while (tables.next()) {
                if (tables.getString(3).equals("users")) {
                    createUsers = 1;
                }
                if (tables.getString(3).equals("admin")) {
                    createAdmin = 1;
                }
                if (tables.getString(3).equals("bid")) {
                    createBid = 1;
                }
                if (tables.getString(3).equals("auction")) {
                    createAuction = 1;
                }
            }
            if (createUsers == 0) {
                String createTb = "Create table users(userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, userName nvarchar(40), firstName nvarchar(40), lastName nvarchar(40), passWord nvarchar(40), email nvarchar(40) NULL, phone char(8) NULL, birthDay Date NULL, registeredAt Date, role char(1))";
                CallableStatement cstmt = connection.prepareCall(createTb);
                cstmt.execute();
            }
            if (createAdmin == 0) {
                String createTb = "Create table admin(adminId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, username nvarchar(20), password nvarchar(32))";
                CallableStatement cstmt = connection.prepareCall(createTb);
                cstmt.execute();
            }
            if (createBid == 0) {
                String createTb = "Create table bid(bidId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, auctionId INT NOT NULL, bidTime DATE, price nvarchar(10), userId INT NULL)";
                CallableStatement cstmt = connection.prepareCall(createTb);
                cstmt.execute();
            }
            if (createAuction == 0) {
                String createTb = "Create table auction(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, title nvarchar(40), user nvarchar(40), startPrice nvarchar(100), endPrice nvarchar(256), description nvarchar(1000), startTime DATETIME, endTime DATETIME, status varchar(10), img varchar(256), winner nvarchar(40))";
                CallableStatement cstmt = connection.prepareCall(createTb);
                cstmt.execute();
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}