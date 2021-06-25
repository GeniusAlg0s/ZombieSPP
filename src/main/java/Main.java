import com.fasterxml.jackson.core.JacksonException;
import com.zombiecastlerush.util.Game;

class Main {
    public static void main(String[] args) {
        Game ZombieCastleRush = Game.getInstance();
        try {
            ZombieCastleRush.start();
            System.out.println("Timer is up. The end ...");
        } catch (JacksonException je) {
            System.out.println(je.getMessage());
        }
    }
}
