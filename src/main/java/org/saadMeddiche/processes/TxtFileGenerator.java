package org.saadMeddiche.processes;

import org.saadMeddiche.models.TxtFileGeneratorResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Generate the .txt file that will be used for the challenge.
 * */
public class TxtFileGenerator {

    private final static Logger logger = Logger.getLogger(TxtFileGenerator.class.getName());
    private final static Random random = new Random();

    private final static String COLUMN_SEPARATOR = ";";
    private final static String TXT_EXTENSION = ".txt";
    private final static long DEFAULT_LINES = 1_000_000L;

    public TxtFileGeneratorResult generate(String filename) {
        return generate(filename, DEFAULT_LINES);
    }

    /**
     * This method does the necessary validations before it delegates generation work to {@link #mainProcess(File, long)}
    * */
    public TxtFileGeneratorResult generate(String filename, long lines) {

        if(filename == null || filename.isEmpty()) {
            return new TxtFileGeneratorResult(false, Optional.of("filename is null or empty"));
        }

        Optional<File> file = this.createFile(filename);

        if(file.isEmpty()) {
            return new TxtFileGeneratorResult(false, Optional.of("failed to create txt file"));
        }

        return mainProcess(file.get(), lines);

    }

    // ============================ MAINS ============================

    /**
     * This method contains the main logic of {@link TxtFileGenerator}.
     * */
    private TxtFileGeneratorResult mainProcess(File file, long lines) {

        logger.info("Generating content for file: " + file.getAbsolutePath());
        String content = this.buildFileContent(lines);

        logger.info("Writing content to file: " + file.getAbsolutePath());
        boolean isContentWritten = this.writeToFile(file, content);

        if(isContentWritten) {
            return new TxtFileGeneratorResult(true, Optional.empty());
        } else  {
            return new TxtFileGeneratorResult(false, Optional.of("failed to write content into txt file"));
        }

    }

    private boolean writeToFile(File file, String content) {

        try(FileWriter fileWriter = new FileWriter(file)) {

            fileWriter.write(content);

            return true;

        }
        catch (IOException ex) {
            logger.severe(String.format("failed to write to file %s due to reason %s", file.getAbsolutePath(), ex.getMessage()));
            return false;
        }

    }

    // TODO: this should be replaced, StringBuilder will break if number of lines is too long
    private String buildFileContent(long lines) {

        StringBuilder sb = new StringBuilder();

        for(long i = 0; i < lines; i++) {
            sb.append(buildLine(i));
        }

        return sb.toString();
    }

    private String buildLine(long id) {
        return id + COLUMN_SEPARATOR + UUID.randomUUID() + COLUMN_SEPARATOR + random.nextInt() + System.lineSeparator();
    }

    // ============================ HELPERS ============================

    private Optional<File> createFile(String filename) {

        try {

            File file = new File(addExtension(filename));

            boolean isFileCreated = file.createNewFile();

            if(isFileCreated) {
                return Optional.of(file);
            }
            else {
                logger.warning(String.format("Unable to create file %s due to existing file with same name.", filename));
                return Optional.empty();
            }

        }
        catch (IOException e) {
            logger.severe(String.format("Failed to create file %s due to I/O error.", filename));
            return Optional.empty();
        }

    }

    private String addExtension(String filename) {
        return filename + TXT_EXTENSION;
    }

}
