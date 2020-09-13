package P5;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        ReizigerDAOPsql rdao;
        ProductDAOPsql pdao;
        OVChipkaartDAOPsql odao;

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Lollol99"
            );

            rdao = new ReizigerDAOPsql(conn);
            pdao = new ProductDAOPsql(conn);
            odao = new OVChipkaartDAOPsql(conn);
            odao.setPdao(pdao);

            testDAO(pdao, rdao, odao);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testDAO(ProductDAOPsql pdao, ReizigerDAO rdao, OVChipkaartDAO odao) {

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
        System.out.println("-------------------------------------------------------------");
        //test: nieuw product toevoegen en daarmee ook gelijk alle connecties tussen ovchipkaarten en het nieuwe product
        Product p1 = new Product(100, "Gratis Reizen", "U hoeft niet meer te betalen voor reizen", 0.01);
        OVChipkaart o2 = new OVChipkaart(19876, Date.valueOf("2021-09-09") , 1, 500, 1);
        OVChipkaart o3 = new OVChipkaart(19777, Date.valueOf("2021-09-10") , 1, 504, 1);
        odao.save(o2);
        odao.save(o3);
        p1.getOvChipkaarten().add(o2);
        p1.getOvChipkaarten().add(o3);
        System.out.println("voor:");
        for (OVChipkaart ovChipkaart : odao.findall()) {
            System.out.println(ovChipkaart);
        }
        System.out.println("na:");
        pdao.save(p1);
        for (OVChipkaart ovChipkaart : odao.findall()) {
            System.out.println(ovChipkaart);
        }

//
//        System.out.println("------------------------------------------------ReizigerDAO------------------------------------------------");
//        List<Reiziger> reizigers = rdao.findAll();
//
//        //print alle reizigers met hun bijbehorende adres
//        int aantal = 0;
//        if (!reizigers.isEmpty()) {
//            for (Reiziger a : reizigers) {
//                aantal += 1;
//                System.out.println(a);
//            }
//        }
//        System.out.println("------------[" + aantal + " reizigers getoond]------------\n");
//
//        //print alle kaarten
//        List<OVChipkaart> kaarten = rdao.getOdao().findall();
//        aantal = 0;
//        if(!kaarten.isEmpty()) {
//            for(OVChipkaart o : kaarten) {
//                aantal += 1;
//                System.out.println(o);
//            }
//        }
//        System.out.println("------------[" + aantal + " OV chipkaarten getoond]------------\n");
//
//        //saved een nieuwe reiziger r1
//        Reiziger r1 = new Reiziger(6, "R", "van den", "Brink", Date.valueOf("2002-04-30"));
//        if(rdao.save(r1)) {
//                System.out.println("[Nieuwe reiziger aangemaakt]");
//         } else {
//            System.out.println("Gebruiker niet gesaved omdat er al een gebruiker met dit id al bestaat.");
//        }
//
//        //gooit nieuwe ovchipkaarten in de lijst ovkaarten van de nieuwe reiziger r1
//        OVChipkaart o1 = new OVChipkaart(11111, Date.valueOf("2023-04-30"), 2, 50.50, 6);
//        r1.getOvChipkaarten().add(o1);
//
//        OVChipkaart o2 = new OVChipkaart(22222, Date.valueOf("2023-05-30"), 1, 27.50, 6);
//        r1.getOvChipkaarten().add(o2);
//
//        OVChipkaart o3 = new OVChipkaart(33333, Date.valueOf("2022-04-30"), 1, 21.50, 6);
//        r1.getOvChipkaarten().add(o3);
//
//        //saved de gehele lijst van ovchipkaarten van reiziger r1 in de database
//        for (OVChipkaart o : r1.getOvChipkaarten()) {
//                if(rdao.getOdao().save(o)) {
//                    System.out.println("[OV chipkaart opgeslagen]");
//                } else {
//                    System.out.println("[OV chipkaart niet opgeslagen, bestaat al!]");
//                }
//        }
        }
    }