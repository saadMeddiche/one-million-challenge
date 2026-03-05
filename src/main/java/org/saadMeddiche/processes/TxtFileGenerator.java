package org.saadMeddiche.processes;

import org.saadMeddiche.models.TxtFileGeneratorResult;

/**
 * Generate the .txt file that will be used for the challenge.
 * */
public interface TxtFileGenerator {

    String COLUMN_SEPARATOR = ";";
    String TXT_EXTENSION = ".txt";
    long DEFAULT_LINES = 1_000_000L;

    TxtFileGeneratorResult generate(String filename);

    TxtFileGeneratorResult generate(String filename, long lines);

}