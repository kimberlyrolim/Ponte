import java.util.concurrent.Semaphore;

public class Ponte {

    private final int CAPACIDADE_MAXIMA = 3;
    private int carrosNaPonte = 0;
    private Sentido sentidoAtual = Sentido.NENHUM;

    // Semáforo binário para controle do acesso às variáveis de estado
    private final Semaphore mutex = new Semaphore(1);

    // Semáforos para gerenciar o controle de filas (bloqueio de carros)
    private final Semaphore filaNorte = new Semaphore(0);
    private final Semaphore filaSul = new Semaphore(0);

    // Contadores para o número de carros esperando em cada fila
    private int esperandoNorte = 0;
    private int esperandoSul = 0;

    public void entrarNaPonte(Carro carro) throws InterruptedException {
        mutex.acquire(); // Inicia a exclusão mútua para acessar as variáveis de estado

        try {
            // Verifica se a ponte está vazia ou se o carro pode entrar
            if (carrosNaPonte == 0) {
                // A ponte está vazia, então o carro define o sentido
                sentidoAtual = carro.getSentido();
                System.out.println(carro + " entrou e definiu o sentido " + sentidoAtual);
            } else {
                // Caso contrário, bloqueia o carro se o sentido for diferente
                if (sentidoAtual != carro.getSentido()) {
                    if (carro.getSentido() == Sentido.NORTE_SUL) {
                        esperandoNorte++;
                    } else {
                        esperandoSul++;
                    }
                    mutex.release(); // Libera o mutex antes de esperar
                    if (carro.getSentido() == Sentido.NORTE_SUL) {
                        filaNorte.acquire(); // Carro na fila norte aguarda
                    } else {
                        filaSul.acquire(); // Carro na fila sul aguarda
                    }
                    mutex.acquire(); // Retoma a exclusão mútua ao ser acordado
                }
            }

            // Verifica se a capacidade máxima foi atingida
            if (carrosNaPonte < CAPACIDADE_MAXIMA) {
                carrosNaPonte++;
                System.out.println(">>> [ENTRADA] " + carro + " entrou na ponte. Sentido: " + sentidoAtual + ". Total: " + carrosNaPonte);
            }

        } finally {
            mutex.release(); // Libera o mutex após manipular o estado
        }
    }

    public void sairDaPonte(Carro carro) throws InterruptedException {
        mutex.acquire(); // Inicia a exclusão mútua para acessar as variáveis de estado

        try {
            // Atualiza o número de carros na ponte
            carrosNaPonte--;
            System.out.println("<<< [SAIDA] " + carro + " saiu da ponte. Restantes: " + carrosNaPonte);

            // Se a ponte ficar vazia, acorda carros do sentido oposto
            if (carrosNaPonte == 0) {
                if (esperandoSul > 0) {
                    filaSul.release(); // Libera um carro na fila sul
                } else if (esperandoNorte > 0) {
                    filaNorte.release(); // Libera um carro na fila norte
                } else {
                    sentidoAtual = Sentido.NENHUM; // Se não houver carros esperando, a ponte fica vazia
                    System.out.println("--- PONTE VAZIA ---");
                }
            }
        } finally {
            mutex.release(); // Libera o mutex após manipular o estado
        }
    }

    public int getCarrosNaPonte() {
        return carrosNaPonte;
    }

    public Sentido getSentidoAtual() {
        return sentidoAtual;
    }
}