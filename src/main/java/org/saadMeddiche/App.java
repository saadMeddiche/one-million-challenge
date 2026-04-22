package org.saadMeddiche;

import org.saadMeddiche.processes.extractors.TxtFileExtractor;
import org.saadMeddiche.processes.extractors.impl.*;
import org.saadMeddiche.processes.extractors.impl.emptyVersions.*;

import org.saadMeddiche.processes.generators.TxtFileGenerator;
import org.saadMeddiche.processes.generators.impl.*;

import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private final static Map<TxtFileExtractor, String> EXTRACTORS = Map.of(
            new EmptyBufferReaderTxtFileExtractor(),  "emptyBufferReader",
            new EmptySeekableByteChannelTxtFileExtractor(),  "emptySeekableByteChannel",
            new EmptyStreamTxtFileExtractor(),  "emptyStreamTxtFile"
    );

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
        EXTRACTORS.forEach(App::extraction_script);
    }

    public static void extraction_script(TxtFileExtractor tfe, String extractorName) {

        System.out.println();
        System.out.println("SCRIPT: extraction_script(" + extractorName + ") #started");

        long start = System.nanoTime();
        var result = tfe.extract("one-million-challenge");
        long end = System.nanoTime();

        if(result.success()) {

            System.out.printf("SCRIPT: extraction_script(%s) #ended #time(%s ms) #sum(%s$%b)",
                    extractorName, (end-start)/1_000_000, result.totalSum(), -15673375262273L == result.totalSum());

        } else {

            result.failReason().ifPresent(System.err::println);
            System.out.println();

        }

        System.out.println();

    }

}
