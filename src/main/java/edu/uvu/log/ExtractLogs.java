package edu.uvu.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractLogs {

    public static void main(String[] args) throws Exception {
        read();
    }

    public static void read() throws Exception {
        System.out.println("Parsing logs...");
        List<Log> logs = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File("/root/access_all")));
        String line;

        while ((line = reader.readLine()) != null) {
            String res = line;

            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(res);

            List<String> tokens = new ArrayList<>();

            String[] splitn = res.split(" ");
            while (m.find()) {
                tokens.add(m.group(1));
            }

            String userAgent = "";
            if (tokens.size() > 0) {
                userAgent = tokens.get(tokens.size() - 1);
            }

            try {
                Log log = new Log(splitn[0], splitn[1], splitn[2], splitn[3] + splitn[4], splitn[5], splitn[6], splitn[7], splitn[8], splitn[9], splitn[10], userAgent);
                logs.add(log);
            } catch (Exception e) {
                Log log = new Log(splitn[0], splitn[1], splitn[2], splitn[3] + splitn[4], splitn[5], splitn[6], "", "", "", "", userAgent);
                logs.add(log);
            }

        }

        String url = "jdbc:mysql://localhost:3306/webappsdb";
        String username = "cyber";
        String password = "security";

        System.out.println("Parsed " + logs.size() + " logs...");
        System.out.println("Attempting to retrieve database connection...");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Retrieved databased connection...");
            createTable(connection);
            insertLogs(connection, logs);
            System.out.println("Exiting...");
        } catch (SQLException e) {
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    public static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createTable = "CREATE TABLE logs (" +
                "IP VARCHAR(30)," +
                "D1 VARCHAR(30)," +
                "D2 VARCHAR(30)," +
                "TIMESTAMP VARCHAR(30)," +
                "METHOD VARCHAR(30)," +
                "QUERY VARCHAR(2000)," +
                "PROTOCOL VARCHAR(30)," +
                "HTTP_STATUS VARCHAR(30)," +
                "SIZE VARCHAR(200)," +
                "REFERRAL_URL VARCHAR(2000)," +
                "USER_AGENT VARCHAR(2000)" +
                " );";

        statement.execute(createTable);
        System.out.println("webappsdb.logs table created...");
    }

    public static void insertLogs(Connection connection, List<Log> logs) throws SQLException {

        String insertLogs = "INSERT INTO logs VALUES (" +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?" +
                ");";

        PreparedStatement statement = connection.prepareStatement(insertLogs);

        for(Log log : logs){
            statement.setString(1, log.getIp());
            statement.setString(2, log.getDashOne());
            statement.setString(3, log.getDashTwo());
            statement.setString(4, log.getTimestamp());
            statement.setString(5, log.getMethod());
            statement.setString(6, log.getQuery());
            statement.setString(7, log.getProtocol());
            statement.setString(8, log.getHttpStatus());
            statement.setString(9, log.getSize());
            statement.setString(10, log.getReferralUrl());
            statement.setString(11, log.getUserAgent());

            statement.execute();
        }

        System.out.println("Completed inserting logs into webappsdb.logs table...");
    }


}
