package org.saadMeddiche.processes.impl;

import org.saadMeddiche.models.TxtFileGeneratorResult;
import org.saadMeddiche.processes.TxtFileGenerator;
import org.saadMeddiche.utils.FasterRandom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Generate the .txt file that will be used for the challenge. It uses strait forward generation.
 * <p>
 * Loop to generate content is a single string — <b>using StringBuilder to store lines</b>, then writes the content into the file.
 * This means the higher lines to generate. The higher memory/time it takes.
 * */
public class StringBuilderTxtFileGenerator extends TxtFileGenerator {

    private final static Logger logger = Logger.getLogger(StringBuilderTxtFileGenerator.class.getName());

    /**
     * This method contains the main logic of {@link StringBuilderTxtFileGenerator}.
     * */
    protected TxtFileGeneratorResult mainProcess(File file, long lines) {

        logger.info("Generating content for file: " + file.getAbsolutePath());
        String content = this.buildFile(lines);

        logger.info("Writing content to file: " + file.getAbsolutePath());
        boolean isContentWritten = this.writeToFile(file, content);

        if(isContentWritten) {
            return new TxtFileGeneratorResult(true, Optional.empty());
        } else  {
            return new TxtFileGeneratorResult(false, Optional.of("failed to write content into txt file"));
        }

    }

    private boolean writeToFile(File file, String content) {

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            bufferedWriter.write(content);

            return true;

        }
        catch (IOException ex) {
            logger.severe(String.format("failed to write to file %s due to reason %s", file.getAbsolutePath(), ex.getMessage()));
            return false;
        }

    }

    private String buildFile(long lines) {

        StringBuilder sb = new StringBuilder();

        for(long i = 0; i < lines; i++) {
            sb.append(buildLine(i));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    private String buildLine(long id) {
        return id + COLUMN_SEPARATOR + FasterRandom.uuid() + COLUMN_SEPARATOR + FasterRandom.number();
    }

}