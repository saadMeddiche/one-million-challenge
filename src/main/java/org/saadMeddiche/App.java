package org.saadMeddiche;

import org.saadMeddiche.processes.TxtFileGenerator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        long start = System.nanoTime();

        TxtFileGenerator fg = new TxtFileGenerator();
        fg.generate("one-million-challenge");

        long end = System.nanoTime();

        System.out.println("Total time: " + (end-start)/1_000_000 + " ms");

    }
}
