import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    ServerSocket socket;
    Socket client_socket;
    private int port;
    int client_id = 0;

    UtenteList Ulist = new UtenteList();
    TavoloList TList = new TavoloList();
    PiattoList PList = new PiattoList();
    Menu MList = new Menu();

    public static void main(String args[]){

        if (args.length!=1){
            System.out.println("Usage java MyServer <port>");
            return;
        }

        MyServer server = new MyServer(Integer.parseInt(args[0]));
        server.start();
    }

    public MyServer(int port){
        System.out.println("Initializing server with port " + port);
        this.port=port;
    }

    public void start(){
        try {
            System.out.println("Starting Server on port " + port);
            socket = new ServerSocket(port);
            System.out.println("Started Server on port " + port);
            while (true){
                System.out.println("Listening on port " + port);
                client_socket = socket.accept();
                System.out.println("Accepted connection from " + client_socket.getRemoteSocketAddress());

                ClientManager cm = new ClientManager(client_socket,Ulist,TList,PList,MList);
                Thread t = new Thread(cm, "client: " + client_id);
                client_id++;
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Non può partire il Server sulla porta " + port);
            e.printStackTrace();

        }
    }
}

