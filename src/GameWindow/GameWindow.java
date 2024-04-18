package GameWindow;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


/*! \class GameWindow
    \brief Implementeaza notiunea de fereastra a jocului.

    Membrul windowFrame este un obiect de tip JFrame care va avea utilitatea unei
    ferestre grafice
 */
public class GameWindow {

    private JFrame windowFrame; // fereastra principala a jocului
    private int width;  // latimea ferestrei
    private int height; // inaltimea ferestrei
    private int title;  // titlul ferestrei

    public GameWindow(GamePanel gamePanel) {
        windowFrame = new JFrame();

        windowFrame.setTitle("Stuck in Adventure Time");
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // inchide consola cand inchidem fereastra

        windowFrame.add(gamePanel); // inseram ceea ce am desenat in "rama"
        windowFrame.setResizable(false);
        //windowFrame.setLocationRelativeTo(null); // fereastra se va deschide in mijlocul ecranului, nu in colt stanga sus

        windowFrame.pack(); // causes this window to be sized to fit the preferred size and layouts of it subcomponents

        windowFrame.setVisible(true);

        windowFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("Back");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
