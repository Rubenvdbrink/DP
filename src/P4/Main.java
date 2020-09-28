package P4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Lollol99"
            );

            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);

            testDAO(rdao);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testDAO(ReizigerDAOPsql rdao) {

        System.out.println("------------------------------------------------ReizigerDAO------------------------------------------------");
        List<Reiziger> reizigers = rdao.findAll();

        //print alle reizigers met hun informate, bijbehorende adres en ovchipkaarten
        int aantal = 0;
        if (!reizigers.isEmpty()) {
            for (Reiziger a : reizigers) {
                aantal += 1;
                System.out.println(a);
            }
        }
        System.out.println("------------[" + aantal + " reizigers getoond]------------\n");

        //print alle kaarten
        List<OVChipkaart> kaarten = rdao.getOdao().findall();
        aantal = 0;
        if(!kaarten.isEmpty()) {
            for(OVChipkaart o : kaarten) {
                aantal += 1;
                System.out.println(o);
            }
        }
        System.out.println("------------[" + aantal + " OV chipkaarten getoond]------------\n");

        //saved een nieuwe reiziger r1
        Reiziger r1 = new Reiziger(6, "R", "van den", "Brink", Date.valueOf("2002-04-30"));

        //gooit nieuwe ovchipkaarten in de lijst ovkaarten van de nieuwe reiziger r1
        OVChipkaart o1 = new OVChipkaart(11111, Date.valueOf("2023-04-30"), 2, 50.50, 6);
        r1.getOvChipkaarten().add(o1);

        OVChipkaart o2 = new OVChipkaart(22222, Date.valueOf("2023-05-30"), 1, 27.50, 6);
        r1.getOvChipkaarten().add(o2);

        OVChipkaart o3 = new OVChipkaart(33333, Date.valueOf("2022-04-30"), 1, 21.50, 6);
        r1.getOvChipkaarten().add(o3);

        //saved de nieuwe reiziger met al zijn ovchipkaarten
        if(rdao.save(r1)) {
                System.out.println("[Nieuwe reiziger aangemaakt en zijn adres en ovchipkaarten opgeslagen]");
         } else {
            System.out.println("Gebruiker niet gesaved omdat er al een gebruiker met dit id al bestaat.");
        }



        //testcode die tijdens het nakijken is gemaakt
//        Reiziger newreiziger = new Reiziger(600, "R", "vd", "Brink", Date.valueOf("2002-01-01"));
//        OVChipkaart ovChipkaart1 = new OVChipkaart(501, Date.valueOf("2020-01-01"), 2, 50.00, 600);
//        OVChipkaart ovChipkaart2 = new OVChipkaart(502, Date.valueOf("2020-01-01"), 2, 51.00, 600);
//        OVChipkaart ovChipkaart3 = new OVChipkaart(503, Date.valueOf("2020-01-01"), 2, 52.00, 600);
//
//        newreiziger.getOvChipkaarten().add(ovChipkaart1);
//        newreiziger.getOvChipkaarten().add(ovChipkaart2);
//        newreiziger.getOvChipkaarten().add(ovChipkaart3);
//
//        System.out.println(newreiziger.getOvChipkaarten().size());
//
//        if (rdao.save(newreiziger)) {
//            System.out.println("Reiziger is aangemaakt");
//        } else {
//            System.out.println("NIET GELUKT");
//        }
//        newreiziger.setAchternaam("andernaam");
//        rdao.update(newreiziger);
//
//        newreiziger.getOvChipkaarten().remove(0);
//        rdao.update(newreiziger);
        }
    }