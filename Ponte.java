/**
 * Classe que representa a ponte (recurso compartilhado).
 */
public class Ponte {

    // Capacidade máxima da ponte
    private final int CAPACIDADE_MAXIMA = 3;

    // Estado atual da ponte
    private int carrosNaPonte = 0;
    private Sentido sentidoAtual = Sentido.NENHUM;

    public void entrarNaPonte(Carro carro) {
        
        // LÓGICA INCORRETA (APENAS PARA DEMONSTRAÇÃO INICIAL)
        // Esta lógica permite que carros entrem e violem as regras.
        
        if (carrosNaPonte == 0) {
            this.sentidoAtual = carro.getSentido();
        }
        this.carrosNaPonte++;
        
        System.out.println(">>> [ENTRADA] " + carro + " entrou na ponte. Sentido: " + this.sentidoAtual + ". Total: " + this.carrosNaPonte);
    }

       public void sairDaPonte(Carro carro) {

        // LÓGICA INCORRETA (APENAS PARA DEMONSTRAÇÃO INICIAL)
        this.carrosNaPonte--;

        System.out.println("<<< [SAÍDA]   " + carro + " saiu da ponte. Restantes: " + this.carrosNaPonte);

        if (this.carrosNaPonte == 0) {
            this.sentidoAtual = Sentido.NENHUM;
            System.out.println("--- PONTE VAZIA ---");
        }
    }

    public int getCarrosNaPonte() {
        return carrosNaPonte;
    }

    public Sentido getSentidoAtual() {
        return sentidoAtual;
    }
}