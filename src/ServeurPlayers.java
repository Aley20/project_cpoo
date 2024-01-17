import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
   import java.awt.Color;
    import java.util.Random;
    
public class ServeurPlayers extends JPanel implements KeyListener {

    private Serpent joueur1;
    private Serpent joueur2;
    ArrayList  <Serpent>joueurs=new ArrayList<>();
    private Timer timer;

    public ServeurPlayers() {
      //  joueur1 = new Serpent();
        //joueur2 = new Serpent();

        this.setPreferredSize(new Dimension(joueurs.get(0).getLargeur(), joueurs.get(0).getLongueur()));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        // Initialisez le timer pour le mouvement régulier des joueurs
        this.timer = new Timer(joueurs.get(0).getVitesse(), e -> {
            for (int i = 0; i < joueurs.size(); i++) {
                joueurs.get(i).actionPerformed(null);
            }
            //joueur1.actionPerformed(null);
            //joueur2.actionPerformed(null);
            this.repaint();
        });
        this.timer.start();
    }
 
    public static Color getRandomColor() {
        Random random = new Random();
        // Générer des valeurs aléatoires pour rouge, vert et bleu
        int red = random.nextInt(256); // 0-255
        int green = random.nextInt(256); // 0-255
        int blue = random.nextInt(256); // 0-255
    
        // Créer une nouvelle couleur avec les valeurs RGB aléatoires
        return new Color(red, green, blue);
    }
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (int i = 0; i < joueurs.size(); i++) {
                joueurs.get(i).draw(graphics, getRandomColor());
            }
    }
    public void reinitialise() {
  for (int i = 0; i < joueurs.size(); i++) {
joueurs.get(i).reinitialise();
}
    }
    @Override
    public void keyPressed(KeyEvent e) {
        // Gérez les touches pour chaque joueur
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
             for (int i = 0; i < joueurs.size(); i++) {
                joueurs.get(i).keyPressed(e);
            }
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ne rien faire ici
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Ne rien faire ici
    }
}

