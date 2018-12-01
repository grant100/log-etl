package edu.uvu.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractLogs {

    public static void main(String[] args) throws Exception {
        read();
    }

    public static void read() throws Exception {

        System.out.print("Enter path to log file: ");
        Scanner in = new Scanner(System.in);
        String file = in.nextLine();

        List<Log> logs = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
        String line;
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        int count = 1;
        while ((line = reader.readLine()) != null) {
            System.out.print("\rParsing  ==> " + count + " logs");
            String res = line;


            Matcher m = p.matcher(res);

            List<String> tokens = new ArrayList<>();

            String[] splitn = res.split(" ");
            while (m.find()) {
                tokens.add(m.group(1));
            }

            String userAgent = "";
            String fullQuery = null;
            if (tokens.size() > 0) {
                userAgent = tokens.get(tokens.size() - 1);
                fullQuery = tokens.get(0);
            }


            try {
                Log log = new Log(splitn[0], splitn[1], splitn[2], splitn[3], splitn[5], splitn[6], splitn[7], splitn[8], splitn[9], splitn[10], userAgent, fullQuery);
                logs.add(log);
            } catch (Exception e) {
                Log log = new Log(splitn[0], splitn[1], splitn[2], splitn[3], splitn[5], splitn[6], "", "", "", "", userAgent, fullQuery);
                logs.add(log);
            }
            count++;
        }
        System.out.println("\rParsed   ==> " + count + " logs   ");
        String url = "jdbc:mysql://localhost:3306/webappsdb";
        String username = "cyber";
        String password = "security";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            System.out.println("\rConnected to: " + url);
            dropTable(connection);
            createTable(connection);
            insertLogs(connection, logs);
            connection.commit();
            System.out.println("Exiting!");
        } catch (SQLException e) {
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    public static void dropTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            String dropTable = "DROP TABLE logs;";
            statement.execute(dropTable);
        } catch (Exception e) {
        }

    }

    public static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createTable = "CREATE TABLE logs (" +
                "IP VARCHAR(30)," +
                "LOG_NAME VARCHAR(30)," +
                "USERNAME VARCHAR(30)," +
                "DATE DATE," +
                "METHOD VARCHAR(30)," +
                "QUERY VARCHAR(2000)," +
                "PROTOCOL VARCHAR(30)," +
                "HTTP_STATUS VARCHAR(30)," +
                "SIZE VARCHAR(2000)," +
                "REFERRAL_URL VARCHAR(2000)," +
                "USER_AGENT VARCHAR(2000)," +
                "FULL_QUERY VARCHAR(2000)" +
                " );";

        statement.execute(createTable);
        System.out.println("webappsdb.logs table created");
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
                "?," +
                "?" +
                ");";

        PreparedStatement statement = connection.prepareStatement(insertLogs);
        System.out.println("Inserting logs into webappsdb.logs table...");
        float i = 1;
        float size = logs.size();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        for (Log log : logs) {
            float pct = (i / size) * 100;
            System.out.print("\rWorking  ==> " + df.format(pct) + "%");
            statement.setString(1, log.getIp());
            statement.setString(2, log.getLogName());
            statement.setString(3, log.getUsername());
            statement.setDate(4, new java.sql.Date(log.getTimestamp().getTime()));         
            statement.setString(5, log.getMethod());
            statement.setString(6, log.getQuery());
            statement.setString(7, log.getProtocol());
            statement.setString(8, log.getHttpStatus());
            statement.setString(9, log.getSize());
            statement.setString(10, log.getReferrer());
            statement.setString(11, log.getUserAgent());
            statement.setString(12, log.getFullQuery());
            statement.execute();
            i++;
        }
        float pct = (i / size) * 100;
        System.out.println("\rFinished ==> " + df.format(pct) + "%      ");
        System.out.println("\rCompleted inserting logs into webappsdb.logs table");
    }


}
