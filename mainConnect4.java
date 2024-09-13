package Connect4;


public class mainConnect4 {

    public static void main(String[] args) {
        secConnect4 functions = new secConnect4();



        while (!functions.didGameEndO && !functions.didGameEndX && !functions.tie) {
            functions.printCol();
            functions.printBoard();
            functions.makeMoveX();
            if (functions.checkForWinX()){
                functions.printCol();
                functions.printBoard();
                System.out.println("Player X wins!");
                break;
            }
            if (functions.isItATie()) {
                functions.printCol();
                functions.printBoard();
                System.out.println("It is a tie");
                break;
            }
            functions.printCol();
            functions.printBoard();
            functions.makeMoveO();
            if (functions.checkForWinO()) {
                functions.printCol();
                functions.printBoard();
                System.out.println("Player O wins!");
                break;
            }
            if (functions.isItATie()) {
                functions.printCol();
                functions.printBoard();
                System.out.println("It is a tie");
                break;
            }
        }
    }
}
