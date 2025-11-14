import java.util.concurrent.Semaphore;

public class Ponte {

    private final int CAPACIDADE_MAXIMA = 3;
    private int carrosNaPonte = 0;
    private Sentido sentidoAtual = Sentido.NENHUM;

    private final Semaphore mutex = new Semaphore(1);

    // Semáforos para gerenciar o controle de filas
    private final Semaphore filaNorte = new Semaphore(0);
    private final Semaphore filaSul = new Semaphore(0);

    private int esperandoNorte = 0;
    private int esperandoSul = 0;

    public void entrarNaPonte(Carro carro) throws InterruptedException {
        mutex.acquire(); // Inicia omutex pra variáveis de estado

        try {
            // Verifica se a ponte está vazia ou se o carro pode entrar
            if (carrosNaPonte == 0) {
                sentidoAtual = carro.getSentido();
                System.out.println(carro + " entrou e definiu o sentido " + sentidoAtual);
            } else {
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
                    mutex.acquire(); // Retoma o mutex ao ser acordado
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
        mutex.acquire(); 

        try {
         
            carrosNaPonte--;
            System.out.println("<<< [SAIDA] " + carro + " saiu da ponte. Restantes: " + carrosNaPonte);

            // Se a ponte ficar vazia, acorda carros do sentido oposto
            if (carrosNaPonte == 0) {
                if (esperandoSul > 0) {
                    filaSul.release(); // Libera um carro na fila sul
                } else if (esperandoNorte > 0) {
                    filaNorte.release(); // Libera um carro na fila norte
                } else {
                    sentidoAtual = Sentido.NENHUM;
                    System.out.println("--- PONTE VAZIA ---");
                }
            }
        } finally {
            mutex.release();
        }
    }

    public int getCarrosNaPonte() {
        return carrosNaPonte;
    }

    public Sentido getSentidoAtual() {
        return sentidoAtual;
    }
}