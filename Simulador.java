
import java.util.Random;

public class Simulador {

    public static void main(String[] args) {
        final int TOTAL_CARROS = 20; // Número total de carros na simulação
        Ponte ponte = new Ponte();
        Random random = new Random();

        System.out.println("--- Iniciando Simulação da Ponte Estreita (SEM CONTROLE) ---");

        for (int i = 0; i < TOTAL_CARROS; i++) {
            
            // Decide aleatoriamente o sentido do próximo carro
            Sentido sentido = (random.nextBoolean()) ? Sentido.NORTE_SUL : Sentido.SUL_NORTE;
            
            Carro carro = new Carro(i, sentido, ponte);
            carro.start();
            
            // Pausa aleatória entre a criação de carros
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Simulador interrompido.");
            }
        }
        
        System.out.println("--- Todos os carros (" + TOTAL_CARROS + ") foram criados e estão a caminho da ponte ---");
    }
}

