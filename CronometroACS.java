import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CronometroACS {

    // Parâmetros.
    private JLabel contagemTempo;
    private Timer tm;
    private int contador = 0;
    private boolean rodando = false;
    private boolean pausado = false;
    private boolean finalizado = false;

    // Método Principal do Cronômetro.
    public static void main(String[] args) {
        CronometroACS Start = new CronometroACS();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Start.init();
            }
        });
    }

    // Métodos auxiliares.
    public void init(){
        // Janela.
        JFrame janela = new JFrame("Cronômetro — AlexsandroCS");
        janela.setSize(300,300);
        janela.setAlwaysOnTop(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());

        // Fontes.
        contagemTempo = new JLabel("00:00:00");
        contagemTempo.setFont(new Font(contagemTempo.getName(), Font.PLAIN, 22));
        janela.add(contagemTempo, BorderLayout.CENTER);

        // Grid do Painel.
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(1,3));

        // Botão Iniciar | Play.
        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!rodando){
                    tm = new Timer();
                    rodando = true;

                    tm.scheduleAtFixedRate(new TimerTask(){
                        @Override
                        public void run(){
                            contador++;

                            int seg = contador % 60;
                            int min = contador / 60;
                            int hora = min / 60;
                            min %= 60;

                            contagemTempo.setText(String.format("%02d:%02d:%02d",hora,min,seg));
                        }
                    },1000,1000);
                }
            }
        });

        // Botão Pausar | Pause.
        JButton btnPausar = new JButton("Pausar");
        btnPausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pausado){
                    tm.cancel();
                    rodando = false;
                    pausado = true;
                    finalizado = false;
                }
            }
        });

        // Botão Finalizar | Finish.
        JButton btnFinalizar = new JButton("Finalizar");
        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!finalizado){
                    tm.cancel();
                    rodando = false;
                    pausado = false;
                    finalizado = true;
                    int seg = 00;
                    int min = 00;
                    int hora = 00;
                    contagemTempo.setText(String.format("%02d:%02d:%02d",hora, min, seg)); // Zerar o contador.
                    contador = 0;
                }
            }
        });

        // Adicionando comando de Botões.
        painel.add(btnIniciar);
        painel.add(btnPausar);
        painel.add(btnFinalizar);

        // Adicionando a Janela modo Usuário.
        janela.add(painel, BorderLayout.EAST);
        janela.pack();
        janela.setVisible(true);
    }
}