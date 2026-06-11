package org.saadMeddiche;

import org.saadMeddiche.processes.extractors.TxtFileExtractor;
import org.saadMeddiche.processes.extractors.impl.*;

import org.saadMeddiche.processes.generators.TxtFileGenerator;
import org.saadMeddiche.processes.generators.impl.*;
import org.saadMeddiche.utils.Timer;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{

    public static final long LINES_TO_GENERATE = 100_000_000L;
    public static final String FILE_NAME_TO_READ_FROM = "one-million-challenge";
    public static final String FILE_NAME_TO_WRITE_INTO = "one-million-challenge";

    private static final int SCRIPT_MOD = 0; // == 0 -> generation ; != 0 -> extraction

    private static final TxtFileGenerator GENERATOR = new SeekableByteChannelTxtFileGenerator();
    private static final TxtFileExtractor EXTRACTOR = new SeekableByteChannelTxtFileExtractor();

    public static void main( String[] args )
    {

        Scanner sc = new Scanner(System.in);

        System.out.println("Click to start");
        sc.nextLine();

        if(SCRIPT_MOD == 0) {
            generation_script();
        }
        else {
            extraction_script();
        }

        System.out.println();
        System.out.println("Click to end");
        sc.nextLine();

    }

    public static void generation_script() {

        System.out.println("┌SCRIPT: generation_script #started");
        System.out.println("│");

        var response = Timer.stopwatch(GENERATOR);

        var generationResult = response.result();

        if(generationResult.success()) {
            System.out.println("├SCRIPT: generation_script #success");
        }
        else {
            System.out.printf("├SCRIPT: generation_script #failed #reason(%s)", generationResult.failReason());
        }

        System.out.println("│");
        System.out.printf("└SCRIPT: generation_script #ended #time(%s ms)", response.time());
        System.out.println();


    }

    public static void extraction_script() {

        System.out.println("┌SCRIPT: extraction_script #started");
        System.out.println("│");

        var response = Timer.stopwatch(EXTRACTOR);

        var extractionResult = response.result();

        if(extractionResult.success()) {
            System.out.printf("├SCRIPT: extraction_script #success #time(%s ms) #sum(%s$%b)",
                    response.time(), extractionResult.totalSum(), -15673375262273L == extractionResult.totalSum());
        } else {
            System.out.printf("├SCRIPT: extraction_script #failed #reason(%s)", extractionResult.failReason());
        }

        System.out.println("│");
        System.out.printf("└SCRIPT: extraction_script #ended #time(%s ms)", response.time());
        System.out.println();

    }

}
