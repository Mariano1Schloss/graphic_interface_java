import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @brief Cette classe représente une interface graphique simple pour interagir avec un serveur distant.
 */
public class Remote extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextArea textArea;

    private Client client;

    /**
     * @brief Constructeur de la classe Remote.
     * <p>
     * Initialise l'interface graphique et crée une instance du client.
     */
    public Remote() {
        /*! Creating the main window*/
        super("Graphical Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*! Creating the text area*/
        textArea = new JTextArea(10, 30);
        textArea.append("Enter media name : ");

        /*! Creating a JScrollPane and adding the text area to it*/
        JScrollPane scrollPane = new JScrollPane(textArea);

        /*! Creating actions for the buttons*/
        Action addAction1 = new AddAction("Search media", "Search a media information", "control 1");
        Action addAction2 = new AddAction("Play media", "Play a media on the server ", "control 2");
        Action exitAction = new ExitAction("Exit", "Exit the application", "control X");
        Action clearAction = new ClearAction("Clear", "Clear the text field", "control r");

        /*! Creating a menu bar and adding a menu to it*/
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        menuBar.add(fileMenu);

        /*! Adding menu items using the actions*/
        fileMenu.add(new JMenuItem(addAction1));
        fileMenu.add(new JMenuItem(addAction2));
        fileMenu.addSeparator(); /*! Separator between adding actions and exit action*/
        fileMenu.add(new JMenuItem(exitAction));

        /*! Setting the menu bar to the JFrame*/
        setJMenuBar(menuBar);

        /*! Creating a tool bar and adding buttons to it*/
        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton(addAction1));
        toolBar.add(new JButton(addAction2));
        toolBar.add(new JButton(clearAction));
        toolBar.addSeparator(); /*! Separator between adding actions and exit action*/
        toolBar.add(new JButton(exitAction));

        /*! Configuring the BorderLayout layout*/
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH); /*! Adding the tool bar to the north*/
        add(scrollPane, BorderLayout.CENTER);

        /*!Create the client*/
        String host = Client.DEFAULT_HOST;
        int port = Client.DEFAULT_PORT;
        try {
            client = new Client(host, port);
        } catch (Exception e) {
            System.err.println("Client: Couldn't connect to " + host + ":" + port);
            System.exit(1);
        }

        System.out.println("Client connected to " + host + ":" + port);

        /*! Packing and making the window visible*/
        pack();
        setVisible(true);
    }

    /**
     * @brief Cette classe interne représente une action d'ajout générique utilisée pour les boutons de recherche et de lecture.
     */
    private class AddAction extends AbstractAction {
        public AddAction(String name, String description, String accelerator) {
            super(name);
            putValue(SHORT_DESCRIPTION, description);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullText = textArea.getText();
            String initialString = "Enter media name :";
            /*! Check if the fullText starts with the initialString*/
            if (fullText.startsWith(initialString)) {
                /*! Extract first line of textArea excluding the initialString*/
                String userInput = fullText.substring(initialString.length()).trim();
                int indexOfNewLine = userInput.indexOf("\n");
                if (indexOfNewLine != -1) userInput = userInput.substring(0, indexOfNewLine);
                System.out.println("User Input: " + userInput);
                String request = "";
                String response = "";

                if (getValue(NAME).equals("Search media")) {
                    request = "printRequest " + userInput;
                } else if (getValue(NAME).equals("Play media")) {
                    request = "playRequest " + userInput;
                }
                if (!request.isBlank()) response = client.send(request);
                if (response.length() == 0) {
                    textArea.append("\nmedia with name " + userInput + " not found\n");
                } else {
                    System.out.println("Response: " + response);
                    textArea.append("\n" + response);
                }
            } else {
                /*! Handle case where the initialString is not present*/
                System.out.println("Invalid input format");
                return;
            }


        }
    }

    /**
     * @brief Cette classe interne représente une action de sortie générique utilisée pour le bouton de sortie.
     */
    private class ExitAction extends AbstractAction {
        public ExitAction(String name, String description, String accelerator) {
            super(name);
            putValue(SHORT_DESCRIPTION, description);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * @brief Cette classe interne représente une action de suppression générique utilisée pour le bouton de suppression.
     */
    private class ClearAction extends AbstractAction {
        public ClearAction(String name, String description, String accelerator) {
            super(name);
            putValue(SHORT_DESCRIPTION, description);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("Enter media name : ");

        }
    }

    /**
     * @param args Les arguments de la ligne de commande.
     * @brief Point d'entrée principal de l'application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Remote());
    }
}
