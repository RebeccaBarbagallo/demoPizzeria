import java.util.ArrayList;
import java.util.Date;

public class TavoloList {

    String last_modification;
    private ArrayList<Tavolo> list;

    public TavoloList(){
        list = new ArrayList<>();
    }

    public synchronized void add(Tavolo t){
        last_modification = new Date().toString();
        list.add(t);
    }

    public synchronized String remove(Tavolo t){
        last_modification = new Date().toString();
        String responso = null;
        for (Tavolo ta : list){
            if( (ta.getID()==t.getID()) ){
            list.remove(ta);
            responso = "REMOVE_OK";
            return responso;
            }
        }
        System.out.println("Tavolo non presente");
        responso = "REMOVE_ERROR";
        return responso;
    }

    public ArrayList<Tavolo> getListCopy(){
        ArrayList<Tavolo> a_list = new ArrayList<>();
        a_list.addAll(list);
        return a_list;
    }

    @Override
    public String toString() {
        String s;
        s = "BEGIN_LIST";
        s = s + "MOD_DATE: " + last_modification;
        for (Tavolo t : list){
            s = s + "ID: "+ t.getID();
            s = s + "Coperti: " + t.getCoperti();
            s = s + "Conto: " + t.getConto();
        }
        s = s + "END_LIST";
        return s;
    }
}
