import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class PrincipalMenu extends JFrame implements KeyListener {
	JButton[] buttons = new JButton[5];
	
	public static ServerSocket serverSocket;

	
	int index;
	static JFrame frame;
    public  boolean IAuser=false;
	PrincipalMenu() throws LineUnavailableException {
        frame = new JFrame("SNAKE GAME");
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(1200, 750));
        frame.setResizable(false);

        // Charger l'image et la redimensionner
        ImageIcon originalIcon = new ImageIcon("snake.jpg");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(1200, 750, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximise la fenêtre
        // Créer un panneau pour le contenu du frame
        JPanel contentPane = new JPanel() {
            public void paintComponent(Graphics g) {
                // Dessiner l'image de fond
                g.drawImage(resizedIcon.getImage(), 0, 0, null);
            }
        };
        contentPane.setLayout(new GridBagLayout());
		// Créer une box horizontale pour les boutons
		Box buttonBox = Box.createVerticalBox();

		// Ajouter des boutons à la box

		JButton COMMENCER = new JButton("COMMENCER");
		JButton IAMODE = new JButton("IAMODE");
		JButton MULTIPLAYER = new JButton("MULTIPLAYER");
	
		JButton JOINDREUNSERVEUR = new JButton("JOINDRE UN SERVEUR");
		JButton SORTIR = new JButton("SORTIR");

		COMMENCER.setBorderPainted(false);
		COMMENCER.setForeground(Color.white);
		COMMENCER.setContentAreaFilled(false);
		COMMENCER.setFont(new Font("Dialog", Font.BOLD, 18));
		IAMODE.setFont(new Font("Dialog", Font.BOLD, 16));
		MULTIPLAYER.setFont(new Font("Dialog", Font.BOLD, 16));
		JOINDREUNSERVEUR.setFont(new Font("Dialog", Font.BOLD, 16));
		SORTIR.setFont(new Font("Dialog", Font.CENTER_BASELINE, 14));
		SORTIR.setBorderPainted(false);
		SORTIR.setForeground(Color.white);
		SORTIR.setContentAreaFilled(false);
		MULTIPLAYER.setBorderPainted(false);
		MULTIPLAYER.setForeground(Color.white);
		MULTIPLAYER.setContentAreaFilled(false);
		IAMODE.setBorderPainted(false);
		IAMODE.setForeground(Color.white);
		IAMODE.setContentAreaFilled(false);
		JOINDREUNSERVEUR.setBorderPainted(false);
		JOINDREUNSERVEUR.setForeground(Color.white);
		JOINDREUNSERVEUR.setContentAreaFilled(false);

		buttonBox.add(COMMENCER);
		buttonBox.add(IAMODE);
		buttonBox.add(MULTIPLAYER);
		buttonBox.add(JOINDREUNSERVEUR);
		buttonBox.add(SORTIR);
		buttons[0] = COMMENCER;
		buttons[1] = IAMODE;
		buttons[2] = MULTIPLAYER;
		buttons[3] = JOINDREUNSERVEUR;
		buttons[4] = SORTIR;
		MouseAdapter mouseAdapter = new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				// Changer la couleur de l'écriture en rose néon lorsque la souris touche le bouton
				JButton button = (JButton) e.getSource();
				button.setForeground(new Color(139, 69, 19)); // Rose néon
			}

			
			public void mouseExited(MouseEvent e) {
				// Changer la couleur de l'écriture en blanc lorsque la souris quitte le bouton
				JButton button = (JButton) e.getSource();
				button.setForeground(Color.WHITE);
			}
		};
		COMMENCER.addMouseListener(mouseAdapter);
		IAMODE.addMouseListener(mouseAdapter);
		MULTIPLAYER.addMouseListener(mouseAdapter);
		JOINDREUNSERVEUR.addMouseListener(mouseAdapter);
		SORTIR.addMouseListener(mouseAdapter);
		SORTIR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // Quitte l'application
			}
		});
		COMMENCER.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
               showSoloGamePanel();
            }
	});
		IAMODE.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                // Inverser le mode IA à chaque clic
                IAuser = true;
                // Afficher le panneau de jeu approprié en fonction du mode choisi
               
                  
                
                    showSoloGamePanel();
                
            }
		});
		MULTIPLAYER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMultiplayerGamePanel();
			}
		});
		JOINDREUNSERVEUR.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(isPortAvailable(1234))
		         {
					new Thread(() -> {
					try {
						if(serverSocket==null){
						serverSocket = new ServerSocket(1234);
						Serveur serveur = new Serveur(serverSocket);
						serveur.startServeur();
						}
						
					} catch (IOException i) {
						i.printStackTrace();
					}
				}).start();
		}
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                NameEntryScreen.createAndShowGUI();
            }
        });
		}
		});
		COMMENCER.addKeyListener(this);
