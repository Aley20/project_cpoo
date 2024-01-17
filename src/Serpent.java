import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Serpent extends JPanel  implements ActionListener, KeyListener, MouseMotionListener, SerpentInterface {

	protected final int largeur = 1150;
	protected final int longueur = 750;
	private boolean enPause = false;
	protected final int taille_tuille = 20; // taille d'une tuile (de la pomme ou du corps du serpent)
	protected final int nb_max = (this.largeur * this.longueur) / (this.taille_tuille * this.taille_tuille); // nombre
																											// maximal
																											// de tuile
																											// qu'il
																											// peut y
																											// avoir
																											// dans la
																											// fenetre

	// tableau des coordonnées x y des parties du corps du serpent
	protected final int x[] = new int[this.nb_max];
	protected final int y[] = new int[this.nb_max];

	protected int taille_serpent = 1; // longueur initial du serpent

	protected Direction direction = Direction.NORD; // direction du serpent
	protected boolean directionChoisi = false; // nécessaire pour calculer le temps que l'utilisateur choisi sa direction
	protected boolean gameover = false; // indique si le jeu est fini ou non
	protected Random random;
	protected Timer timer;

	Pomme pomme=new Pomme();

	// Temps de déplacement du serpent va s'aggrandir petit à petit quand il mange une pomme
	private int vitesse = 200;

	// Temps nécessaire pour faire un pas
	protected int delais = 10000000;
	protected long tempDebut = 0;

	public Serpent() {
		this.addMouseMotionListener(this);

		random = new Random();
		this.setPreferredSize(new Dimension(this.largeur, this.longueur));
		this.setBackground(Color.BLACK);
		addKeyListener(this);
        setFocusable(true);
		
		play();
		this.tempDebut = System.currentTimeMillis();
		this.requestFocusInWindow();
	}

	public boolean getgameOver() { return this.gameover; }
	public int getLargeur(){
		return this.largeur;
	}
	public int getLongueur(){
		return this.longueur;
	}
	/**
	 * initialise le jeu en ajoutant de la nourriture et en lançant le timmer
	 */
	public void play() {
		this.ajoutPomme();
		this.initialiseVitesse();
		this.tempDebut = System.currentTimeMillis();
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics,Color.GRAY);
	}

	/**
	 * gere le deplacement du serpent en fonction de la direction actuelle
	 */

public void move() {
	// parcours du corps du serpent sauf la tête
	for (int i = this.taille_serpent; i > 0; i--) {
		this.x[i] = this.x[i - 1];
		this.y[i] = this.y[i - 1];
	}

	// Déplacement de la tête du serpent selon la direction
	if (this.direction==this.direction.NORD) {
		this.y[0] = (this.y[0] - this.taille_tuille + this.longueur) % this.longueur;
	} else if (this.direction==this.direction.EST) {
		this.x[0] = (this.x[0] + this.taille_tuille) % this.largeur;
	} else if (this.direction==this.direction.OUEST) {
		this.x[0] = (this.x[0] - this.taille_tuille + this.largeur) % this.largeur;
	} else if (this.direction==this.direction.SUD) {
		this.y[0] = (this.y[0] + this.taille_tuille) % this.longueur;
	}
}

// Ajoutez ces méthodes pour gérer les mouvements de souris
@Override
public void mouseMoved(MouseEvent e) {
	// Obtient la position actuelle de la souris
	int mouseX = e.getX();
	int mouseY = e.getY();

	// Obtient la position actuelle de la tête du serpent
	int headX = this.x[0] + this.taille_tuille / 2;
	int headY = this.y[0] + this.taille_tuille / 2;

	// Calcule la différence entre les positions de la souris et de la tête du serpent
	int diffX = mouseX - headX;
	int diffY = mouseY - headY;

	// Met à jour la direction du serpent en fonction de la différence
	if (Math.abs(diffX) > Math.abs(diffY)) {
		if (diffX > 0 && !(this.direction==this.direction.OUEST)) {
			this.direction = this.direction.EST;
		} else if (diffX < 0 && !(this.direction==this.direction.EST)) {
			this.direction = this.direction.OUEST;
		}
	} else {
		if (diffY > 0 && !(this.direction==this.direction.NORD)) {
			this.direction = this.direction.SUD;
		} else if (diffY < 0 && !(this.direction==this.direction.SUD)) {
			this.direction = this.direction.NORD;
		}
	}
}

