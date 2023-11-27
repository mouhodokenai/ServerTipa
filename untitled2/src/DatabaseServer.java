import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class DatabaseServer {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/clicker?serverTimezone=UTC"; // "clicker" это название бд, нужно заменить
    private static final String USERNAME = "тут имя пользователя ";
    private static final String PASSWORD = "тут пароль";

    private static final int SERVER_PORT = 12776;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server listening on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String query = reader.readLine();
                    System.out.println("Received query from client: " + query);

                    String result = executeQuery(query);
                    writer.println(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String executeQuery(String sqlQuery) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            StringBuilder result = new StringBuilder();
            while (resultSet.next()) {
                String columnName = resultSet.getString("name");
                result.append(columnName).append("\n");
            }

            return result.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error processing the query.";
        }
    }
}
