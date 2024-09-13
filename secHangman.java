package Hangman;

import java.util.Scanner;
import java.util.Random;
public class secHangman {

    String[] words = new String[10];
    String underscore = "";
    char guess;
    String guessed=" ";
    String randWord;
    int allowedMistakes;

    public secHangman() {
        this.words = words;
        this.underscore = underscore;
        this.guess = guess;
        this.guessed = guessed;
        this.randWord = randWord;
        this.allowedMistakes = allowedMistakes;
        initalizeWords();
        chooseWord();
        initializeUnderscore();

    }

    public void initalizeWords() {
        words[0] = "Birthday";
        words[1] = "aequelatasiodestablismentariesim";
        words[2] = "Lepidopterology";
        words[3] = "Fissigemmation";
        words[4] = "Necessitarianism";
        words[5] = "Nuke";
        words[6] = "ChocolateCake";
        words[7] = "BirthdayCake";
        words[8] = "Anicent";
        words[9] = "CheesePizza";
    }

    public void initializeUnderscore() {
        for (int i = 0; i < randWord.length(); i++) {
            underscore += "_";
        }
    }

    public void inputChar() {
        System.out.println("Input your guess:");
        Scanner s = new Scanner(System.in);
        guess = s.next().charAt(0);
        guessed += guess;
        guessed+=", ";
    }

    public void chooseWord() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(10);
        randWord = words[randomNumber];
        switch (randomNumber) {
            case 0:
                allowedMistakes = 5+1;
                break;
            case 1:
                allowedMistakes = 10+1;
                break;
            case 2:
                allowedMistakes = 7+1;
                break;
            case 3:
                allowedMistakes = 8+1;
                break;
            case 4:
                allowedMistakes = 9+1;
                break;
            case 5:
                allowedMistakes = 3+1;
                break;
            case 6:
                allowedMistakes = 6+1;
                break;
            case 7:
                allowedMistakes = 5+1;
                break;
            case 8:
                allowedMistakes = 4+1;
                break;
            case 9:
                allowedMistakes = 5+1;
                break;
            default:
                allowedMistakes = 5+1;
                break;
        }
    }

    public void checkforChar() {
        int found = 0;
            for (int j = 0; j < randWord.length(); j++) {

                if (guess == randWord.toLowerCase().charAt(j)) {
                    underscore = underscore.substring(0, j) + guess + underscore.substring(j + 1);
                    found = 1;
            }
        }
        if (found == 0) {
            allowedMistakes--;
            System.out.println("There is no char:" + guess + " You have:" + allowedMistakes + " mistakes left.");
        }
    }

    public boolean isItJover() {
        for (int i = 0; i <randWord.length(); i++) {
            if (underscore.charAt(i) == '_') {
                return false;
            }
        }
        return true;
    }
}


