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
                if (tables.getString(3).equals("user")) {
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
                String createTb = "Create table user(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, userName nvarchar(40) NOT NULL, firstName nvarchar(40), lastName nvarchar(40), passWord nvarchar(40) NOT NULL, email nvarchar(40) NOT NULL UNIQUE, phone char(8) NOT NULL UNIQUE, birthDay DATETIME NOT NULL, registeredAt DATETIME)";
                CallableStatement cstmt = connection.prepareCall(createTb);
                cstmt.execute();
            }
            if (createAdmin == 0) {
                String createTb = "Create table admin(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, username nvarchar(20) NOT NULL UNIQUE, password nvarchar(32) NOT NULL, phone nvarchar(8), email nvarchar(40))";
                CallableStatement cstmt = connection.prepareCall(createTb);
                cstmt.execute();
            }
            if (createBid == 0) {
                String createTb = "Create table bid(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, email varchar(40) NOT NULL, auctionId INT NOT NULL, createdAt DATETIME, price nvarchar(1000) NOT NULL, userId INT NULL)";
                CallableStatement cstmt = connection.prepareCall(createTb);
                cstmt.execute();
            }
            if (createAuction == 0) {
                String createTb = "Create table auction(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, title nvarchar(40) NOT NULL, user nvarchar(40) NOT NULL, userId INT NOT NULL, startPrice nvarchar(100) NOT NULL, endPrice nvarchar(256) NOT NULL, description nvarchar(1000) NOT NULL, startDateTime DATETIME, endDateTime DATETIME, status varchar(10), img varchar(256) NOT NULL, winner nvarchar(40) NOT NULL)";
                CallableStatement cstmt = connection.prepareCall(createTb);
                cstmt.execute();
            }
            String insert = "insert into user(userName, firstName, lastName, passWord, email, phone, birthDay, registeredAt) values ('user1', 'fname1', 'lname1', md5('123'), 'user1@gmail.com', '88888888', DATE '1999-03-13' , now())";
            CallableStatement cstmt = connection.prepareCall(insert);
            cstmt.execute();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}