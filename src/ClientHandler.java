import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers=new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    public ClientHandler(Socket socket){
        try {
            this.socket=socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUsername=bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Serveur: "+clientUsername+" a rejoint le CHAT!");
        } catch (IOException e) {
            // TODO: handle exception
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    @Override
    public void run(){
String messageFromClient;
while(socket.isConnected()){
    try {
        messageFromClient=bufferedReader.readLine();
        broadcastMessage(messageFromClient);
    } catch (IOException e) {
        // TODO: handle exception
        closeEverything(socket,bufferedReader,bufferedWriter);
    }
}
    }
    public void broadcastMessage(String messageAEnvoyer){
        for(ClientHandler clientHandler:clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageAEnvoyer);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch(IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }
    public void removedClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage(clientUsername+" a quitté le JEU!");
    }
    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter)
    {
        removedClientHandler();
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

}