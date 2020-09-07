package P4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection connection;

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reiziger values(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, reiziger.getReiziger_id());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, reiziger.getGeboortedatum());
            preparedStatement.execute();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE reiziger SET voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=? ");
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, reiziger.getGeboortedatum());
            preparedStatement.setInt(5, reiziger.getReiziger_id());
            preparedStatement.execute();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?");
            preparedStatement.setInt(1, reiziger.getReiziger_id());
            return preparedStatement.execute();
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int reizigerid = resultSet.getInt("reiziger_id");
            String voorletters = resultSet.getString("voorletters");
            String tussenvoegsel = resultSet.getString("tussenvoegsel");
            String achternaam = resultSet.getString("achternaam");
            Date geboortedatum = resultSet.getDate("geboortedatum");

            Reiziger r1 = new Reiziger(reizigerid, voorletters, tussenvoegsel, achternaam, geboortedatum, null);
            return r1;

        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum=?");
            preparedStatement.setDate(1, Date.valueOf(datum));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int reizigerid = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                Date geboortedatum = resultSet.getDate("geboortedatum");

                Reiziger r1 = new Reiziger(reizigerid, voorletters, tussenvoegsel, achternaam, geboortedatum, null);
                reizigers.add(r1);
            }
            return reizigers;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reiziger");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int reizigerid = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                Date geboortedatum = resultSet.getDate("geboortedatum");

                Reiziger r1 = new Reiziger(reizigerid, voorletters, tussenvoegsel, achternaam, geboortedatum, null);
                reizigers.add(r1);
            }
            return reizigers;

        } catch (SQLException e) {
            return null;
        }
    }
}
