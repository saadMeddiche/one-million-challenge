package org.saadMeddiche.processes;

import org.saadMeddiche.models.TxtFileGeneratorResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
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

    /**
     * This method is just an overload for {@link #generate(String, long)} if no lines are provided}
     * */
    public TxtFileGeneratorResult generate(String filename) {
        return generate(filename, DEFAULT_LINES);
    }

    /**
     * This method does the necessary validations before it delegates generation work to {@link #mainProcess(File, long)}
    * */
    public TxtFileGeneratorResult generate(String filename, long lines) {

        String treatedFilename = this.treatFilename(filename);

        Optional<File> file = this.createFile(treatedFilename);

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
        String content = this.buildFileContentUsingList(lines);

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

    // 25_000_000 lines -> 4GB
    // pre-allocated capacity: 18_382 ms, 20250 ms || no pre-allocated capacity: 19_886 ms, 22_963 ms
    private String buildFileContentUsingList(long lines) {

        List<String> newLines = new ArrayList<>();

        for(long i = 0 ; i < lines; i++) {
            newLines.add(buildLine(i));
        }

        return String.join(System.lineSeparator(), newLines);

    }

    private String buildLine(long id) {
        return id + COLUMN_SEPARATOR + UUID.randomUUID() + COLUMN_SEPARATOR + random.nextInt();
    }

    // 25_000_000 lines -> 4GB
    // pre-allocated capacity: 15_661 ms, 27_187 ms || no pre-allocated capacity: 17_211 ms, 17_767 ms
    private String buildFileContentUsingBuilder(long lines) {

        StringBuilder sb = new StringBuilder();

        for(long i = 0; i < lines; i++) {
            sb.append(buildSeparatedLine(i));
        }

        return sb.toString();
    }

    private String buildSeparatedLine(long id) {
        return id + COLUMN_SEPARATOR + UUID.randomUUID() + COLUMN_SEPARATOR + random.nextInt() + System.lineSeparator();
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
                logger.warning(String.format("Unable to create file %s due to existing file with same name.", filename));
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