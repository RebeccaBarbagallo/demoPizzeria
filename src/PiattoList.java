import java.util.ArrayList;
import java.util.Date;

public class PiattoList {

    String last_modification;
    private ArrayList<Piatto> list;

    public PiattoList() {
       list = new ArrayList<>();
    }

    public synchronized void add(Piatto p){
        last_modification = new Date().toString();
        list.add(p);
    }

    public synchronized String remove(String nome){
        last_modification = new Date().toString();
        String responso = null;
        for ( Piatto pi: list){
            if( (pi.getNome().equals(nome)))
            {
                list.remove(pi);
                responso = "REMOVE_OK";
                return responso;
            }
        }
        System.out.println("Piatto non presente in lista");
        responso = "REMOVE_ERROR";
        return responso;
    }

    public ArrayList<Piatto> getListCopy(){
        ArrayList<Piatto> a_list = new ArrayList<>();
        a_list.addAll(list);
        return a_list;
    }

    @Override
    public String toString() {
        String s;
        s = "BEGIN_LIST";
        s = s + "MOD_DATE: " + last_modification;
        for (Piatto p : list){
            s = s + "Nome: " + p.getNome();
            s = s + "Prezzo: " + p.getPrezzo();
        }
        s = s + "END_LIST";
        return s;
    }
}
