package nanogenius;

import javax.microedition.lcdui.*;
import java.util.Random;

/**
 * @author Flavio A. S. Ximenes
 * @author Francisco A. Tristão
 * @author Igor Mori Tristão
 */
public class NanoGenius extends Canvas implements CommandListener {

    private Main game;
    private Command cmdSair, cmdJogar, cmdSobre, cmdHelp, cmdVoltar;
    private int altura, largura, larguraAlt, percBorda, curBlock, curSample = 0, emJogo = 0, percDesloc;
    private StringBuffer sequencia = new StringBuffer();
    private static Random random = new Random();
    private TextBox sobre, help;

    public NanoGenius(Main midlet) {
        this.game = midlet;

        cmdSair = new Command("Sair", Command.EXIT, 0);
        cmdJogar = new Command("Novo", Command.ITEM, 0);
        cmdSobre = new Command("Sobre", Command.ITEM, 0);
        cmdHelp = new Command("Ajuda", Command.HELP, 0);
        cmdVoltar = new Command("Voltar", Command.BACK, 0);

        altura = getHeight();
        largura = getWidth();
        percDesloc = (largura * 20) / 100;
        larguraAlt = largura - percDesloc;
        percBorda = (largura * 10) / 100;
        curBlock = 0;
        Musica.oitava = 36;

        addCommand(cmdSair);
        addCommand(cmdJogar);
        addCommand(cmdSobre);
        addCommand(cmdHelp);
        setCommandListener(this);

        montaSobre();
        montaHelp();
    }

    public void paint(Graphics g) {
        g.setColor(Cor.BRANCO);
        g.fillRect(0, 0, largura, altura);

        g.setColor(Cor.PRETO);
        g.fillArc(percDesloc / 2, percDesloc, larguraAlt, larguraAlt, 0, 360);

        if (curBlock == 1) {
            Musica.tocaNota(Musica.cNat);
        }
        if (curBlock == 2) {
            Musica.tocaNota(Musica.eNat);
        }
        if (curBlock == 3) {
            Musica.tocaNota(Musica.fNat);
        }
        if (curBlock == 4) {
            Musica.tocaNota(Musica.gNat);
        }
        if (curBlock == 5) {
            Musica.tocaNota(Musica.aNat);
        }

        g.setColor(curBlock == 1 ? Cor.VERDE : Cor.VERDE_ESCURO);
        g.fillArc(percDesloc / 2, percDesloc, larguraAlt - percBorda, larguraAlt - percBorda, 90, 90);

        g.setColor(curBlock == 2 ? Cor.VERMELHO : Cor.VERMELHO_ESCURO);
        g.fillArc(percBorda + percDesloc / 2, percDesloc, larguraAlt - percBorda, larguraAlt - percBorda, 360, 90);

        g.setColor(curBlock == 3 ? Cor.AMARELO : Cor.AMARELO_ESCURO);
        g.fillArc(percDesloc / 2, percBorda + percDesloc, larguraAlt - percBorda, larguraAlt - percBorda, 180, 90);

        g.setColor(curBlock == 4 ? Cor.AZUL : Cor.AZUL_ESCURO);
        g.fillArc(percBorda + percDesloc / 2, percBorda + percDesloc, larguraAlt - percBorda, larguraAlt - percBorda, 360, -90);

        g.setColor(Cor.PRETO);
        g.fillArc(((larguraAlt / 4) - (percBorda / 2)) + percDesloc / 2, ((larguraAlt / 4) - (percBorda / 2)) + percDesloc, (larguraAlt / 2) + percBorda, (larguraAlt / 2) + percBorda, 0, 360);

        g.setColor(curBlock == 5 ? Cor.ROXO : Cor.ROXO_ESCURO);
        g.fillArc((larguraAlt / 3) + percDesloc / 2, (larguraAlt / 3) + percDesloc, larguraAlt / 3, larguraAlt / 3, 0, 360);

        g.setColor(Cor.PRETO);
        g.fillRect(0, 0, largura, (int) (percDesloc / 1.3));

        g.setColor(Cor.BRANCO);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        if (sequencia.length() == 0) {
            g.drawString("", largura / 2, 2, Graphics.TOP | Graphics.HCENTER);
        } else {
            g.drawString("Jogada " + sequencia.length(), largura / 2, 2, Graphics.TOP | Graphics.HCENTER);
        }
    }

