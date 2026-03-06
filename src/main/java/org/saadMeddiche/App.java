package org.saadMeddiche;

import org.saadMeddiche.processes.TxtFileGenerator;
import org.saadMeddiche.processes.impl.ChunkTxtFileGenerator;
import org.saadMeddiche.processes.impl.SimpleTxtFileGenerator;
import org.saadMeddiche.processes.impl.StringBuilderTxtFileGenerator;

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

        generation_script();

        System.out.println("Click to end");
        sc.nextLine();

    }

    public static void generation_script() {

        System.out.println("SCRIPT: generation_script #started");
        long start = System.nanoTime();

        TxtFileGenerator fg = new ChunkTxtFileGenerator();
        //TxtFileGenerator fg = new SimpleTxtFileGenerator();
        //TxtFileGenerator fg = new StringBuilderTxtFileGenerator();
        fg.generate("one-million-challenge", 37_000_000L);

        long end = System.nanoTime();

        System.out.printf("SCRIPT: generation_script #ended %s ms", (end-start)/1_000_000);
        System.out.println();

    }

}