@Override
public void mouseDragged(MouseEvent e) {
	// Ne rien faire ici, mais nécessaire en raison de l'interface
}


	/**
	 * verifie si la tete du serpent a toucher la pomme, ou la efleurer
	 * si la tete du serpent est a proximité de la pomme , la pomme sera considéré
	 * comme mange
	 */
	public void verifPomme() {
		int cpt = this.taille_tuille / 2;
		if (Math.abs(this.x[0] - this.pomme.getPommeX()) <= cpt && Math.abs(this.y[0] - this.pomme.getPommeY()) <= cpt) {
			this.taille_serpent++;
			this.pomme.ajoutPomme();
			this.ajoutPomme();
		}
		// Si la tete du serpent touche la pomme empoisonnée c'est game over
		if (Math.abs(this.x[0] - this.pomme.getPoisonX()) <= cpt && Math.abs(this.y[0] - this.pomme.getPoisonY()) <= cpt) {
			// this.timer.setDelay(300);
			this.initialiseVitesse();
			this.gameover = true;
		}
	}

	/**
	 *
	 * @param graphics permet de dessiner l'état actuel du jeux
	 */
	public void draw(Graphics graphics,Color couleur) {
		if (!gameover) {
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
			graphics.fillOval(x[0] + taille_tuille / 4 - eyeSize / 2, y[0] + taille_tuille / 4 - eyeSize / 2, eyeSize, eyeSize);
			graphics.fillOval(x[0] + 3 * taille_tuille / 4 - eyeSize / 2, y[0] + taille_tuille / 4 - eyeSize / 2, eyeSize, eyeSize);
	
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
		} else {
			// Draw game over screen
			gameOver(graphics);
		}
	}
	


	/**
	 * 
	 * @param graphics va reinitialiser le jeu
	 */
	public void reinitialise() {
		this.taille_serpent = 1;
		this.pomme.reinitialisePomme();
		this.direction = this.direction.NORD;
		this.gameover = false;
		this.play();
	}

	public void gameOver(Graphics graphics) {
		// game over au centre de la fenetre
		graphics.setColor(Color.red);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 60));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over", (this.largeur - metrics.stringWidth("Game Over")) / 2, this.longueur / 2); 																																																																																																						

		// Score en dessous de game over
		graphics.setColor(Color.white);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: " + this.pomme.getPomme(), (this.largeur - metrics.stringWidth("Score: " + this.pomme.getPomme())) / 2,
				this.longueur / 2 + 50);

		graphics.drawString("Press 'R' to restart or 'M' to return to the menu",
				(this.largeur - metrics.stringWidth("Press 'R' to restart or 'M' to return to the menu")) / 2,
				this.longueur / 2 + 100);
	}

	/**
	 * 
	 * @param position x de la pomme
	 * @param position y de la pomme
	 * @return true si une position (x,y) est sur le serpent false sinon
	 */
	public boolean isOnSnake(int x, int y) {
		for (int i = 0; i < this.taille_serpent; i++) {
			if (this.x[i] == x && this.y[i] == y) {
				return true;
			}
		}
		return false;
	}

	public void augmenteVitesse() {
		int cpt = this.vitesse - this.vitesse/10;
		if (cpt > 100) {
			this.vitesse -= 1;
			this.timer = new Timer(this.vitesse, this);
			this.timer.stop();
			this.timer.start();
		}
	}

	public void initialiseVitesse() {
		this.vitesse = 200;
		if (this.timer != null && this.timer.isRunning()) {
			this.timer.stop();
		}
		this.timer = new Timer(this.vitesse, this);
		// this.timer.stop();
		this.timer.start();
	}

	public void ajoutPomme() {
		int x = random.nextInt((this.largeur / this.taille_tuille) - 1) * this.taille_tuille;
		int y = random.nextInt((this.longueur / this.taille_tuille) - 1) * this.taille_tuille;

		this.pomme.setPoisonX(random.nextInt((this.largeur / this.taille_tuille) - 1) * this.taille_tuille);
		this.pomme.setPoisonY(random.nextInt((this.longueur / this.taille_tuille) - 1) * this.taille_tuille);

		// verifie que la pomme n'apparait pas sur le serpent
		while (this.isOnSnake(x, y)) {
			x = random.nextInt((this.largeur / this.taille_tuille) - 1) * this.taille_tuille;
			y = random.nextInt((this.longueur / this.taille_tuille) - 1) * this.taille_tuille;
		}
		// Vérifie que la pomme empoisonnée n'apparaît pas sur la pomme
		while (this.pomme.getPoisonX() == x && this.pomme.getPoisonY() == y) {
			this.pomme.setPoisonX(random.nextInt((this.largeur / this.taille_tuille) - 1) * this.taille_tuille);
			this.pomme.setPoisonY(random.nextInt((this.longueur / this.taille_tuille) - 1) * this.taille_tuille);
		}
		this.pomme.setPommeX(y);
		this.pomme.setPommeY(y);
		//this.augmenteVitesse();
	}

	/**
	 * verifie si la tete du serpent a touché son propre corps
	 */
	public void collision() {
		// vérifie si la tete du serpent entre en collision avec son corps
		for (int i = this.taille_serpent; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				this.initialiseVitesse();
				this.gameover = true;
			}
		}
	}

	/**
	 * gére le déplacement du jeu
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		long tempsEcoule = System.currentTimeMillis() - this.tempDebut;
		if (tempsEcoule > 10000 && !this.directionChoisi) {
			this.gameover = true;
			this.initialiseVitesse();
		} else if (this.gameover == false) {
			this.move();
			this.verifPomme();
			this.collision();
		}
		// this.initialiseVitesse();
		this.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {  }

	@Override
	public void keyReleased(KeyEvent e) {  }

	@Override
	/**
	 * traite les événement de touches, donc elle va permettre de changer la
	 * direction du serpent en fonction de la touche pressé
	 * On vérifiera d'abord si le temps écoulé ne dépasse pas 10 secondess
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
            // Basculez l'état de pause
            enPause = !enPause;
            if (enPause) {
                // Arrêter le timer 
				timer.stop();

            } else {
                // Redémarrer le timer 
				timer.restart();
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            // Quitter le jeu 
            System.exit(0);
        }
		// Si c'est la deuxième entrée, comparez le temps actuel avec le temps de la
		// première entrée
		long tempsActuel = System.currentTimeMillis();
		long tempsEcoule = tempsActuel - this.tempDebut;

		if (tempsEcoule > this.delais) {
			// Si le temps écoulé dépasse le délai maximal, finissez le jeu
			this.gameover = true;
		}
		this.tempDebut = tempsActuel;
		switch (e.getKeyCode()) {
			// System.out.println("Direction: " + this.direction);
			case KeyEvent.VK_UP:
				if (this.direction != this.direction.SUD) {
					this.direction = this.direction.NORD;
					this.directionChoisi = true;
				}
				break;

			case KeyEvent.VK_RIGHT:
				if (this.direction != this.direction.OUEST) {
					this.direction = this.direction.EST;
					this.directionChoisi = true;
				}
				break;
			case KeyEvent.VK_LEFT:
				if (this.direction != this.direction.EST) {
					this.direction = this.direction.OUEST;
					this.directionChoisi = true;
				}
				break;

			case KeyEvent.VK_DOWN:
				if (this.direction != this.direction.NORD) {
					this.direction = this.direction.SUD;
					this.directionChoisi = true;
				}
				break;
		}
	}
	public int getVitesse(){
		return this.vitesse;
	}
}