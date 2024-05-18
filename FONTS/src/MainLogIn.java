import presentacio.controladors.CtrlPresentacio;

import java.io.IOException;

public class MainLogIn {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
            () -> {
                CtrlPresentacio ctrlPresentacio = null;
                try {
                    ctrlPresentacio = new CtrlPresentacio();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ctrlPresentacio.iniciPresentacio();
            }
        );
    }
}
