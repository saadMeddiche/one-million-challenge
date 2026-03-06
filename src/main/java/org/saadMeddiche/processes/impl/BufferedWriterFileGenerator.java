package org.saadMeddiche.processes.impl;

import org.saadMeddiche.models.TxtFileGeneratorResult;
import org.saadMeddiche.processes.TxtFileGenerator;
import org.saadMeddiche.utils.FasterRandom;

import java.io.*;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Generate the .txt file that will be used for the challenge. It uses strait forward generation.
 * <p>
 * */
public class BufferedWriterFileGenerator extends TxtFileGenerator {

    private final static Logger logger = Logger.getLogger(BufferedWriterFileGenerator.class.getName());

    protected TxtFileGeneratorResult mainProcess(File file, long lines) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for(int i = 0; i < lines; i++) {
                bw.write(buildLine(i));
                bw.newLine();
            }

        } catch (IOException e) {
            return new TxtFileGeneratorResult(false, Optional.of(e.getMessage()));
        }

        return new TxtFileGeneratorResult(true, Optional.empty());
    }

    private String buildLine(long id) {
        return id + COLUMN_SEPARATOR + FasterRandom.uuid() + COLUMN_SEPARATOR + FasterRandom.number();
    }

}