package org.example.mqtt;

import junit.framework.TestCase;
import org.example.service.hibernate.TopicService;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MqttTopicsExample  extends TestCase {
    public void testName() {
        try {
            // URL административного API вашего брокера
            URL url = new URL("http://localhost:1883/api/v4/topics");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Authorization", "Bearer <YOUR_API_TOKEN>");

            // Чтение ответа
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
                scanner.close();
            } else {
                System.out.println("Ошибка: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() {
        TopicService topicService = new TopicService();
        System.out.println(topicService.getAllTopics());
    }
}
