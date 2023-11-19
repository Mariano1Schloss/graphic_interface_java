import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/*! \file MainWindow.java
 *  \brief Contient la définition de la classe MainWindow.
 */

/*! \class MainWindow
 *  \brief Représente une fenêtre graphique principale avec une zone de texte et des actions associées.
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextArea textArea;

    /*! \brief Constructeur de la classe MainWindow.
     *
     *  Initialise la fenêtre graphique et les composants associés.
     */
    public MainWindow() {
        /*! Creating the main window */
        super("Graphical Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*! Creating the text area */
        textArea = new JTextArea(10, 30);

        /*! Creating a JScrollPane and adding the text area to it */
        JScrollPane scrollPane = new JScrollPane(textArea);

        /*! Creating actions for the buttons */
        Action addAction1 = new AddAction("Add Line 1", "Add a line 1 to the text area", "control 1");
        Action addAction2 = new AddAction("Add Line 2", "Add a line 2 to the text area", "control 2");
        Action exitAction = new ExitAction("Exit", "Exit the application", "control X");

        /*! Creating a menu bar and adding a menu to it */
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        menuBar.add(fileMenu);

        /*! Adding menu items using the actions */
        fileMenu.add(new JMenuItem(addAction1));
        fileMenu.add(new JMenuItem(addAction2));
        fileMenu.addSeparator(); /*! Separator between adding actions and exit action */
        fileMenu.add(new JMenuItem(exitAction));

        /*! Setting the menu bar to the JFrame */
        setJMenuBar(menuBar);

        /*! Creating a tool bar and adding buttons to it */
        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton(addAction1));
        toolBar.add(new JButton(addAction2));
        toolBar.addSeparator(); /*! Separator between adding actions and exit action */
        toolBar.add(new JButton(exitAction));

        /*! Configuring the BorderLayout layout */
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH); /*! Adding the tool bar to the north */
        add(scrollPane, BorderLayout.CENTER);

        /*! Packing and making the window visible */
        pack();
        setVisible(true);
    }

    /*! \class AddAction
     *  \brief Représente une action pour ajouter des lignes à la zone de texte.
     */
    private class AddAction extends AbstractAction {
        /*! \brief Constructeur de la classe AddAction.
         *
         *  \param name Le nom de l'action.
         *  \param description La description de l'action.
         *  \param accelerator L'accélérateur de l'action.
         */
        public AddAction(String name, String description, String accelerator) {
            super(name);
            putValue(SHORT_DESCRIPTION, description);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator));
        }

        /*! \brief Méthode appelée lorsqu'une action est déclenchée.
         *
         *  \param e L'événement d'action.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getValue(NAME).equals("Add Line 1")) {
                textArea.append("Line 1 added\n");
            } else if (getValue(NAME).equals("Add Line 2")) {
                textArea.append("Line 2 added\n");
            }
        }
    }

    /*! \class ExitAction
     *  \brief Représente une action pour quitter l'application.
     */
    private class ExitAction extends AbstractAction {
        /*! \brief Constructeur de la classe ExitAction.
         *
         *  \param name Le nom de l'action.
         *  \param description La description de l'action.
         *  \param accelerator L'accélérateur de l'action.
         */
        public ExitAction(String name, String description, String accelerator) {
            super(name);
            putValue(SHORT_DESCRIPTION, description);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator));
        }

        /*! \brief Méthode appelée lorsqu'une action est déclenchée.
         *
         *  \param e L'événement d'action.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /*! \brief Point d'entrée principal de l'application.
     *
     *  \param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
