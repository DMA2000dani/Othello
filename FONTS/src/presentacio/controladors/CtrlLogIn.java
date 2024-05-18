package presentacio.controladors;

import domini.controladors.CtrlDominiLogIn;
import presentacio.vistes.VistaLogin;


public class CtrlLogIn {

    private VistaLogin VL;
    private CtrlDominiLogIn CDLI;

    public CtrlLogIn(VistaLogin VLI) {
        this.VL = VLI;
        this.CDLI = new CtrlDominiLogIn();
    }

    public void iniciarsessio(String usuari, String contrasenya, CtrlPresentacio ctrlPresentacio){
        if(CDLI.inicisessio(usuari, contrasenya)) {
            ctrlPresentacio.setPerfilLoginat(usuari);
            ctrlPresentacio.iniciarPresentacio();
        }
        else{ // mostra error = "l'usuari ja existeix"
            VL.actualitza("l'usuari o la contrasenya no és correcte");
        }
    }

    public void registrarse(String usuari, String contrasenya, CtrlPresentacio ctrlPresentacio){
        if(CDLI.registrarse(usuari, contrasenya)) {
            VL.actualitza("S'ha registrat correctament, ja pots iniciar sessió");
        }
        else {
            VL.actualitza("l'usuari ja existeix, crea un altre");
        }
    }

    public void assignarVista(VistaLogin vl) {
        this.VL = vl;
    }
}
