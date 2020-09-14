package P5;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;

    public ProductDAOPsql(Connection conn) { this.conn = conn; }

    @Override
    public boolean save(Product product) {
        try {
            PreparedStatement saveProductDetails = conn.prepareStatement("INSERT INTO product values(?, ?, ?, ?)");
            saveProductDetails.setInt(1, product.getProduct_nummer());
            saveProductDetails.setString(2, product.getNaam());
            saveProductDetails.setString(3, product.getBeschrijving());
            saveProductDetails.setDouble(4, product.getPrijs());
            saveProductDetails.execute();

            //hier wordt de relatie tussen ovchipkaart en zijn producten gepersisteerd
            PreparedStatement saveOvChipkaartProducten = conn.prepareStatement("INSERT INTO ov_chipkaart_product values(?, ?, ?, ?)");
            saveOvChipkaartProducten.setInt(2, product.getProduct_nummer());
            for(OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
                saveOvChipkaartProducten.setInt(1, ovChipkaart.getKaart_nummer());
                saveOvChipkaartProducten.setString(3, "gekocht");
                saveOvChipkaartProducten.setDate(4, Date.valueOf(LocalDate.now()));
                saveOvChipkaartProducten.execute();
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE product SET naam=?, beschrijving=?, prijs=? WHERE product_nummer=?");
            preparedStatement.setString(1, product.getNaam());
            preparedStatement.setString(2, product.getBeschrijving());
            preparedStatement.setDouble(3, product.getPrijs());
            preparedStatement.setInt(4, product.getProduct_nummer());
            preparedStatement.execute();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        System.out.println(product);
        try {
            PreparedStatement removeProductFromOvChipkaart = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer=?");
            removeProductFromOvChipkaart.setInt(1, product.getProduct_nummer());
            removeProductFromOvChipkaart.execute();

            PreparedStatement removeProduct = conn.prepareStatement("DELETE FROM product WHERE product_nummer=?");
            removeProduct.setInt(1, product.getProduct_nummer());
            System.out.println(removeProduct);
            removeProduct.execute();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public List<Product> findall() {
        List<Product> producten = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM product");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int product_nummer = resultSet.getInt("product_nummer");
                String product_naam = resultSet.getString("naam");
                String product_beschrijving = resultSet.getString("beschrijving");
                Double product_prijs = resultSet.getDouble("prijs");
                Product p1 = new Product(product_nummer, product_naam, product_beschrijving, product_prijs);
                producten.add(p1);
            }
            return producten;
        } catch(Exception e) {
            return null;
        }
    }

    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        List<Product> producten = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT product.product_nummer, naam, beschrijving, prijs FROM ov_chipkaart_product" +
                    " JOIN product ON ov_chipkaart_product.product_nummer = product.product_nummer WHERE ov_chipkaart_product.kaart_nummer = ?");
            preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int product_nummer = resultSet.getInt("product_nummer");
                String product_naam = resultSet.getString("naam");
                String product_beschrijving = resultSet.getString("beschrijving");
                Double product_prijs = resultSet.getDouble("prijs");
                Product p1 = new Product(product_nummer, product_naam, product_beschrijving, product_prijs);
                producten.add(p1);
            }
            return producten;
        } catch(Exception e) {
            return null;
        }
    }
}
