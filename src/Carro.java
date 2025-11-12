import java.util.Random;

public class Carro extends Thread {
    
    private int id;
    private Sentido sentido;
    private Ponte ponte;
    private static final Random random = new Random();

    public Carro(int id, Sentido sentido, Ponte ponte) {
        this.id = id;
        this.sentido = sentido;
        this.ponte = ponte;
    }

    @Override
    public void run() {
        try {
            // 1. Simula um tempo variável até o carro chegar na ponte
            Thread.sleep(random.nextInt(10000));

            // 2. Tenta entrar na ponte (método crítico)
            System.out.println(this + " querendo entrar no sentido " + this.sentido);
            ponte.entrarNaPonte(this);

            // 3. Simula o tempo de travessia da ponte
            Thread.sleep(3000 + random.nextInt(3000));

            // 4. Sai da ponte (método crítico)
            ponte.sairDaPonte(this);

        } catch (InterruptedException e) {
            System.out.println("Carro " + id + " foi interrompido.");
        }
    }

    public Sentido getSentido() {
        return sentido;
    }

    @Override
    public String toString() {
        return "Carro #" + id + " (" + sentido + ")";
    }
}