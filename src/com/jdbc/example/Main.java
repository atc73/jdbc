package com.jdbc.example;

import java.sql.*;

public class Main {
    private static String url = "jdbc:mysql://localhost:3306/example?serverTimezone=UTC";
    private static String user = "root";
    private static String password= "";

    public static void main(String[] args) throws SQLException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            System.out.println("driver installed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Driver MySQL introuvable");
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connecté");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "SELECT * FROM fruit";
        try {
            assert connection != null;
            try (Statement st = connection.createStatement()) {
                ResultSet resultSet = st.executeQuery(query);
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("idFruit"));
                    System.out.println(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String querySql= "SELECT *FROM fruit WHERE idFruit = ?";
        try (PreparedStatement preparedStatement= connection.prepareStatement(querySql)) {
            preparedStatement.setLong(1, 1L);
            ResultSet resultSet= preparedStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getInt("idFruit"));
            System.out.println(resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.close();

    }

}
