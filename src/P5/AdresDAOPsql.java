package P5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO adres values(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, adres.getId());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.execute();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE adres SET woonplaats=?, postcode=?, huisnummer=?, straat=? WHERE adres_id=?");
            preparedStatement.setString(1, adres.getWoonplaats());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setInt(5, adres.getId());
            preparedStatement.execute();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM adres WHERE reiziger_id=?");
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.execute();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM adres WHERE reiziger_id=?");
            preparedStatement.setInt(1, reiziger.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int adresid = resultSet.getInt("adres_id");
            String postcode = resultSet.getString("postcode");
            String huisnummer = resultSet.getString("huisnummer");
            String straat = resultSet.getString("straat");
            String woonplaats = resultSet.getString("woonplaats");

            return new Adres(adresid, postcode, huisnummer, straat, woonplaats);
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {

        List<Adres> adressen = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM adres");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int adresid = resultSet.getInt("adres_id");
                String postcode = resultSet.getString("postcode");
                String huisnummer = resultSet.getString("huisnummer");
                String straat = resultSet.getString("straat");
                String woonplaats = resultSet.getString("woonplaats");

                adressen.add(new Adres(adresid, postcode, huisnummer, straat, woonplaats));

            }
            return adressen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
