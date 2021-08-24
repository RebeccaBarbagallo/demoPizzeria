import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class UtenteList implements Serializable {

    String archive_id;
    String last_modification;
    private ArrayList<Utente> list;

    public UtenteList(){
        list = new ArrayList<>();
    }

    public synchronized void add(Utente p){
        last_modification = new Date().toString();
        list.add(p);
    }

    public synchronized String remove(String cf){
        last_modification = new Date().toString();
        String responso = null;
        for (Utente ut : list){
            if ((ut.getCF().equals(cf))){
                list.remove(ut);
                responso = "REMOVE_OK";
                return responso;
            }
        }
        System.out.println("Utente non presente in lista");
        responso = "REMOVE_ERROR";
        return responso;
    }

    public ArrayList<Utente> getListCopy(){
        ArrayList<Utente> a_list = new ArrayList<>();
        a_list.addAll(list);
        return a_list;
    }

    @Override
    public String toString() {
        String s;
        s = "BEGIN_LIST";
        s = s + "MOD_DATE:" +  last_modification;
        for (Utente u :  list){
            s = s + "CF: " + u.getCF();
            s = s + "Nome: " + u.getNome();
            s = s + "Cognome: " + u.getCognome();
            s = s + "Mansione: " + u.getMansione();
        }
        s = s + "END_LIST";
        return s;
    }
}
