package org.saadMeddiche;

import org.saadMeddiche.processes.extractors.TxtFileExtractor;
import org.saadMeddiche.processes.extractors.impl.*;
import org.saadMeddiche.processes.extractors.impl.emptyVersions.*;

import org.saadMeddiche.processes.generators.TxtFileGenerator;
import org.saadMeddiche.processes.generators.impl.*;

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

        System.out.println("Choose script:");
        System.out.println("1- generation_script");
        System.out.println("2- extraction_script");
        System.out.println();

        String choice = sc.nextLine();

        switch (choice) {
            case "1": generation_script(); break;
            case "2": extraction_script(); break;
            default: extraction_script(); break;
        }

        System.out.println();
        System.out.println("Click to end");
        sc.nextLine();

    }

    public static void generation_script() {

        System.out.println("SCRIPT: generation_script #started");
        long start = System.nanoTime();

        TxtFileGenerator fg = new BufferedWriterFileGenerator();
        fg.generate("one-million-challenge", 100_000_000L);

        long end = System.nanoTime();
        System.out.printf("SCRIPT: generation_script #ended %s ms", (end-start)/1_000_000);

    }

    public static void extraction_script() {

        System.out.println("SCRIPT: extraction_script #started");
        long start = System.nanoTime();

        TxtFileExtractor tfe = new BufferReaderTxtFileExtractor();
        //TxtFileExtractor tfe = new EmptyBufferReaderTxtFileExtractor();

        //TxtFileExtractor tfe = new SeekableByteChannelTxtFileExtractor();
        //TxtFileExtractor tfe = new EmptySeekableByteChannelTxtFileExtractor();

        //TxtFileExtractor tfe = new StreamTxtFileExtractor();
        //TxtFileExtractor tfe = new EmptyStreamTxtFileExtractor();

        var result = tfe.extract("one-million-challenge");

        if(result.success()) {
            System.out.printf("SUM: %d", result.totalSum());
            System.out.println();
            System.out.println(-15673375262273L == result.totalSum());
            System.out.println();
        } else {
            result.failReason().ifPresent(System.err::println);
            System.out.println();
        }

        long end = System.nanoTime();
        System.out.printf("SCRIPT: extraction_script #ended %s ms", (end-start)/1_000_000);

    }

}
