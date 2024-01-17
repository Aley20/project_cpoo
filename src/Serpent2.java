import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Serpent2 extends Serpent implements ActionListener {

	private boolean useAI = true;
	private boolean enPause = false;

	public Serpent2() {
		super();
		super.tempDebut=0;
		addKeyListener(this);
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
            // Basculez l'état de pause
            enPause = !enPause;
            if (enPause) {
                // Arrêter le timer 
				timer.stop();
            } else {
				timer.restart();
                // Redémarrer le timer 
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            // Quitter le jeu ou fermer la fenêtre
            System.exit(0);
        }
	}
// Enable AI control
public void enableAI() {
    this.useAI = true;
    // Stop the timer for player control
    this.direction = aiMove();
    this.move();
    this.verifPomme();
    this.collision();
    this.repaint();
    //aiTimer.start();
}
private Direction aiMove() {
	double appleX = this.pomme.getPommeX();
	double appleY = this.pomme.getPommeY();
	
	int headX = (int)this.x[0];
	int headY = (int)this.y[0];
	
	// Calculate the horizontal and vertical distances to the apple
	double dx = appleX - headX;
	double dy = appleY - headY;
	
	// Check horizontal alignment first
	if (dx != 0) {
		if (dx > 0) {
			this.direction = this.direction.EST;
		} else {
			this.direction = this.direction.OUEST;
		}
	}
	// If horizontally aligned, check vertical alignment
	else if (dy != 0) {
		if (dy > 0) {
			this.direction = this.direction.SUD;
		} else {
			this.direction = this.direction.NORD;
		}
	}
	
	// Move the snake in the calculated direction
	this.move();
	
	// Check for collisions and update the game state
	this.verifPomme();
	this.collision();
	return this.direction;
}
	

	/**
	 * gere le deplacement du serpent en fonction de la direction actuelle
	 */
	@Override
	public void move() {
		// Copiez la position actuelle de la tête du serpent
		int newHeadX = x[0];
		int newHeadY = y[0];

		// Déplacement de la tête du serpent selon la direction
		if (this.direction==this.direction.NORD) {
			newHeadY = (newHeadY - this.taille_tuille + this.longueur) % this.longueur;
		} else if (this.direction==this.direction.EST) {
			newHeadX = (newHeadX + this.taille_tuille) % this.largeur;
		} else if (this.direction==this.direction.OUEST) {
			newHeadX = (newHeadX - this.taille_tuille + this.largeur) % this.largeur;
		} else if (this.direction==this.direction.SUD) {
			newHeadY = (newHeadY + this.taille_tuille) % this.longueur;
		}

		// Mettez à jour la position de la tête du serpent
		x[0] = newHeadX;
		y[0] = newHeadY;

		// Le reste du corps suit la tête du serpent
		for (int i = this.taille_serpent - 1; i > 0; i--) {
			this.x[i] = this.x[i - 1];
			this.y[i] = this.y[i - 1];
		}
	}

	/**
	 * gére le déplacement du jeu
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// AI movement logic
		if (useAI) {
			// Call a method to handle AI movement
			aiMove();
		} else if (this.gameover == false) {
			this.move();
			this.verifPomme();
			this.collision();
		}

		this.repaint();

	}
	@Override
	public void draw(Graphics graphics, Color couleur){
		// Draw poisoned apple
		graphics.setColor(Color.RED);
		graphics.fillOval(pomme.getPoisonX(), pomme.getPoisonY(), taille_tuille, taille_tuille);
		// Draw regular apple
		graphics.setColor(Color.GREEN);
		graphics.fillOval(pomme.getPommeX(), pomme.getPommeY(), taille_tuille, taille_tuille);
		// Draw snake head
		graphics.setColor(Color.WHITE);
		graphics.fillOval(x[0], y[0], taille_tuille, taille_tuille);

		// Draw snake eyes (two circles on the head)
		int eyeSize = taille_tuille / 4;
		graphics.setColor(Color.BLACK);
		graphics.fillOval(x[0] + taille_tuille / 4 - eyeSize / 2, y[0] + taille_tuille / 4 - eyeSize / 2, eyeSize,
				eyeSize);
		graphics.fillOval(x[0] + 3 * taille_tuille / 4 - eyeSize / 2, y[0] + taille_tuille / 4 - eyeSize / 2,
				eyeSize, eyeSize);
		// Draw snake body
		for (int i = 1; i < taille_serpent - 1; i++) {
			graphics.setColor(couleur);
			graphics.fillOval(x[i], y[i], taille_tuille, taille_tuille);
		}
		// Draw snake tail
		graphics.setColor(couleur);
		graphics.fillOval(x[taille_serpent - 1], y[taille_serpent - 1], taille_tuille, taille_tuille);
		// Draw score
		graphics.setColor(Color.YELLOW);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: " + pomme.getPomme(),
				(largeur - metrics.stringWidth("Score: " + pomme.getPomme())) / 2,
				graphics.getFont().getSize());
		graphics.drawString("Appuyer sur (E) pour quitter et (M) pour aller dans le menu",
			(largeur - metrics.stringWidth("Appuyer sur (E) pour quitter et (M) pour aller dans le menu")) / 2,
			this.longueur - graphics.getFont().getSize()-10);
		}

}