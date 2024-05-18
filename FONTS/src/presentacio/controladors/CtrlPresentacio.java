package presentacio.controladors;

import domini.shared.Regles;
import presentacio.vistes.*;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlPresentacio {

    private final VistaPrincipal vistaPrincipal;

    // Atributs per defecte.
    private String perfilLoginat = "";

    private CtrlJugarPartida ctrlJugarPartida;
    private VistaJugarPartida vistaJugarPartida;
    private VistaSelJugadors vistaSelJugadors;

    private CtrlCrearTauler ctrlCrearTauler;
    private VistaCrearTauler vistaCrearTauler;

    private CtrlRecords ctrlRecords;
    private VistaRecords vistaRecords;

    private CtrlPerfil ctrlPerfil;
    private VistaPerfil vistaPerfil;

    private CtrlRankings ctrlRankings;
    private VistaRankings vistaRankings;
    private VistaRankPerfils vistaRankPerfils;

    private CtrlLogIn CL;
    private VistaLogin VL = new VistaLogin(this, CL);

    private CtrlCarregarTauler CCT;
    private VistaCarregarTauler VCT;

    private CtrlCarregarPartida CCP;
    private VistaCarregarPartida VCP;

    private VistaModificarRegles VMR;

    public CtrlPresentacio() throws IOException {
        vistaPrincipal = new VistaPrincipal(this);

        // Controlador i Vistes de Crear
        ctrlCrearTauler = new CtrlCrearTauler();
        vistaCrearTauler = new VistaCrearTauler(this, ctrlCrearTauler);
        ctrlCrearTauler.assignarVistes(vistaCrearTauler);


        //Controlador i Vista Login
        this.CL = new CtrlLogIn(VL);
        this.VL = new VistaLogin(this, CL);
        CL.assignarVista(this.VL);


        //Controlador i VistaCarragarTauler
        CCT = new CtrlCarregarTauler(VCT);
        VCT = new VistaCarregarTauler(this, this.CCT);


        //Controlador i Vista ModificarRegles
        this.VMR = new VistaModificarRegles(this);


        //Controlador i Vista Perfil
        this.ctrlPerfil = new CtrlPerfil();
        this.vistaPerfil = new VistaPerfil(this,this.ctrlPerfil);

        //Controlador i Vista Rankings
        this.ctrlRankings = new CtrlRankings();
        this.vistaRankings = new VistaRankings(this, this.ctrlRankings);
        this.vistaRankPerfils = new VistaRankPerfils(this,this.ctrlRankings);

        //Controlador i Vista Records
        this.ctrlRecords = new CtrlRecords();
        this.vistaRecords = new VistaRecords(this,this.ctrlRecords);

    }

    public void iniciarPresentacio() {
        vistaPrincipal.esborraContinguts();
        vistaPrincipal.inicialitzarMenubarVista();
        vistaPrincipal.ferVisible();
    }

    public void iniciarPartidaNova() {
        ctrlJugarPartida = new CtrlJugarPartida(perfilLoginat, VCT.getSeleccionat(), VMR.getRegles());
        vistaSelJugadors = new VistaSelJugadors(this, ctrlJugarPartida);
        vistaJugarPartida = new VistaJugarPartida(this, ctrlJugarPartida);
        ctrlJugarPartida.assignarVistes(vistaSelJugadors, vistaJugarPartida);
        vistaPrincipal.esborraContinguts();
        vistaSelJugadors.ferVisible();
    }

    public void iniciarPartidaGuardada(String nomPartida) {
        ctrlJugarPartida = new CtrlJugarPartida(perfilLoginat, nomPartida);
        vistaJugarPartida = new VistaJugarPartida(this, ctrlJugarPartida);
        ctrlJugarPartida.assignarVistes(null, vistaJugarPartida);
        vistaPrincipal.esborraContinguts();
        vistaJugarPartida.ferVisible();
        ctrlJugarPartida.iniciarPartidaGuardada();
    }

    public void crearTauler() {
        vistaPrincipal.esborraContinguts();
        vistaCrearTauler.ferVisible();
    }

    public void iniciPresentacio() {
        vistaPrincipal.esborraContinguts();
        VL.ferVisible();
    }

    public void carregarTauler() {
        vistaPrincipal.esborraContinguts();
        try {
            VCT.actualitzar_taulers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VCT.ferVisible();
    }

    public void carregarPartida() throws IOException {
        //Controlador i Vista Carregarartida
        this.CCP = new CtrlCarregarPartida(VCP);
        this.VCP = new VistaCarregarPartida(this, this.CCP);
        vistaPrincipal.esborraContinguts();
        try {
            this.VCP.actualitzarPartides();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VCP.ferVisible();
    }

    public void modificarRegles(){
        vistaPrincipal.esborraContinguts();
        VMR.ferVisible();
    }

    public void carregarRankingG() {
        vistaPrincipal.esborraContinguts();
        vistaRankings.ferVisible();
    }

    public void carregarRankingP() {
        vistaPrincipal.esborraContinguts();
        vistaRankPerfils.ferVisible();
    }


    public void carregarPerfil() {
        vistaPrincipal.esborraContinguts();
        vistaPerfil.ferVisible();
    }

    public void carregarRecords(){
        vistaPrincipal.esborraContinguts();
        vistaRecords.ferVisible();
    }

    public void setPerfilLoginat(String perfilLoginat) {
        this.perfilLoginat = perfilLoginat;
    }
    public String getPerfilLoginat() {
        return this.perfilLoginat;
    }
}
