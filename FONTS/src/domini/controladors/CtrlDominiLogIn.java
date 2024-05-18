package domini.controladors;

import persistencia.Gestors.GestorPerfil;

public class CtrlDominiLogIn {

    private GestorPerfil GP;

    public CtrlDominiLogIn(){
        GP = new GestorPerfil();
    }

    public boolean inicisessio(String usuari, String contrasenya){
        return GP.login(usuari, contrasenya);
    }

    public boolean registrarse(String usuari, String contrasenya) {
        return GP.register(usuari, contrasenya);
    }
}
