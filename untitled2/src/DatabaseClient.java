import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DatabaseClient {

    // Адрес сервера и порт
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12776; // Предположим, что сервер слушает на порту 12345

    public static void main(String[] args) {
        try {
            // Устанавливаем соединение с сервером
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            // Получаем потоки ввода и вывода для обмена данными с сервером
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Пример отправки запроса на сервер
            String query = "SELECT * FROM clickers";
            writer.println(query);

            // Пример чтения ответа от сервера
            String response = reader.readLine();
            System.out.println("Response from server: " + response);

            // Закрываем ресурсы
            socket.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
