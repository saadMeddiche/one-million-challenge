package org.saadMeddiche.processes;

import org.saadMeddiche.models.TxtFileGeneratorResult;

import java.util.Optional;

/**
 * Generate the .txt file that will be used for the challenge.
 * */
public class TxtFileGenerator {

    private final static long DEFAULT_LINES = 1_000_000_000L;

    public TxtFileGeneratorResult generate(String filename) {
        return generate(filename, DEFAULT_LINES);
    }

    /**
     * This method handles only input validation, it delegates generation work to {@link #mainProcess(String, long)}
    * */
    public TxtFileGeneratorResult generate(String filename, long lines) {

        if(filename == null || filename.isEmpty()) {
            return new TxtFileGeneratorResult(false, Optional.of("Filename is null or empty"));
        }

        return generate(filename, lines);

    }

    private TxtFileGeneratorResult mainProcess(String filename, long lines) {
        return new TxtFileGeneratorResult(true, Optional.empty());
    }

}
