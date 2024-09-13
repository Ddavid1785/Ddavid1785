package Hangman;

public class mainHangman {
    public static void main(String[] args) {
        secHangman methods = new secHangman();

        System.out.println("Welcome to a game of hangman! Enjoy!");
        System.out.println("You will be allowed "+methods.allowedMistakes+" mistakes for this word!");
        while(methods.allowedMistakes>=1) {
            System.out.println("Word:" + methods.underscore);
            methods.inputChar();
            methods.checkforChar();
            if(methods.isItJover()){
                System.out.println("You have won congralatulations!");
                break;
            }
            if(methods.allowedMistakes<1){
                System.out.println("You have lost try again! The random word was:"+methods.randWord);
                break;
            }
            System.out.println("Letters you have guessed:"+methods.guessed);
        }

    }
}
