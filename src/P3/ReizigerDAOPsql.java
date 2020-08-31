package P3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection connection;
    private AdresDAO adao;

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reiziger values(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, reiziger.getGeboortedatum());
            return preparedStatement.execute();
        } catch(Exception e){
            System.out.println("\nGebruiker met id " + reiziger.getId() + " bestaat al!");
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE reiziger SET voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=? ");
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, reiziger.getGeboortedatum());
            return preparedStatement.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?");
            preparedStatement.setInt(1, reiziger.getId());
            return preparedStatement.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
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

            return new Reiziger(reizigerid, voorletters, tussenvoegsel, achternaam, geboortedatum);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
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

                reizigers.add(new Reiziger(reizigerid, voorletters, tussenvoegsel, achternaam, geboortedatum));
            }
            return reizigers;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
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

                reizigers.add(new Reiziger(reizigerid, voorletters, tussenvoegsel, achternaam, geboortedatum));
            }
            return reizigers;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
