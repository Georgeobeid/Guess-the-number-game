package george.obeid.console;

import george.obeid.Game;
import george.obeid.MessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
public class ConsoleNumberGuess  {

    //fields
    private final Game game;
    private final MessageGenerator messageGenerator;

    public ConsoleNumberGuess(Game game, MessageGenerator messageGenerator) {
        this.game = game;
        this.messageGenerator = messageGenerator;
    }

    //events
    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        log.info("Start ----> Container ready fo use");
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println(messageGenerator.getMainMessage());
            System.out.println(messageGenerator.getResultMessage());
            int guess = scanner.nextInt();
            game.setGuess(guess);
            game.check();
            if (game.isGameWon() || game.isGameLost()){
                System.out.println(messageGenerator.getResultMessage());
                System.out.println("Play again Y/N?");
                String playAgainString=scanner.next();
                if (!playAgainString.equals("y")){
                    break;
                }
                game.reset();
            }
        }
    }
}
