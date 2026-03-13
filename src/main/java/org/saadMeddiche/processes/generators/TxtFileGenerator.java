package org.saadMeddiche.processes.generators;

import org.saadMeddiche.models.TxtFileGeneratorResult;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Generate the .txt file that will be used for the challenge.
 * */
public abstract class TxtFileGenerator {

    private final static Logger logger = Logger.getLogger(TxtFileGenerator.class.getName());

    protected final String COLUMN_SEPARATOR = ";";
    protected final String TXT_EXTENSION = ".txt";
    protected final long DEFAULT_LINES = 1_000_000L;

    /**
     * This method contains the main logic of {@link TxtFileGenerator}.
     * The concrete classes should generate content and write it into the file.
     * */
    protected abstract TxtFileGeneratorResult mainProcess(File file, long lines);

    /**
     * This method it is an overload of {@link #generate(String, long)} method, in case the number of lines is not provided
     * */
    public final TxtFileGeneratorResult generate(String filename) {
        return generate(filename, DEFAULT_LINES);
    }

    /**
     * This method does the necessary validations before it delegates generation work to {@link #mainProcess(File, long)}
     * */
    public final TxtFileGeneratorResult generate(String filename, long lines) {
        String treatedFilename = this.treatFilename(filename);

        Optional<File> file = this.createFile(treatedFilename);

        if(file.isEmpty()) {
            return new TxtFileGeneratorResult(false, Optional.of("failed to create txt file"));
        }

        return mainProcess(file.get(), lines);
    }

    // ============================ HELPERS ============================

    private Optional<File> createFile(String filename) {

        try {

            File file = new File(filename);

            boolean isFileCreated = file.createNewFile();

            if(isFileCreated) {
                return Optional.of(file);
            }
            else {
                logger.info(String.format("A file with the same name of %s already exists.", filename));
                return Optional.of(file);
            }

        }
        catch (IOException e) {
            logger.severe(String.format("Failed to create file %s due to I/O error.", filename));
            return Optional.empty();
        }

    }

    private String treatFilename(String filename) {

        if(filename == null || filename.isEmpty()) {
            return UUID.randomUUID() + TXT_EXTENSION;
        }

        if(filename.endsWith(TXT_EXTENSION)) {
            return filename;
        }

        return filename + TXT_EXTENSION;
    }

}