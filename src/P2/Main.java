package P2;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    private Connection connection;

    public static void main(String[] args) {

    }

    private Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/ovchip?user=postgres&password=Lollol99"
            );
            return conn;
        } catch(Exception e){
            System.out.println("foutje bedankt");
        }
    return null;
    }
}
