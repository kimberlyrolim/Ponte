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
            Thread.sleep(random.nextInt(10000));

            System.out.println(this + " querendo entrar no sentido " + this.sentido);
            ponte.entrarNaPonte(this);

            //tempo de travessia da ponte
            Thread.sleep(3000 + random.nextInt(3000));

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