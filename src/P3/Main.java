package P3;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/ovchip?user=postgres&password=Lollol99"
            );

            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);
            AdresDAOPsql adao = new AdresDAOPsql(conn);

            testDAO(rdao, adao);

            conn.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private static void testDAO(ReizigerDAOPsql rdao, AdresDAOPsql adao) {
        List<Reiziger> reizigers = rdao.findAll();
        for (Reiziger a : reizigers) {
            System.out.println(a + " ||| " + adao.findByReiziger(a));
        }
        System.out.println();
    }
}