IAMODE.addKeyListener(this);
SORTIR.addKeyListener(this);
MULTIPLAYER.addKeyListener(this);
JOINDREUNSERVEUR.addKeyListener(this);

		frame.setLocationRelativeTo(null);

		// Ajouter la box avec les boutons au panneau
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(350, 0, 0, 0);
		contentPane.add(buttonBox, constraints);

		// Ajouter le panneau au frame
		frame.setContentPane(contentPane);

		// Centrer le frame sur l'écran
		frame.setLocationRelativeTo(null);

		// Ajouter le KeyListener au JFrame
		frame.addKeyListener(this);

		// Initialiser l'indice du bouton sélectionné on commence par commencer which is
		// at the index 0
		index = 0;
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	
	public void keyTyped(KeyEvent e) {

	}
	boolean isPortAvailable(int port) {
		try {
			//  créer un ServerSocket temporaire sur le port spécifié
			ServerSocket tempServerSocket = new ServerSocket(port);
			// si cela réussit, fermer le socket temporaire et renvoyez vrai
			tempServerSocket.close();
			return true;
		} catch (IOException e) {
			// si une exception est levée, cela signifie que le port est déjà utilisé
			return false;
		}
	}

	int focused = -1;

	

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		buttons[index].setFocusable(true);

		if (keyCode == KeyEvent.VK_DOWN) {
			// L'utilisateur a appuyé sur la touche de la flèche vers le bas

			// Déselectionner le bouton courant
			buttons[index].setFocusable(false);
			buttons[index].setForeground(Color.WHITE);

			// Sélectionner le prochain bouton
			index = (index + 1) % buttons.length;
			buttons[index].setFocusable(true);

			buttons[index].setForeground(new Color(139, 69, 19)); // Marron en utilisant les valeurs RGB

			buttons[index].requestFocus();
			focused = index;
		}
		if (keyCode == KeyEvent.VK_UP) {
			// L'utilisateur a appuyé sur la touche de la flèche vers le bas

			// Déselectionner le bouton courant
			buttons[index].setFocusable(false);
			buttons[index].setForeground(Color.WHITE);

			// Sélectionner le prochain bouton
			if (index - 1 >= 0) {
				index = (index - 1) % buttons.length;
			} else {
				index = (index - 1 + 5) % buttons.length;
			}
			buttons[index].setFocusable(true);

			buttons[index].setForeground(new Color(139, 69, 19)); // Marron en utilisant les valeurs RGB

			buttons[index].requestFocus();
			focused = index;

		}
		if (keyCode == KeyEvent.VK_ENTER) {

			switch (focused) {
			case 0:
						showSoloGamePanel();
						//view.requestFocus();
				break;// commencer la partie
			case 1:
			IAuser=true;
           showSoloGamePanel();
		   break;
			case 2:
         showMultiplayerGamePanel();
			//	System.out.println("MULTIPLAYER appliquée");
				break;// afficher une video demo je ferais ca a la fin du jeu
			case 3:
            //showSoloGamePanel();
				//System.out.println("bouton exit appuyé");
				System.exit(0);
				break;// sortir
				case 4:
				System.exit(0);
				break;// sortir
			} 

		}
		// if (keyCode == KeyEvent.VK_ENTER) {
