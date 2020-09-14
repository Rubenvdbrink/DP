package P5;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        ProductDAOPsql pdao;
        OVChipkaartDAOPsql odao;

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Lollol99"
            );

            pdao = new ProductDAOPsql(conn);
            odao = new OVChipkaartDAOPsql(conn);
            odao.setPdao(pdao);

            testDAO(pdao, odao);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testDAO(ProductDAOPsql pdao, OVChipkaartDAO odao) {

        //test: toon alle producten van bepaalde ovchipkaart
        OVChipkaart o1 = new OVChipkaart(35283, null, 1, 500, 1111);
        System.out.println("-------Alle producten van OVChipkaart met nummer 35283-------");
        for(Product product : pdao.findByOVChipkaart(o1)) {
            System.out.println(product);
        }
        System.out.println("-------------------------------------------------------------");

        //test: toon alle producten
        System.out.println("\n---------------------Alle producten--------------------------");
        for (Product product : pdao.findall()) {
            System.out.println(product);
        }

        //test: nieuw product toevoegen en daarmee ook gelijk alle connecties tussen ovchipkaarten en het nieuwe product
        System.out.println("\n------Nieuw product toevoegen en aan ovkaarten toevoegen-------");

        Product p1 = new Product(100, "Gratis Reizen", "U hoeft niet meer te betalen voor reizen", 0.01);
        OVChipkaart o2 = new OVChipkaart(19876, Date.valueOf("2021-09-09") , 1, 500, 1);
        OVChipkaart o3 = new OVChipkaart(19777, Date.valueOf("2021-09-10") , 1, 504, 1);
        odao.save(o2);
        odao.save(o3);
        p1.getOvChipkaarten().add(o2);
        p1.getOvChipkaarten().add(o3);
        System.out.println("---voor:");
        for (OVChipkaart ovChipkaart : odao.findall()) {
            System.out.println(ovChipkaart);
        }
        System.out.println("\n---na:");
        pdao.save(p1);
        for (OVChipkaart ovChipkaart : odao.findall()) {
            System.out.println(ovChipkaart);
        }
        System.out.println("-------------------------------------------------------------");

        //test verwijder een product en verwijder tegelijk alle producten van de ovchipkaarten die dat product hebben
        System.out.println("\n--------Verwijder product en producten van ovchipkaart-------");
        System.out.println("---voor:");
        for (OVChipkaart ovChipkaart : odao.findall()) {
            System.out.println(ovChipkaart);
        }
        System.out.println("\n---na:");
        pdao.delete(p1);
        for (OVChipkaart ovChipkaart : odao.findall()) {
            System.out.println(ovChipkaart);
        }
        System.out.println("-------------------------------------------------------------");
        }
    }