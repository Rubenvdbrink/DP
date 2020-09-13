package P5;


import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart);
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    List<OVChipkaart> findall();
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
}
