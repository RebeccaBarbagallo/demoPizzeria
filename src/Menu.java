
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Menu {

    String last_modification;
    private ArrayList<Piatto> list;

    public Menu(){
        list = new ArrayList<>();
    }

    public synchronized void add(Piatto p){
        last_modification = new Date().toString();
        list.add(p);
        saveMenu(list);
    }

    public synchronized String remove(String nome){
        last_modification = new Date().toString();
        String responso = null;
        for (Piatto pi : list){
            if ((pi.getNome().equals(nome))){
                list.remove(pi);
                saveMenu(list);
                responso = "REMOVE_OK";
                return responso;
            }
        }
        System.out.println("Piatto non presente nel Menu'");
        responso = "REMOVE_ERROR";
        return responso;
    }

    public ArrayList<Piatto> showMenu(){
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream("menu.ser"));
            list = (ArrayList<Piatto>) is.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveMenu(ArrayList<Piatto> list) {
        try {
            var oos = new ObjectOutputStream(new FileOutputStream("menu.ser"));
            oos.writeObject(list);
            oos.close();
            System.out.println("Lista salvata correttamente");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Save_Error");
            e.printStackTrace();
        }
    }
}
