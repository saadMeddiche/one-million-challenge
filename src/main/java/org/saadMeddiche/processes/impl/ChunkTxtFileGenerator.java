package org.saadMeddiche.processes.impl;

import org.saadMeddiche.models.TxtFileGeneratorResult;
import org.saadMeddiche.processes.TxtFileGenerator;

import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Generate the .txt file that will be used for the challenge. It uses strait forward generation.
 * <p>
 * It generates content and writes into file in chunks. It takes less/controlled memory at the cost of extra time.
 * */
public class ChunkTxtFileGenerator extends TxtFileGenerator {

    private final static Logger logger = Logger.getLogger(ChunkTxtFileGenerator.class.getName());

    protected TxtFileGeneratorResult mainProcess(File file, long lines) {
        return new TxtFileGeneratorResult(true, Optional.empty());
    }

}