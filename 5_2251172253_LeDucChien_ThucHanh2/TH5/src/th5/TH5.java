import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TH5{

    // Địa chỉ máy cài ActiveMQ với cổng mặc định
    private static final String url = "failover://tcp://localhost:61616";

    // Tên queue
    private static final String subject = "TESTQUEUE1";

    public static void main(String[] args) {
        Connection connection = null;
        Session session = null;

        try {
            // Tạo kết nối đến ActiveMQ
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();

            // Tạo session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Tạo queue đích
            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);

            // Gửi message
            int msgTemp = 0;
            while (true) {
                msgTemp++;
                String msg = "TextMessage: " + msgTemp;
                TextMessage message = session.createTextMessage(msg);
                producer.send(message);
                System.out.println("→ " + msg + " has been sent.");
                Thread.sleep(1000); // Gửi mỗi 1 giây
            }

        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối (nếu cần)
            try {
                if (session != null) session.close();
                if (connection != null) connection.close();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }
}
