import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

public class Serveur {
    public static boolean existe=false;
    private ServerSocket serveursocket;
    public static ArrayList<Client> listeDeJoueur=new ArrayList<>();
    public int indice;

    public Serveur(ServerSocket serveursocket) {
        this.serveursocket = serveursocket;
        existe=true;
      //  List<PointColor> foodPoints = generateFoodPoints(10); // Génère 10 morceaux de nourriture avec couleurs aléatoires
      //  SwingUtilities.invokeLater(() -> new SnakeFood(foodPoints));
    }

    public void startServeur() {
        try {
            while (!serveursocket.isClosed()) {
                Socket socket = serveursocket.accept();

                System.out.println("Un nouveau client s'est identifié!");
                
                ClientHandler clienthandler = new ClientHandler(socket);
                Thread thread = new Thread(clienthandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            if (serveursocket != null) {
                serveursocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /*  private List<PointColor> generateFoodPoints(int count) {
        List<PointColor> foodPoints = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int x = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width);
            int y = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().height);
            Color color = generateRandomLightColor(random);
            foodPoints.add(new PointColor(new Point(x, y), color));
        }

        return foodPoints;
    }

    private Color generateRandomLightColor(Random random) {
        int r = random.nextInt(150) + 100;
        int g = random.nextInt(150) + 100;
        int b = random.nextInt(150) + 100;
        return new Color(r, g, b);
    }*/

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Serveur serveur = new Serveur(serverSocket);
            serveur.startServeur();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}