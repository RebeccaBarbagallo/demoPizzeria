import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClient {

    Socket socket;
    private String address;
    private int port;

       public static void main(String args[]){

        if (args.length != 2){
            System.out.println("Usage java <address> <port>");
            return;
        }
        MyClient client = new MyClient(args[0], Integer.parseInt(args[1]));
        client.start();
    }

    public MyClient(String address, int port) {
           this.address = address;
           this.port = port;
    }

    public void start(){
        System.out.println("Starting client connection to " + address + "," + port);
        try {
            socket = new Socket(address,port);
            System.out.println("Started client connection to "+ address + "," + port );

            //to Server
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            //form server
            Scanner server_scanner = new Scanner(socket.getInputStream());
            //from user
            Scanner user_scanner = new Scanner(System.in);

            String msg_to_send;
            String msg_received;

            boolean go = true;
            int choice;

            while (go){
                System.out.println("-------------------------");
                System.out.println("0 - BOSS");
                System.out.println("1 - DIPENDENTE");
                System.out.println("2 - EXIT");
                System.out.println("Inserisci la tua scelta->");
                choice = user_scanner.nextInt();

                switch (choice){
                    case 0:
                        Amministratore am = new Amministratore();
                        am.menu(socket);
                        break;

                    case 1:
                        Dipendente dip = new Dipendente();
                        dip.menu(socket);
                        break;

                    case 2:
                        go = false;
                        System.out.println("Chiusura client..");
                        msg_to_send = "QUIT";
                        pw.println(msg_to_send);
                        pw.flush();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}