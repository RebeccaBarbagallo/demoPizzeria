import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Amministratore {

    public void menu(Socket socket) throws IOException {
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        //dal server
        Scanner server_scanner = new Scanner(socket.getInputStream());
        //dall'utente
        Scanner user_scanner = new Scanner(System.in);

        String msg_to_send;
        String msg_received;

        boolean goboss = true;
        int choice;
        while (goboss){
            System.out.println("-------------------------");
            System.out.println("0 - Aggiungi dipendente (ADD_UTENTE)");
            System.out.println("1 - Rimuovi dipendente (REMOVE_UTENTE) ");
            System.out.println("2 - Mostra lista dipendenti (LIST_UTENTI)");
            System.out.println("3 - Salva dipendenti (SAVE)");
            System.out.println("4 - Carica dipendenti (CARICA)");
            System.out.println("5 - QUIT");
            System.out.println("-------------------------");
            System.out.println("Inserisci la tua scelta:");
            choice = user_scanner.nextInt();

            switch (choice){
                case 0:
                    //0 - Aggiungi utente (ADD_UTENTE)
                    System.out.println("Inserisci CF: ");
                    String cf = user_scanner.next();
                    cf = cf.toUpperCase();
                    System.out.println("Inserisci nome: ");
                    String nome = user_scanner.next();
                    System.out.println("Inserisci cognome: ");
                    String cognome = user_scanner.next();
                    System.out.println("Inserisci mansione (PIZZAIOLO/CAMERIERE): ");
                    String mansione = user_scanner.next();
                    mansione = mansione.toUpperCase();

                    msg_to_send = "ADD_UTENTE "  + cf + " " + nome + " " + cognome + " " + mansione;
                    System.out.println("Debug: e' stato inviato " + msg_to_send);
                    pw.println(msg_to_send);
                    pw.flush();
                    msg_received = server_scanner.nextLine();
                    if (msg_received.equals("ADD_OK")){
                        System.out.println("Utente aggiunto correttamente");
                    } else if (msg_received.equals("ADD_ERROR")){
                        System.out.println("Errore: utente non aggiunto");
                    } else{
                        System.out.println("Errore: Messaggio sconosciuto-> " + msg_received);
                    }
                    break;

                case 1:
                    //1 - Rimuovi utente (REMOVE_UTENTE)
                    System.out.println("Inserisci CF utente da eliminare: ");
                    String cfr = user_scanner.next();
                    cfr = cfr.toUpperCase();
                    msg_to_send = "REMOVE_UTENTE " + cfr;
                    System.out.println("Inviato " + msg_to_send);
                    pw.println(msg_to_send);
                    pw.flush();
                    msg_received = server_scanner.nextLine();
                    if(msg_received.equals("REMOVE_OK")){
                        System.out.println("Utente eliminato con successo");
                    }else if (msg_received.equals("REMOVE_ERROR")){
                        System.out.println("Errore, impossibile eliminare utente");
                    }else {
                        System.out.println("Errore: messaggio sconosciuto-> " + msg_received);
                    }
                    break;

                case 2:
                    //2 - Mostra utenti (LIST_UTENTI)
                    msg_to_send = "LIST_UTENTI";
                    pw.println(msg_to_send);
                    pw.flush();

                    msg_received = server_scanner.nextLine();
                    boolean listening = true;
                    if (msg_received.equals("BEGIN")){
                        System.out.println("Ricezione lista..");
                        while (listening){
                            msg_received = server_scanner.nextLine();
                            if (msg_received.equals("END")){
                                listening=false;
                                System.out.println("Fine lista");
                            }else {
                                System.out.println(msg_received);
                            }
                        }
                    }else{
                        System.out.println("Risposta sconosciuta: " + msg_received);
                    }
                    break;

                case 3:
                    //3 - Salva utente (SAVE)
                    pw.println("SAVE");
                    pw.flush();
                    msg_received = server_scanner.nextLine();
                    if (msg_received.equals("SAVE_OK")){
                        System.out.println("File salvato correttamente");
                    }else if (msg_received.equals("SAVE_ERROR")){
                        System.out.println("Errore salvataggio file");
                    }else{
                        System.out.println("Messaggio sconosciuto: "+ msg_received);
                    }
                    break;

                case 4:
                    //4 - Carica utenti (CARICA)
                    pw.println("CARICA");
                    pw.flush();
                    break;

                case 5:
                    goboss = false;
                    System.out.println("Torna indietro");
                    break;
            }
        }
    }
}
