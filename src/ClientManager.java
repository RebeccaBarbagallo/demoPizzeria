import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientManager implements Runnable {

    private Socket client_socket;
    private UtenteList lutente;
    private TavoloList ltavolo;
    private PiattoList lpiatto;
    private Menu menu;

    public ClientManager(Socket client_socket, UtenteList lutente, TavoloList ltavolo, PiattoList lpiatto, Menu menu){
        this.client_socket = client_socket;
        this.lutente = lutente;
        this.ltavolo = ltavolo;
        this.lpiatto = lpiatto;
        this.menu = menu;
     }

    @Override
    public void run() {
        String tid = Thread.currentThread().getName();
        System.out.println(tid + " -> Accepted connection from " + client_socket.getRemoteSocketAddress());

        Scanner client_scanner = null;
        PrintWriter pw = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        try {
            client_scanner = new Scanner(client_socket.getInputStream());
            pw = new PrintWriter(client_socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream("ut.ser"));
            lutente = (UtenteList) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean go = true;
        while (go){
            String message = client_scanner.nextLine();
            System.out.println("Server: Ricevuto " + message);
            Scanner msg_scanner = new Scanner(message);

            String cmd = msg_scanner.next();
            System.out.println("Received Command: " + cmd);
            if (cmd.equals("ADD_UTENTE")){
                String cf = msg_scanner.next();
                cf.toUpperCase();
                String nome = msg_scanner.next();
                String cognome = msg_scanner.next();
                String mansione = msg_scanner.next();
                mansione.toUpperCase();
                Utente u = new Utente(cf,nome,cognome,mansione);

                lutente.add(u);
                System.out.println("Server: Aggiunto " + u);

                pw.println("ADD_OK");
                pw.flush();
            }
            else if (cmd.equals("REMOVE_UTENTE")){
                String cf = msg_scanner.next();
                String responso = lutente.remove(cf);
                pw.println(responso);
                pw.flush();
            }
            else if (cmd.equals("LIST_UTENTI")){
                pw.println("BEGIN");
                pw.flush();
                ArrayList<Utente> tmp;
                tmp = lutente.getListCopy();
                for (Utente u: tmp){
                    pw.println(u);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }
            else if (cmd.equals("SAVE")){
                try {
                    var oos = new ObjectOutputStream(new FileOutputStream("ut.ser"));
                    oos.writeObject(lutente);
                    oos.close();
                    pw.println("SAVE_OK");
                    pw.flush();
                    System.out.println("Server: lista salvata correttamente");
                } catch (IOException e) {
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }
            }
            else if (cmd.equals("CARICA")){
                try {
                    is = new ObjectInputStream(new FileInputStream("ut.ser"));
                    lutente = (UtenteList) is.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (cmd.equals("QUIT")){
                System.out.println("Server: Chiusura connessione con " + client_socket.getRemoteSocketAddress());
                try {
                    client_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                go = false;
            }
            else if (cmd.equals("CASSA")) {
                System.out.println("Chiusura cassa: " + ltavolo.chiusuraCassa());
                pw.println(ltavolo.chiusuraCassa());
                pw.flush();
                File chiusuracassa = new File("chiusuracassa.txt");
                try{
                    //questo sovrascive
                    //PrintWriter p = new PrintWriter("chiusuracassa.txt", "UTF-8");
                    //questo fa linee separate leggibile
                    PrintWriter p = new PrintWriter(new FileWriter(chiusuracassa, true));
                    p.println(dtf.format(LocalDateTime.now()) + " - " + ltavolo.chiusuraCassa() + " euro");
                    p.close();
                }
                catch (IOException e){
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
              }
             else if (cmd.equals("CODICE")){
                ArrayList<Utente> tmp;
                tmp = lutente.getListCopy();
                String cf = msg_scanner.next();

                for (Utente u: tmp){
                    if (cf.equals(u.getCF())){
                        if (u.getMansione().equals("PIZZAIOLO")||u.getMansione().equals("pizzaiolo")){
                            System.out.println("Mansione: " + u.getMansione());
                            pw.println("PIZZAIOLO");
                            pw.flush();
                            break;
                        }else if (u.getMansione().equals("CAMERIERE")||u.getMansione().equals("cameriere")){
                            System.out.println("Mansione: " + u.getMansione());
                            pw.println("CAMERIERE");
                            pw.flush();
                            break;
                        }
                    }
                }
            }
            else if (cmd.equals("ADD_PIATTO")){
                String nome = msg_scanner.next();
                int prezzo = msg_scanner.nextInt();

                Piatto pia = new Piatto(nome,prezzo);

                menu.add(pia);
                System.out.println("Server: Aggiunto " + pia);

                pw.println("ADD_OK");
                pw.flush();
            }
            else if (cmd.equals("REMOVE_PIATTO")){
                String nome = msg_scanner.next();
                String responso = menu.remove(nome);
                pw.println(responso);
                pw.flush();
            }
            else if (cmd.equals("REMOVE_TAVOLO")){
                int id = msg_scanner.nextInt();
                String responso = null;
                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();
                for (Tavolo ta: tmp){
                    if (id == ta.getID()){
                        responso = ltavolo.remove(ta);
                    }
                }
                pw.println(responso);
                pw.flush();
            }
            else if (cmd.equals("LIST_MENU")){
                pw.println("BEGIN");
                pw.flush();
                ArrayList<Piatto> tmp;
                tmp = menu.showMenu();
                for (Piatto p: tmp){
                    pw.println(p);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
        }
            else if (cmd.equals("LIST_TAVOLI")){
                pw.println("BEGIN");
                pw.flush();
                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();
                for (Tavolo u: tmp){
                    pw.println(u);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }
            else if (cmd.equals("SHOW_COMANDA")){
                int id = msg_scanner.nextInt();
                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();
                ArrayList<Piatto> tmpp;
                for (Tavolo ta: tmp){
                    if(id==ta.getID()){
                        tmpp=ta.getListaCons();
                        pw.println("BEGIN");
                        pw.flush();

                        for (Piatto p: tmpp){
                            pw.println(p);
                            pw.flush();
                        }
                        pw.println("END");
                        pw.flush();
                        break;
                    }
                }
                pw.println("TAVOLO_ERROR");
                pw.flush();
            }
            else if (cmd.equals("TAVOLO")){
                int id = msg_scanner.nextInt();
                int coperti = msg_scanner.nextInt();

                Tavolo ta = new Tavolo(id,coperti);
                ltavolo.add(ta);
                pw.println("ADD_OK");
                pw.flush();
            }
            else if (cmd.equals("ADDPIATTOTAVOLO")){
                int id = msg_scanner.nextInt();
                String nome = msg_scanner.next();
                int prezzo = 0;

                ArrayList<Piatto> tmpP;
                tmpP=menu.showMenu();

                for ( Piatto pi: tmpP){
                    if ((pi.getNome().equals(nome))){
                        prezzo = pi.getPrezzo();
                    }
                }

                Piatto pia = new Piatto(nome,prezzo);

                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();

                for (Tavolo ta: tmp){
                    if (id==ta.getID()){
                        ta.insCons(pia);
                        pw.println("ADD_OK");
                        pw.flush();
                    }
                }

            }
            else if (cmd.equals("CONTO")){
                ArrayList<Tavolo> tmp;
                tmp = ltavolo.getListCopy();
                int id = msg_scanner.nextInt();
                int conto;
                for (Tavolo ta: tmp){
                            if(id==ta.getID()){
                                conto=ta.getConto();
                                pw.println(conto);
                                pw.flush();
                    }
                }
            }
        }
    }
}
