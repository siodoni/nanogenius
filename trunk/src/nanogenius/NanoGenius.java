package nanogenius;

import javax.microedition.lcdui.*;
import java.util.Random;

public class NanoGenius extends Canvas implements CommandListener {

    private Main game;
    private Command cmdSair;
    private Command cmdLoop;
    private int largura, altura, percBorda, curBlock, curLayout = 0, curSample = 0, emJogo = 0, percDesloc;
    private StringBuffer sequencia = new StringBuffer();
    private static Random random = new Random();

    public NanoGenius(Main midlet) {
        percDesloc = (getWidth() * 20) / 100;
        largura = getWidth() - percDesloc;
        altura = getHeight();
        percBorda = (getWidth() * 10) / 100;
        curBlock = 0;
        Escala.oitava = 36;

        //setFullScreenMode(true);
        this.game = midlet;
        cmdSair = new Command("Sair", Command.EXIT, 0);
        addCommand(cmdSair);
        cmdLoop = new Command("Novo", Command.ITEM, 0);
        addCommand(cmdLoop);
        setCommandListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Cor.BRANCO);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Cor.PRETO);
        g.fillArc(percDesloc / 2, 0, largura, largura, 0, 360);

        if (curBlock == 1) {
            Escala.tocaNota(Escala.cNat);
        }
        if (curBlock == 2) {
            Escala.tocaNota(Escala.eNat);
        }
        if (curBlock == 3) {
            Escala.tocaNota(Escala.fNat);
        }
        if (curBlock == 4) {
            Escala.tocaNota(Escala.gNat);
        }
        if (curBlock == 5) {
            Escala.tocaNota(Escala.aNat);
        }

        g.setColor(curBlock == 1 ? Cor.VERDE : Cor.VERDE_ESCURO);
        g.fillArc(percDesloc / 2, 0, largura - percBorda, largura - percBorda, 90, 90);

        g.setColor(curBlock == 2 ? Cor.VERMELHO : Cor.VERMELHO_ESCURO);
        g.fillArc(percBorda + percDesloc / 2, 0, largura - percBorda, largura - percBorda, 360, 90);

        g.setColor(curBlock == 3 ? Cor.AMARELO : Cor.AMARELO_ESCURO);
        g.fillArc(percDesloc / 2, percBorda, largura - percBorda, largura - percBorda, 180, 90);

        g.setColor(curBlock == 4 ? Cor.AZUL : Cor.AZUL_ESCURO);
        g.fillArc(percBorda + percDesloc / 2, percBorda, largura - percBorda, largura - percBorda, 360, -90);

        g.setColor(Cor.PRETO);
        g.fillArc(((largura / 4) - (percBorda / 2)) + percDesloc / 2, (largura / 4) - (percBorda / 2), (largura / 2) + percBorda, (largura / 2) + percBorda, 0, 360);

        g.setColor(curBlock == 5 ? Cor.ROXO : Cor.ROXO_ESCURO);
        g.fillArc((largura / 3) + percDesloc / 2, largura / 3, largura / 3, largura / 3, 0, 360);

        g.setColor(Cor.LARANJA_ESCURO);
        if (sequencia.length() == 0) {
            g.drawString("", 0, largura + percBorda, Graphics.BOTTOM|Graphics.LEFT);
        }else{
            g.drawString("Jogada " + sequencia.length(), 0, largura + percBorda, Graphics.BOTTOM|Graphics.LEFT);
        }
    }

    protected void pausa(int tempo) {
        try {
            Thread.currentThread().sleep(tempo);
        } catch (InterruptedException ex) {
        }
    }

    protected void piscaBloco(int bloco, int tempo) {
        curBlock = bloco;
        repaint();
        serviceRepaints();
        pausa(tempo);
        curBlock = 0;
        repaint();
        serviceRepaints();
    }

    protected void keyPressed(int keyCode) {

        int tecla = getGameAction(keyCode);

        if (emJogo == 0) {
            if (keyCode == KEY_NUM1) {
                piscaBloco(1, 600);
            }
            if (keyCode == KEY_NUM3) {
                piscaBloco(2, 600);
            }
            if (keyCode == KEY_NUM5) {
                piscaBloco(5, 600);
            }
            if (keyCode == KEY_NUM7) {
                piscaBloco(3, 600);
            }
            if (keyCode == KEY_NUM9) {
                piscaBloco(4, 600);
            }

            if (tecla == Canvas.FIRE) {
                novoJogo();
            } else {

                if (sequencia.length() > 0) {
                    if (((keyCode == KEY_NUM1) && (sequencia.charAt(curSample) == KEY_NUM1))
                     || ((keyCode == KEY_NUM3) && (sequencia.charAt(curSample) == KEY_NUM2))
                     || ((keyCode == KEY_NUM5) && (sequencia.charAt(curSample) == KEY_NUM5))
                     || ((keyCode == KEY_NUM7) && (sequencia.charAt(curSample) == KEY_NUM3))
                     || ((keyCode == KEY_NUM9) && (sequencia.charAt(curSample) == KEY_NUM4))) {
                        curSample++;
                        if (curSample == sequencia.length()) {
                            pausa(1000);
                            jogar();
                        }
                    } else {
                        for (int i = 0; i < 3; i++) {
                            for (int x = 1; x < 6; x++) {
                                piscaBloco(x, 100);
                            }
                        }
                        pausa(2000);
                        novoJogo();
                    }
                }
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdSair) {
            game.destroyApp(false);
        }
        if (c == cmdLoop) {
            novoJogo();
        }
    }

    private void novoJogo() {
        sequencia = new StringBuffer();
        jogar();
    }

    private void jogar() {
        emJogo = 1;

        sequencia.append(random.nextInt(5) + 1);
        for (int i = 0; i < sequencia.length(); i++) {
            piscaBloco(sequencia.charAt(i) - 48, 600);
        }
        emJogo = 0;
        curSample = 0;
    }
}
