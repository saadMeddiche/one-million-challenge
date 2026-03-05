package org.saadMeddiche;

import org.saadMeddiche.processes.TxtFileGenerator;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Scanner sc = new Scanner(System.in);

        System.out.println("Click to start");
        sc.nextLine();

        long start = System.nanoTime();

        TxtFileGenerator fg = new TxtFileGenerator();
        fg.generate("one-million-challenge");

        long end = System.nanoTime();

        System.out.println("Total time: " + (end-start)/1_000_000 + " ms");

        System.out.println("Click to end");
        sc.nextLine();

    }
}
