import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class IaJoueur extends JPanel implements KeyListener {
    private Serpent joueur1;
    private Serpent2 joueur2; // Assurez-vous que Serpent2 a une logique IA propre
    private Timer timer;
    private boolean enPause = false;

    public IaJoueur() {
        joueur1 = new Serpent();
        joueur2 = new Serpent2();

        this.setPreferredSize(new Dimension(joueur1.getLargeur(), joueur1.getLongueur()));
        this.setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        // Initialisez le timer pour le mouvement régulier des joueurs
        this.timer = new Timer(joueur1.getVitesse(), e -> {
            if (!enPause) {
                joueur1.actionPerformed(e);
                joueur2.actionPerformed(e); // Assurez-vous que cette méthode est adaptée à une IA
                checkGameOver();
                this.repaint();
            }
        });
        this.timer.start();
    }
    public Serpent getJoueur1(){
        return this.joueur1;
    }
    public Serpent2 getJoueur2(){
        return this.joueur2;
    }

    private void checkGameOver() {
        if (joueur1.getgameOver() || joueur2.getgameOver()) {
            timer.stop();
            // Afficher un message de fin de jeu ou gérer la fin de jeu
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            enPause = !enPause;
            if (enPause) {
                timer.stop();
            } else {
                timer.restart();
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else {
            joueur1.keyPressed(e); // Assurez-vous que cette méthode gère bien les touches pour le joueur
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        joueur1.draw(graphics, Color.BLUE); // Dessin du joueur
        joueur2.draw(graphics, Color.ORANGE); // Dessin de l'IA
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implémenter si nécessaire
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Implémenter si nécessaire
    }
}
