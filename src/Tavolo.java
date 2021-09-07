import java.io.Serializable;
import java.util.ArrayList;

public class Tavolo implements Serializable {

    private int ID;
    private int coperti;
    private int conto;
    private ArrayList<Piatto> listaCons = new ArrayList<>();


    public Tavolo(int ID, int coperti){
        this.ID = ID;
        this.coperti = coperti;
    }

    public ArrayList<Piatto> getListaCons() {
        return listaCons;
    }

    public void insCons(Piatto pi){
        conto=conto+pi.getPrezzo();
        listaCons.add(pi);
    }

    public int calcolaConto(){
        return conto;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCoperti() {
        return coperti;
    }

    public void setCoperti(int coperti) {
        this.coperti = coperti;
    }

    public int getConto() {
        return conto;
    }

    public void setConto(int conto) {
        this.conto = conto;
    }


    @Override
    public String toString() {
        return "Tavolo (" +
                "ID:" + ID +
                ", coperti:" + coperti +
                ", conto:" + conto +
                ')';
    }
}
