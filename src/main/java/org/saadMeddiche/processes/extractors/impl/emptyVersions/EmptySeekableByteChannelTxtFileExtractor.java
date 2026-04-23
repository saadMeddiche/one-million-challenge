package org.saadMeddiche.processes.extractors.impl.emptyVersions;

import org.saadMeddiche.models.TxtFileExtractorResult;
import org.saadMeddiche.processes.extractors.TxtFileExtractor;
import org.saadMeddiche.processes.extractors.impl.SeekableByteChannelTxtFileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

/**
 * This extractor is a dummy twin of {@link SeekableByteChannelTxtFileExtractor} with no sum logic.
 * It just goes threw the bytes of the file.
 * <p>
 * The role of this class is to have the cost of sum logic, that will help me to improve the logic of its {@code SeekableByteChannelTxtFileExtractor}
 * */
public class EmptySeekableByteChannelTxtFileExtractor extends TxtFileExtractor {

    private final static int ALLOCATION_SIZE = 5_000_000;

    protected TxtFileExtractorResult mainProcess(File file, String columnSeparator) {

        try (SeekableByteChannel ch = Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {

            ByteBuffer bf = ByteBuffer.allocateDirect(ALLOCATION_SIZE);
            //ByteBuffer bf = ByteBuffer.allocate(ALLOCATION_SIZE);

            while(ch.read(bf) > 0) {
                bf.flip();
                bf.clear();
            }

            return new TxtFileExtractorResult(true, 0, Optional.empty());

        }
        catch ( IOException e ) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }

}
