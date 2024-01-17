import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SerpentMultijoueur extends JPanel implements KeyListener {

    private Serpent joueur1;
    private Serpent joueur2;
    private Timer timer;

    public SerpentMultijoueur(Serpent joueur1,Serpent joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;

        this.setPreferredSize(new Dimension(joueur1.getLargeur(), joueur1.getLongueur()));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        // Initialisez le timer pour le mouvement régulier des joueurs
        this.timer = new Timer(joueur1.getVitesse(), e -> {
            joueur1.actionPerformed(null);
            joueur2.actionPerformed(null);
            this.repaint();
        });
        this.timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        joueur1.draw(graphics, Color.BLUE); // Couleur pour le joueur 1
        joueur2.draw(graphics, Color.ORANGE);  // Couleur pour le joueur 2
    }
    public boolean getgameOver() {
        if(joueur1.getgameOver() || joueur2.getgameOver())timer.stop();
      return (joueur1.getgameOver() || joueur2.getgameOver());
    
    }
    public void reinitialise() {
    joueur1.reinitialise();
    joueur2.reinitialise();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        // Gérez les touches pour chaque joueur
        switch (e.getKeyCode()) {
            // Joueur 1
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                joueur1.keyPressed(e);
                break;

            // Joueur 2
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                joueur2.keyPressed(e);
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

