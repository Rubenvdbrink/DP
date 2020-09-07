package P4;

import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres);

    boolean update(Adres adres);

    boolean delete(Reiziger reiziger);

    Adres findByReiziger(Reiziger reiziger);

    List<Adres> findAll();
}
