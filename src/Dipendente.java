import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Dipendente {

    public void menu(Socket socket) throws IOException {
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        //dal server
        Scanner server_scanner = new Scanner(socket.getInputStream());
        //dall'utente
        Scanner user_scanner = new Scanner(System.in);

        String msg_to_send;
        String msg_received;

        boolean godip = true;
        int choice;

        while (godip) {

            System.out.println("---------------------------");
            System.out.println("0 - LOGIN");
            System.out.println("1 - EXIT");
            System.out.println("---------------------------");
            System.out.println("Inserisci la tua scelta: ");
            choice = user_scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Inserisci CF: ");
                    String cf = user_scanner.next();
                    cf = cf.toUpperCase();
                    msg_to_send = "CODICE " + cf;
                    System.out.println("Debug: Inviato " + msg_to_send);
                    pw.println(msg_to_send);
                    pw.flush();

                    msg_received = server_scanner.nextLine();

                    if (msg_received.equals("PIZZAIOLO")) {
                        boolean gopizzaiolo = true;
                        while (gopizzaiolo) {
                            System.out.println("---------------------------");
                            System.out.println("0 - Aggiungi Pietanza (ADD_PIATTO)");
                            System.out.println("1 - Rimuovi Pietanza (REMOVE_PIATTO)");
                            System.out.println("2 - Mostra Menu' (LIST_MENU)");
                            System.out.println("3 - Mostra Tavoli (LIST_TAVOLI)");
                            System.out.println("4 - Mostra Comanda (SHOW COMANDA)");
                            System.out.println("5 - QUIT");
                            System.out.println("---------------------------");
                            System.out.println("Inserisci la tua scelta: ");
                            choice = user_scanner.nextInt();

                            switch (choice) {

                                case 0:
                                    //0 - Aggiungi Pietanza (ADD_PIATTO)
                                    System.out.println("Inserisci nome piatto: ");
                                    String nome = user_scanner.next();
                                    nome=nome.toUpperCase();
                                    System.out.println("Inserisci prezzo: ");
                                    int prezzo = user_scanner.nextInt();

                                    msg_to_send = "ADD_PIATTO " + nome + " " + prezzo;
                                    System.out.println("Debug: Inviato " + msg_to_send);
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    if (msg_received.equals("ADD_OK")) {
                                        System.out.println("Piatto aggiunto con successo al menu'");
                                    } else if (msg_received.equals("ADD_ERROR")) {
                                        System.out.println("Errore, piatto non aggiunto al menu'");
                                    } else {
                                        System.out.println("Errore, messaggio sconosciuto-> " + msg_received);
                                    }
                                    break;

                                case 1:
                                    //1 - Rimuovi Pietanza (REMOVE_PIATTO)
                                    System.out.println("Inserisci il nome della pietanza da eliminare:");
                                    String nomer = user_scanner.next();
                                    nomer = nomer.toUpperCase();
                                    msg_to_send = "REMOVE_PIATTO " + nomer;
                                    System.out.println("Debug: Inviato " + msg_to_send);
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();

                                    if (msg_received.equals("REMOVE_OK")) {
                                        System.out.println("Piatto eliminato con successo");
                                    } else if (msg_received.equals("REMOVE_ERROR")) {
                                        System.out.println("Errore, piatto non eliminato");
                                    } else {
                                        System.out.println("Errore, messaggio sconosciuto-> " + msg_received);
                                    }
                                    break;

                                case 2:
                                    //2 - Mostra Menù (LIST_MENU)
                                    msg_to_send = "LIST_MENU";
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    boolean listen = true;
                                    if (msg_received.equals("BEGIN")) {
                                        System.out.println("Ricezione lista..");
                                        while (listen) {
                                            msg_received = server_scanner.nextLine();
                                            if (msg_received.equals("END")) {
                                                listen = false;
                                                System.out.println("Fine lista");
                                            } else {
                                                System.out.println(msg_received);
                                            }
                                        }
                                    } else {
                                        System.out.println("Risposta sconoscita: " + msg_received);
                                    }
                                    break;

                                case 3:
                                    //3 - Mostra Tavoli (LIST_TAVOLI)
                                    msg_to_send = "LIST_TAVOLI";
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    boolean listeni = true;
                                    if (msg_received.equals("BEGIN")) {
                                        System.out.println("Ricezione lista..");
                                        while (listeni) {
                                            msg_received = server_scanner.nextLine();
                                            if (msg_received.equals("END")) {
                                                listeni = false;
                                                System.out.println("Fine lista");
                                            } else {
                                                System.out.println(msg_received);
                                            }
                                        }
                                    } else {
                                        System.out.println("Risposta sconosciuta: " + msg_received);
                                    }
                                    break;

                                case 4:
                                    //4 - Mostra Comanda (SHOW COMANDA)
                                    System.out.println("Inserisci ID tavolo: ");
                                    int idt = user_scanner.nextInt();
                                    msg_to_send = "SHOW_COMANDA " + idt;

                                    System.out.println("Debug: Inviato " + msg_to_send);
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    boolean listenin = true;
                                    if (msg_received.equals("BEGIN")) {
                                        System.out.println("Ricezione lista..");
                                        while (listenin) {
                                            msg_received = server_scanner.nextLine();
                                            if (msg_received.equals("END")) {
                                                listenin = false;
                                                System.out.println("Fine lista");
                                            } else {
                                                System.out.println(msg_received);
                                            }
                                        }
                                    } else if (msg_received.equals("TAVOLO_ERROR")) {
                                        System.out.println("Errore, tavolo non presente");
                                    } else {
                                        System.out.println("Errore, messaggio sconosciuto: " + msg_received);
                                    }
                                    break;

                                case 5:
                                    //QUIT
                                    gopizzaiolo = false;
                                    System.out.println("Torna indietro");
                                    break;
                            }
                        }
                    } else if (msg_received.equals("CAMERIERE")) {
                        boolean gocameriere = true;
                        while (gocameriere) {
                            System.out.println("---------------------------");
                            System.out.println("0 - Crea Tavolo (TAVOLO)");
                            System.out.println("1 - Aggiungi Pietanza al Tavolo (ADDPIATTOTAVOLO)");
                            System.out.println("2 - Conto (CONTO)");
                            System.out.println("3 - Mostra Menu' (LIST_MENU)");
                            System.out.println("4 - Mostra Comanda (SHOW_COMANDA)");
                            System.out.println("5 - Elimina Tavolo (REMOVE_TAVOLO)");
                            System.out.println("6 - QUIT");
                            System.out.println("---------------------------");
                            System.out.println("Inserisci la tua scelta:");

                            choice = user_scanner.nextInt();
                            switch (choice) {
                                case 0:
                                    //0 - Crea Tavolo (TAVOLO)
                                    System.out.println("Inserisci ID Tavolo: ");
                                    int id = user_scanner.nextInt();
                                    System.out.println("Inserisci Numero Coperti: ");
                                    int coperti = user_scanner.nextInt();
                                    msg_to_send = "TAVOLO " + id + " " + coperti;
                                    System.out.println("Debug: Inviato " + msg_to_send);
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    if (msg_received.equals("ADD_OK")) {
                                        System.out.println("Tavolo Aggiunto con successo");
                                    } else if (msg_received.equals("ADD_ERROR")) {
                                        System.out.println("Errore, tavolo non aggiunto");
                                    } else {
                                        System.out.println("Errore, messaggio sconosciuto-> " + msg_received);
                                    }
                                    break;

                                case 1:
                                    //1 - Aggiungi Pietanza al Tavolo (ADDPIATTOTAVOLO)
                                    System.out.println("Inserisci ID Tavolo ");
                                    int idt = user_scanner.nextInt();
                                    System.out.println("Inserisci Nome Piatto: ");
                                    String nome = user_scanner.next();
                                    nome = nome.toUpperCase();

                                    msg_to_send = "ADDPIATTOTAVOLO " + idt + " " + nome;

                                    System.out.println("DEBUG: Inviato " + msg_to_send);
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    if (msg_received.equals("ADD_OK")) {
                                        System.out.println("Pietanza aggiunta al tavolo con successo");
                                    } else if (msg_received.equals("ADD_ERROR")) {
                                        System.out.println("Errore, piatto non aggiunta");
                                    } else {
                                        System.out.println("Errore, messaggio sconosciuto-> " + msg_received);
                                    }
                                    break;

                                case 2:
                                    //Conto (CONTO)
                                    System.out.println("Inserire ID del tavolo: ");
                                    int idT = user_scanner.nextInt();
                                    msg_to_send = "CONTO " + idT;
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    System.out.println("Il conto del tavolo "+idT+" e': "+ msg_received);
                                    break;

                                case 3:
                                    //"3 - Mostra Menù (LIST_MENU)
                                    msg_to_send = "LIST_MENU";
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    boolean listening = true;
                                    if (msg_received.equals("BEGIN")) {
                                        System.out.println("Ricezione lista..");
                                        while (listening) {
                                            msg_received = server_scanner.nextLine();
                                            if (msg_received.equals("END")) {
                                                listening = false;
                                                System.out.println("Fine lista");
                                            } else {
                                                System.out.println(msg_received);
                                            }
                                        }
                                    } else {
                                        System.out.println("Risposta sconosciuta: " + msg_received);
                                    }
                                    break;

                                case 4:
                                    //4 - Mostra Comanda (SHOW_COMANDA)
                                    System.out.println("Inserisci ID Tavolo ");
                                    int idtt = user_scanner.nextInt();
                                    msg_to_send = "SHOW_COMANDA " + idtt;

                                    System.out.println("DEBUG: Inviato " + msg_to_send);
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();
                                    boolean listeningc = true;
                                    if (msg_received.equals("BEGIN")) {
                                        System.out.println("Ricezione lista...");
                                        while (listeningc) {
                                            msg_received = server_scanner.nextLine();
                                            if (msg_received.equals("END")) {
                                                listeningc = false;
                                                System.out.println("Fine lista");
                                            } else {
                                                System.out.println(msg_received);
                                            }
                                        }
                                    } else if (msg_received.equals("TAVOLO_ERROR")) {
                                        System.out.println("Errore, tavolo non presente");
                                    } else {
                                        System.out.println("Errore, messaggio sconosciuto-> " + msg_received);
                                    }
                                    break;

                                case 5:
                                    //5 - Elimina Tavolo (REMOVE_TAVOLO)
                                    System.out.println("Inserire ID del tavolo da eliminare: ");
                                    int idTa = user_scanner.nextInt();
                                    msg_to_send = "REMOVE_TAVOLO " + idTa;
                                    pw.println(msg_to_send);
                                    pw.flush();

                                    msg_received = server_scanner.nextLine();

                                    if (msg_received.equals("REMOVE_OK")) {
                                        System.out.println("Tavolo Eliminato!");
                                    } else if (msg_received.equals("REMOVE_ERROR")) {
                                        System.out.println("Errore!!!");
                                    } else {
                                        System.out.println("Errore, messaggio sconosciuto-> " + msg_received);
                                    }
                                    break;

                                case 6:
                                    //6 - QUIT
                                    gocameriere = false;
                                    System.out.println("Torna Indietro");
                                    break;
                            }
                        }
                    }
                    break;
                case 1:
                    godip = false;
                    System.out.println("Torna indietro");

            }
        }
    }
}
