package main;

import java.util.Scanner;

public class Main {
    private static int choice;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int quitMessage = 9;
        do {
            System.out.println("---------- MENU ----------");
            System.out.println("1) Place card");
            System.out.println("2) Attack");
            System.out.println("3) End turn");
            System.out.println("9) Quit game");
            System.out.println("---------- **** ----------");
            choice = sc.nextInt();
            checkChoice(choice);
        } while(choice != quitMessage);
    }

    public static void checkChoice(int choice) {
        switch (choice) {
            case 1:
                printCaseOne();
                break;
            case 2:
                printCaseTwo();
                break;
            case 3:
                printCaseThree();
                break;
            case 9:
                printCaseNine();
                break;
                default:
                    break;
        }
    }

    private static void printCaseOne() {
        System.out.println("---------- PLACE CARD ----------");
        System.out.println("1) Card1");
        System.out.println("2) Card2");
        System.out.println("3) Card3");
        System.out.println("4) Card4");
        System.out.println("5) Card5");
        System.out.println("---------- *********** ----------");
        choice = sc.nextInt();
        //TODO Place card in next available spot

        System.out.println("You placed card: " + choice);
    }

    private static void printCaseTwo() {
        System.out.println("---------- ATTACK CARD ----------");
        System.out.println("1) Card1");
        System.out.println("2) Card2");
        System.out.println("3) Card3");
        System.out.println("4) Card4");
        System.out.println("5) Card5");
        System.out.println("---------- *********** ----------");

        choice = sc.nextInt();
        printEnemyCards(choice);
    }

    private static void printCaseThree() {
        System.out.println("---------- END TURN ----------");
    }

    private static void printCaseNine() {
        System.out.println("---------- QUIT GAME ----------");
    }

    private static void printEnemyCards(int choice) {
        System.out.println("---------- ENEMY CARDS ----------");
        System.out.println("1) Card1");
        System.out.println("2) Card2");
        System.out.println("3) Card3");
        System.out.println("4) Card4");
        System.out.println("5) Card5");
        System.out.println("---------- *********** ----------");

        int choice2 = sc.nextInt();
        System.out.printf("You attacked enemy card %d, with card %d\n", choice2,choice);
    }
}