//          
//        	//if(foc==0)System.out.println("partie commencé");
//        	//if(foc==1)System.out.println("IAMODE affichés");
//        	//if(foc==2)System.out.println("MULTIPLAYER appliquée");
//        	//if(foc==3)System.exit(1);
//        }

	}

	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
     // Méthode pour revenir à l'écran principal après la fin du jeu
     private void showMainMenu() {
        // Supprimer le GamePanel et ajouter à nouveau le panneau principal avec les boutons
        frame.getContentPane().removeAll();
        try {
            new PrincipalMenu().frame.setLocationRelativeTo(null);
            
        } catch (LineUnavailableException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée, par exemple, imprimer la trace de la pile
        }
    
        frame.revalidate();
        frame.repaint();
    }
  // Méthode pour afficher le GamePanel lorsque le mode solo est sélectionné
  protected void showSoloGamePanel() {
    // Créer le GamePanel et l'ajouter au cadre
    
    if(IAuser==false)   {
   Serpent gamePanel = new Serpent();
   frame.getContentPane().removeAll();
JPanel j=new JPanel();
j.setPreferredSize(new Dimension(1200,750));
j.setBackground(Color.BLACK);
frame.setContentPane(j);
frame.getContentPane().add(gamePanel);
// Redessiner le cadre pour refléter les changements
frame.revalidate();
frame.repaint();
    gamePanel.setFocusable(true);
    gamePanel.requestFocusInWindow();


    gamePanel.addKeyListener(new KeyAdapter(){
    
        public void keyPressed(KeyEvent e){
            if(gamePanel.getgameOver() && e.getKeyCode()=='R'){
                gamePanel.removeAll();
                gamePanel.reinitialise();
            } else if(gamePanel.getgameOver() && e.getKeyCode()=='M'){
                //gamePanel.removeAll();
               showMainMenu();
            }
        }
    });
    }else{
        Serpent2 gamePanel = new Serpent2();
		frame.getContentPane().removeAll();
JPanel j=new JPanel();
j.setPreferredSize(new Dimension(1200,750));
j.setBackground(Color.BLACK);
frame.setContentPane(j);
frame.getContentPane().add(gamePanel);
// Redessiner le cadre pour refléter les changements
frame.revalidate();
frame.repaint();
    gamePanel.setFocusable(true);
    gamePanel.requestFocusInWindow();


    gamePanel.addKeyListener(new KeyAdapter(){
		public void keyPressed(KeyEvent e){
			if(gamePanel.getgameOver() && e.getKeyCode()=='R'){
				gamePanel.removeAll();
				gamePanel.reinitialise();
			} else if((gamePanel.getgameOver() && e.getKeyCode()=='M') || (!(gamePanel.getgameOver()) && e.getKeyCode()=='M')){
				//gamePanel.removeAll();
				showMainMenu();
			} else if(!(gamePanel.getgameOver()) && e.getKeyCode()=='E'){
				System.exit(0);
			}
		}
    });
    }

}
 // Nouvelle méthode pour afficher le panneau de jeu multijoueur
 public void showMultiplayerGamePanel() {
    // Créer le GamePanel multijoueur et l'ajouter au cadre
    SerpentMultijoueur gamePanel = new SerpentMultijoueur(new Serpent(),new Serpent());
    frame.getContentPane().removeAll();
	JPanel j=new JPanel();
	j.setPreferredSize(new Dimension(1200,750));
	j.setBackground(Color.BLACK);
	frame.setContentPane(j);
    frame.getContentPane().add(gamePanel);

    // Redessiner le cadre pour refléter les changements
    frame.revalidate();
    frame.repaint();
    gamePanel.setFocusable(true);
    gamePanel.requestFocusInWindow();

    // Ajouter un écouteur pour les touches
    gamePanel.addKeyListener(new KeyAdapter() {
        
        public void keyPressed(KeyEvent e) {
            if (gamePanel.getgameOver() && e.getKeyCode() == 'R') {
                gamePanel.removeAll();
                gamePanel.reinitialise();
            } else if (gamePanel.getgameOver() && e.getKeyCode() == 'M') {
                showMainMenu();
            }
        }
    });
}

}