package Connect4;

import java.util.Scanner;

public class secConnect4{

    char[][] board = new char[6][7];
    int row;
    char userInputChar;
    int col;
    boolean tie = false;
    boolean didGameEndX = false;

    boolean didGameEndO = false;
    public secConnect4(){
        this.board = board;
        this.row = row;
        this.userInputChar = userInputChar;
        this.col = col;
        this.tie = false;
        this.didGameEndX = didGameEndX;
        this.didGameEndO = didGameEndO;
        initializeBoard();
    }
    public void printCol(){

        System.out.println("  A B C D E F G");

    }
    private void initializeBoard(){
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                board[i][j]='.';
            }
        }
    }
    public void printBoard(){
        int row = 1;
        for(int i=0;i<6;i++) {
            System.out.print(row+" ");
            for(int j=0;j<7;j++) {
                System.out.print(board[i][j]+" ");

            }
            System.out.println();
            row++;
            }
    }

    public void makeMoveX(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the row for X:");
        row=s.nextInt();
        row-=1;
        System.out.println("Enter the column for X:");
        userInputChar=s.next().toUpperCase().charAt(0);
        if (userInputChar == 'A') {
            col=0;
        }else if(userInputChar=='B'){
            col=1;
        }else if(userInputChar=='C'){
            col=2;
        }else if(userInputChar=='D'){
            col=3;
        }else if(userInputChar=='E'){
            col=4;
        }else if(userInputChar=='F'){
            col=5;
        }else if(userInputChar=='G'){
            col=6;
        }
        board[row][col] = 'X';
        }
    public void makeMoveO(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the row for O:");
        row=s.nextInt();
        row-=1;
        System.out.println("Enter the column for O:");
        userInputChar=s.next().toUpperCase().charAt(0);
        if (userInputChar == 'A') {
            col=0;
        }else if(userInputChar=='B'){
            col=1;
        }else if(userInputChar=='C'){
            col=2;
        }else if(userInputChar=='D'){
            col=3;
        }else if(userInputChar=='E'){
            col=4;
        }else if(userInputChar=='F'){
            col=5;
        }else if(userInputChar=='G'){
            col=6;
        }
        board[row][col] = 'O';
    }
    public boolean isItATie() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == '.') {
                    return false;
                }
            }
        }
        tie = true;
        return true;
    }
    public boolean checkForWinX() {

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 'X' && board[row][col + 1] == 'X' &&
                        board[row][col + 2] == 'X' && board[row][col + 3] == 'X') {
                    didGameEndX=true;
                    break;
                }
            }
        }

        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col] == 'X' && board[row + 1][col] == 'X' &&
                        board[row + 2][col] == 'X' && board[row + 3][col] == 'X') {
                    didGameEndX=true;
                    break;
                }
            }
        }
// row 3 col 0
        //
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 'X' && board[row - 1][col + 1] == 'X' &&
                        board[row - 2][col + 2] == 'X' && board[row - 3][col + 3] == 'X') {
                    didGameEndX=true;
                    break;
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 'X' && board[row + 1][col + 1] == 'X' &&
                        board[row + 2][col + 2] == 'X' && board[row + 3][col + 3] == 'X') {
                    didGameEndX=true;
                    break;
                }
            }
        }
        return didGameEndX;
    }
    public boolean checkForWinO() {

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 'O' && board[row][col + 1] == 'O' &&
                        board[row][col + 2] == 'O' && board[row][col + 3] == 'O') {
                    didGameEndO=true;
                    break;
                }
            }
        }

        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col] == 'O' && board[row + 1][col] == 'O' &&
                        board[row + 2][col] == 'O' && board[row + 3][col] == 'O') {
                    didGameEndO=true;
                    break;
                }
            }
        }

        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 'O' && board[row - 1][col + 1] == 'O' &&
                        board[row - 2][col + 2] == 'O' && board[row - 3][col + 3] == 'O') {
                    didGameEndO=true;
                    break;
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 'O' && board[row + 1][col + 1] == 'O' &&
                        board[row + 2][col + 2] == 'O' && board[row + 3][col + 3] == 'O') {
                    didGameEndO=true;
                    break;
                }
            }
        }
        return didGameEndO;
    }
}