    private void pausa(int tempo) {
        try {
            Thread.currentThread().sleep(tempo);
        } catch (InterruptedException ex) {
        }
    }

    private void piscaBloco(int bloco, int tempo) {
        curBlock = bloco;
        repaint();
        serviceRepaints();
        pausa(tempo);
        curBlock = 0;
        repaint();
        serviceRepaints();
    }

    public void keyPressed(int keyCode) {

        //int tecla = getGameAction(keyCode);

        if ((emJogo == 0) && (keyCode >= KEY_NUM0 && keyCode <= KEY_NUM9)) {
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

            if (sequencia.length() > 0) {
                if (((keyCode == KEY_NUM1) && (sequencia.charAt(curSample) == KEY_NUM1)) //
                        || ((keyCode == KEY_NUM3) && (sequencia.charAt(curSample) == KEY_NUM2)) //
                        || ((keyCode == KEY_NUM5) && (sequencia.charAt(curSample) == KEY_NUM5)) //
                        || ((keyCode == KEY_NUM7) && (sequencia.charAt(curSample) == KEY_NUM3)) //
                        || ((keyCode == KEY_NUM9) && (sequencia.charAt(curSample) == KEY_NUM4))) {
                    curSample++;
                    if (curSample == sequencia.length()) {
                        emJogo = 1;
                        pausa(1000);
                        jogar();
                    }
                } else {
                    emJogo = 1;
                    for (int i = 0; i < 2; i++) {
                        for (int x = 1; x < 6; x++) {
                            piscaBloco(x, 100);
                        }
                    }
                    if (sequencia.length() > 2) {
                        Musica es = new Musica();
                        es.tocaMusica("/fim.mp3");
                    }
                    pausa(2000);
                    novoJogo();
                }
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdSair) {
            emJogo = 0;
            game.destroyApp(false);
        } else if (c == cmdJogar) {
            novoJogo();
        } else if (c == cmdSobre) {
            Display.getDisplay(game).setCurrent(sobre);
        } else if (c == cmdHelp) {
            Display.getDisplay(game).setCurrent(help);
        } else if (c == cmdVoltar) {
            Display.getDisplay(game).setCurrent(this);
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

    private void montaSobre() {
        Runtime runtime = Runtime.getRuntime();
        sobre = new TextBox(
                "Sobre",
                game.getAppProperty("MIDlet-Name") + "\n\n" +
                "Versão: " + game.getAppProperty("MIDlet-Version") + "\n\n" +
                "Autores:\n" + game.getAppProperty("MIDlet-Vendor").replace('-', '\n') + "\n\n" +
                "Genius era um brinquedo muito popular na década de 80 " +
                "e que buscava estimular a memorização de cores e sons. " +
                "Com um formato semelhante a um OVNI, possuía botões coloridos " +
                "que emitiam sons harmônicos e se iluminavam em sequência. " +
                "Cabia aos jogadores repetir o processo sem errar (Wikipedia).\n\n" +
                "Memória total: " + runtime.totalMemory() / 1024 + " kb" + "\n" +
                "Memória livre: " + runtime.freeMemory() / 1024 + " kb",
                500,
                TextField.ANY | TextField.UNEDITABLE);

        sobre.addCommand(cmdVoltar);
        sobre.setCommandListener(this);
    }

    private void montaHelp() {
        help = new TextBox(
                "Help",
                "Para jogar utilize as teclas 1 (verde), " +
                "3 (vermelho), 5 (roxo), 7 (amarelo) e 9 (azul).\n\n" +
                "A cada sequência correta o jogo irá acrescentar mais uma " +
                "tecla na sequência.\n\nCaso o jogador erre o jogo será reiniciado " +
                "automaticamente.",
                500,
                TextField.ANY | TextField.UNEDITABLE);
        help.addCommand(cmdVoltar);
        help.setCommandListener(this);
    }
}
