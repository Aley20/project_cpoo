import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) throws Exception{
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.socket = socket;
            this.username = username;

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void sendMessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner =new Scanner(System.in);
            while(socket.isConnected()){
                String message=scanner.nextLine();
                bufferedWriter.write(username+": "+ message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch (IOException e) {
            // TODO: handle exception*
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter)
    {
        try {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {  
                String msgGrpChat;
                while (socket.isConnected()) {
                    try {
                        msgGrpChat = bufferedReader.readLine();
                        System.out.println(msgGrpChat);
                    } catch (IOException e) {
                        // TODO: handle exception
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
    
    public static void main(String[] args) {
      /*     try {
          
          //  new PrincipalMenu().frame.setLocationRelativeTo(null);
        } catch (Exception e) {
            
            e.printStackTrace();
            System.err.println("EXCEPTION DETECTEE");
        }*/
        Scanner scanner = new Scanner(System.in);
        System.err.println("Entrez votre NOM: ");
        String username = scanner.nextLine();
        try {
            // Attempt to connect to the server
            Socket socket = new Socket("localhost", 1234);
            try {
                 Client client = new Client(socket, username);
            client.listenForMessage();
            client.sendMessage();
            } catch (Exception e) {
                // TODO: handle exception
                e.getStackTrace();
            }
           
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
}