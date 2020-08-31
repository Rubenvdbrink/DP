package P2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/ovchip?user=postgres&password=Lollol99"
            );
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);

            testReizigerDAO(rdao);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.println("sietske is verwijderd!");
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //DELETE
        rdao.delete(sietske);
        System.out.println("sietske is verwijderd!");

        //find by ID
        System.out.println("\nReiziger met gekozen id:");
        System.out.println(rdao.findById(1));

        //find by GbDatum
        System.out.println("\nReiziger met gekozen geboorte datum:");
        System.out.println(rdao.findByGbdatum("2002-10-22"));
    }
}
