package P4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
       try {
           PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO ov_chipkaart values(?, ?, ?, ?, ?)");
           preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
           preparedStatement.setDate(2, ovChipkaart.getGeldig_tot());
           preparedStatement.setInt(3, ovChipkaart.getKlasse());
           preparedStatement.setDouble(4, ovChipkaart.getSaldo());
           preparedStatement.setInt(5, ovChipkaart.getReiziger_id());
           preparedStatement.execute();
           return true;
       } catch(Exception e) {
           return false;
       }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE ov_chipkaart SET geldig_tot=?, klasse=?, saldo=?, reiziger_id=? WHERE kaart_nummer=?");
            preparedStatement.setDate(1, ovChipkaart.getGeldig_tot());
            preparedStatement.setInt(2, ovChipkaart.getKlasse());
            preparedStatement.setDouble(3, ovChipkaart.getSaldo());
            preparedStatement.setInt(4, ovChipkaart.getKlasse());
            preparedStatement.setInt(5, ovChipkaart.getKaart_nummer());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer=?");
            preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
            preparedStatement.execute();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findall() {
        List<OVChipkaart> kaarten = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int kaart_nummer = resultSet.getInt("kaart_nummer");
                Date geldig_tot = resultSet.getDate("geldig_tot");
                int klasse = resultSet.getInt("klasse");
                double saldo = resultSet.getDouble("saldo");
                int reiziger_id = resultSet.getInt("reiziger_id");
                OVChipkaart o1= new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);
                kaarten.add(o1);
            }
            return kaarten;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) { //dit veranderd naar een list omdat een reiziger meerdere kaarten kan hebben
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id=?");
            preparedStatement.setInt(1, reiziger.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int kaart_nummer = resultSet.getInt("kaart_nummer");
                Date geldig_tot = resultSet.getDate("geldig_tot");
                int klasse = resultSet.getInt("klasse");
                double saldo = resultSet.getDouble("saldo");
                int reiziger_id = resultSet.getInt("reiziger_id");
                ovChipkaarten.add(new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id));
            }
            return ovChipkaarten;
        } catch (Exception e) {
            return null;
        }
    }
}
