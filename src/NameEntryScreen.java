import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class NameEntryScreen{
    // Messages à afficher
    private static final String[] messages = {
        "Ne cours pas vers les autres!",
        "Mange pour grandir!",
        "Clique pour accélérer!"
    };
    private static int messageIndex = 0;
    public static String nom="";

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("SERPENT AMICALE- ENTREZ VOTRE NOM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true); // Aucune bordure de fenêtre ou barre de titre
        frame.setResizable(false);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();

        // Créez un JPanel qui servira de conteneur pour les autres composants
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
    
        // Créez un label pour le titre avec le texte et la police souhaités
        JLabel title = new JLabel("SERPENT AMICALE");
        title.setFont(new Font("Calibri", Font.BOLD, 64));
        title.setForeground(Color.BLACK); // Couleur du texte
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Créez un label pour les instructions qui changent
        JLabel instruction = new JLabel(messages[0]);
        instruction.setFont(new Font("Calibri", Font.PLAIN, 24));
        instruction.setForeground(Color.BLACK);
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Timer pour changer les messages
        Timer instructionTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageIndex = (messageIndex + 1) % messages.length;
                instruction.setText(messages[messageIndex]);
            }
        });
        instructionTimer.start();
    
        // Champ de texte pour le nom de l'utilisateur
        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(300, 50));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Bouton pour commencer à jouer
        JButton playButton = new JButton("JOUER");
        playButton.setFont(new Font("Dialog", Font.BOLD, 16));
        playButton.setBorderPainted(false);
		playButton.setForeground(Color.BLACK);
		playButton.setContentAreaFilled(false);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Inside NameEntryScreen class
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
                if (!playerName.trim().isEmpty()) {
                    frame.dispose(); // Close the current window
        
                    // Create a new JFrame for the game
                    JFrame gameFrame = new JFrame("Snake Game");
                    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gameFrame.setUndecorated(true); // No window borders or title bar
                    gameFrame.setResizable(false);
        
                    // Create and add the game panel
                    IaJoueur gamePanel = new IaJoueur();
                    gameFrame.add(gamePanel);
                    gamePanel.setFocusable(true);
                    gamePanel.requestFocusInWindow();
                    gamePanel.addKeyListener(new KeyAdapter(){
                        public void keyPressed(KeyEvent e){
                            if(e.getKeyCode()=='M'){
                                gamePanel.removeAll();
                                System.exit(0);
                            } else if(e.getKeyCode()=='R'){
                                gamePanel.removeAll();
                                System.exit(0);
                            }
                        }
                    });
        
                    // Set the game frame to full screen
                    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    GraphicsDevice device = env.getDefaultScreenDevice();
                    device.setFullScreenWindow(gameFrame);
        
                    // Show the game frame
                    gameFrame.validate();
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer un nom pour jouer.", "Nom requis", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        

    
        // Ajoutez tous les composants au panel
        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(instruction);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(playButton);
        panel.add(Box.createVerticalGlue());
    
        // Ajoutez le panel au frame
        frame.add(panel);
    
        // Assurez-vous que le frame est en plein écran
        device.setFullScreenWindow(frame);
    
        // Rendez le frame visible
        frame.validate();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
    
