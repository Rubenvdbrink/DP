package P3;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/ovchip?user=postgres&password=Lollol99"
            );


            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn, new AdresDAOPsql(conn));

            testDAO(rdao);

            conn.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private static void testDAO(ReizigerDAOPsql rdao) {
        System.out.println("------------------------------------------------ReizigerDAO------------------------------------------------");
        List<Reiziger> reizigers = rdao.findAll();

        //print alle reizigers met hun bijbehorende adres
        int aantal = 0;
        if (!reizigers.isEmpty()) {
            for (Reiziger a : reizigers) {
                aantal += 1;
                System.out.println(a);
            }
        }
        System.out.println("------------[" + aantal + " reizigers getoond]------------");

        //saved een nieuwe reiziger
        if(rdao.save(new Reiziger(10, "Ruben", "van den", "Brink", Date.valueOf("2002-04-30"), null))) {
                System.out.println("\n[Nieuwe reiziger] : " + rdao.findById(10));
         } else {
            System.out.println("\nGebruiker niet gesaved omdat er al een gebruiker met dit id al bestaat.");
        }

        //delete een reiziger
        if(rdao.getAdao().delete(rdao.findById(10))) {
            System.out.println("\n[Gebruiker verwijderd]");
        } else {
            System.out.println("\n[Gebruiker niet verwijderd]");
        }

        //findbyid
        if (!rdao.findById(2).equals(null)) {
            System.out.println("\n[Gevonden reiziger] : " + rdao.findById(2) +"\n");
        }

        //findbygbdatum
        if(!rdao.findByGbdatum("2002-12-03").isEmpty()) {
            int aantalreizigers = 0;
            for(Reiziger r : rdao.findByGbdatum("2002-12-03")) {
                aantalreizigers += 1;
                System.out.println(r);
            }
            System.out.println("------------[" + aantalreizigers + " reizigers getoond]------------");
        }

        //update reiziger
        if (!rdao.findById(3).equals(null)) {
            Reiziger reiziger_old = rdao.findById(3);
            Reiziger reiziger_new = new Reiziger(reiziger_old.getId(), "RG", reiziger_old.getTussenvoegsel(),
                    reiziger_old.getAchternaam(), reiziger_old.getGeboortedatum(), reiziger_old.getAdres());
            if(rdao.update(reiziger_new)) {
                System.out.println("\n[Gebruiker geupdate] Nieuw: " + reiziger_new + " Oud: " + reiziger_old);
            } else {
                System.out.println("[Gebruiker niet geupdate]");
            }
        }
        System.out.println("------------------------------------------------AdresDAO------------------------------------------------");

        //toon alle adressen
        List<Adres> adressen = rdao.getAdao().findAll();

        //print alle reizigers met hun bijbehorende adres
        int aantaladressen = 0;
        if(!adressen.isEmpty()) {
            for (Adres a : adressen) {
                aantaladressen += 1;
                System.out.println(a);
            }
        }
        System.out.println("------------[" + aantaladressen + " adressen getoond]------------");

        //findbyreiziger adres
        Reiziger reiziger2 = rdao.findById(2);
        if(!rdao.getAdao().findByReiziger(reiziger2).equals(null)) {
            System.out.println("\n[Gevonden Adres] : " + rdao.getAdao().findByReiziger(reiziger2));
        } else {
            System.out.println("\nGeen adres gevonden");
        }

        //delete adres
        Reiziger reiziger5 = rdao.findById(5);
        if (rdao.getAdao().delete(reiziger5)) {
            System.out.println("\n[Adres verwijderd]");
        } else {
            System.out.println("\n[Adres niet verwijderd omdat reiziger geen adres heeft]");
        }

        //update adres
        if(!rdao.getAdao().findByReiziger(rdao.findById(4)).equals(null)) {
            Adres adres_old = rdao.getAdao().findByReiziger(rdao.findById(4));
            Adres adres_new = new Adres(adres_old.getId(), adres_old.getPostcode(),
                    adres_old.getHuisnummer(), adres_old.getStraat(), "Culemborg");
            if(rdao.getAdao().update(adres_new)) {
                System.out.println("\n[Adres geupdate] Nieuw: " + adres_new + " Oud: " + adres_old);
            } else {
                System.out.println("\n[Adres niet geupdate]");
            }
        }

        //save adres
        Adres new_adres = new Adres(555, "4102AW", "190", "Mendelssohnstraat", "Culemborg");
        if(rdao.getAdao().save(new_adres)) {
            System.out.println("\n[Nieuw adres] : " + new_adres);
        } else {
            System.out.println("\nAdres niet gesaved omdat adres al bestaat.");
        }
    }
}