package org.example;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner S = new Scanner(System.in);
        System.out.println("Choose the application interface:");
        System.out.println("1. CLI");
        System.out.println("2. GUI (Not available yet!)");
        int choice = S.nextInt();

        if (choice == 1)
        {
            IMDB.getInstance().run();
        }
        else
        {
            main(args);
        }
    }
}
