package org.example;

import java.util.Random;
import java.util.Scanner;

public class TicTacToeWithPC {

    private static final String[][] GRID = new String[3][3];
    private static final String FIGURE_X = "X";
    private static final String FIGURE_O = "O";
    private static boolean CHECK_OF_GAME = true;

    // Печать поля
    static void printGrid() {
        System.out.println("---------");
        System.out.println("|" + " " + GRID[0][0] + " " + GRID[0][1] + " " + GRID[0][2] + " " + "|");
        System.out.println("|" + " " + GRID[1][0] + " " + GRID[1][1] + " " + GRID[1][2] + " " + "|");
        System.out.println("|" + " " + GRID[2][0] + " " + GRID[2][1] + " " + GRID[2][2] + " " + "|");
        System.out.println("---------");
    }

    // Ход человека
    static void makeMoveHuman() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите координаты: ");
        try {
            int userInputRow = scanner.nextInt() - 1;
            int userInputColumn = scanner.nextInt() - 1;
            if ((userInputRow > 2 || userInputRow < 0) || (userInputColumn > 2 || userInputColumn < 0)) {
                System.out.println("Координаты должны быть от 1 до 3!");
                makeMoveHuman();
            } else if (!GRID[userInputRow][userInputColumn].equals("X") && !GRID[userInputRow][userInputColumn].equals("O") ){
                whoMove(GRID, userInputRow, userInputColumn);
            } else {
                System.out.println("This cell is occupied! Choose another one!");
                makeMoveHuman();
            }
        }
        catch (Exception e) {
            System.out.println("Вы должны вводить числа!");
            makeMoveHuman();
        }
    }


    static void makeMoveComp(){
        System.out.println("Making move by comp");
        Random random = new Random();
        boolean res = true;
        while (res) {
            int compInputRow = random.nextInt(4 - 1);
            int compInputColumn = random.nextInt(4 - 1);
            if (!GRID[compInputRow][compInputColumn].equals("X") && !GRID[compInputRow][compInputColumn].equals("O") ){
                whoMove(GRID, compInputRow, compInputColumn);
                res = false;
            }
        }
    }

    // Проверка на очередь хода
    static void whoMove(String[][] grid, int inputRow, int inputColumn) {
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j].equals("X")) xCount++;
                if (grid[i][j].equals("O")) oCount++;
            }
        }
        if (xCount == oCount) grid[inputRow][inputColumn] = "X";
        if (xCount > oCount) grid[inputRow][inputColumn] = "O";
    }

    // Проверка на окончание игры
    static boolean checkEndGame(String[][] grid) {
        if (checkWinner(grid, FIGURE_X)) {
            printGrid();
            System.out.println("X wins");
            CHECK_OF_GAME = false;
            return true;
        } else if (checkWinner(grid, FIGURE_O)) {
            printGrid();
            System.out.println("O wins");
            CHECK_OF_GAME = false;
            return true;
        } else if (checkDraw(grid)) {
            printGrid();
            System.out.println("Draw");
            CHECK_OF_GAME = false;
            return true;
        } else {
            return false;
        }
    }

    // Проверка на победу
    static boolean checkWinner(String[][] grid, String symbol) {
        return (grid[0][0].equals(symbol) && grid[0][1].equals(symbol) && grid[0][2].equals(symbol)) ||
                (grid[1][0].equals(symbol) && grid[1][1].equals(symbol) && grid[1][2].equals(symbol)) ||
                (grid[2][0].equals(symbol) && grid[2][1].equals(symbol) && grid[2][2].equals(symbol)) ||

                (grid[0][0].equals(symbol) && grid[1][0].equals(symbol) && grid[2][0].equals(symbol)) ||
                (grid[0][1].equals(symbol) && grid[1][1].equals(symbol) && grid[2][1].equals(symbol)) ||
                (grid[0][2].equals(symbol) && grid[1][2].equals(symbol) && grid[2][2].equals(symbol)) ||

                (grid[0][0].equals(symbol) && grid[1][1].equals(symbol) && grid[2][2].equals(symbol)) ||
                (grid[0][2].equals(symbol) && grid[1][1].equals(symbol) && grid[2][0].equals(symbol));
    }

    // Проверка на ничью
    static boolean checkDraw(String[][] grid) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j].equals("X") || grid[i][j].equals("O")) count++;
            }
        }
        return count == 9;
    }

    // Игра человека против человека
    static void gameHumanHuman() {
        while (CHECK_OF_GAME) {
            printGrid();
            makeMoveHuman();
            checkEndGame(GRID);
        }
    }

    // Игра человека против компьютера
    static void gameCompHuman() {
        while (CHECK_OF_GAME) {
            printGrid();
            makeMoveHuman();
            if (!checkEndGame(GRID)) {
                printGrid();
                makeMoveComp();
                checkEndGame(GRID);
            }
        }
    }

    // Игра компьютера против компьютера
    static void gameCompComp () {
        while (CHECK_OF_GAME) {
            printGrid();
            makeMoveComp();
            checkEndGame(GRID);
        }
    }

    // Заполнение поля пустыми значениями
    static void fillGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                GRID[i][j] = " ";
            }
        }
    }

    public static void main(String[] args) {
        fillGrid();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите параметры игры: ");
        System.out.println("1. Человек против человека");
        System.out.println("2. Человек против компьютера");
        System.out.println("3. Компьютер против компьютера");

        boolean checking = true;

        while (checking) {
            int input = scanner.nextInt();
            switch (input) {
                case 1 -> {
                    gameHumanHuman();
                    checking = false;
                }
                case 2 -> {
                    gameCompHuman();
                    checking = false;
                }
                case 3 -> {
                    gameCompComp();
                    checking = false;
                }
                default -> System.out.println("Не правильные параметры!");
            }
        }
    }
}

