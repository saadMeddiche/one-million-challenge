package org.saadMeddiche.processes.extractors.impl;

import org.saadMeddiche.models.TxtFileExtractorResult;
import org.saadMeddiche.processes.extractors.TxtFileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class SeekableByteChannelTxtFileExtractor extends TxtFileExtractor {

    private final static int ALLOCATION_SIZE = 5_000_000;

    protected TxtFileExtractorResult mainProcess(File file, String columnSeparator) {

        try (SeekableByteChannel ch = Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {

            ByteBuffer bf = ByteBuffer.allocate(ALLOCATION_SIZE);

            long numbersSum = 0;

            int lineCount = 0;

            while(ch.read(bf) > 0) {

                bf.flip();

                int last_index = bf.limit() - 1;

                byte lastByte = bf.get(last_index);

                int completedLinesPosition = bf.limit();

                if(lastByte != '\n') {

                    int unCompleteLineLength = 0;

                    for(int i = last_index ; i >= 0 ; i--) {

                        byte b = bf.get(i);

                        if(b =='\n') break;

                        unCompleteLineLength++;

                    }

                    completedLinesPosition = bf.limit() - unCompleteLineLength;

                }

                while(bf.position() < completedLinesPosition) {

                    int numberOfDigits = lineCount == 0 ? 1 : (int) (Math.log10(lineCount) + 1);

                    int thirdColumnPosition = bf.position() + numberOfDigits + 36 + 2;

                    byte numberNature = numberNature(bf.get(thirdColumnPosition));

                    bf.position(thirdColumnPosition + (numberNature & 0b0000_0001) );

                    byte b = bf.get();

                    int number = 0;

                    do {
                        number = number * 10 + (b - '0');
                        b = bf.get();
                    }
                    while (b != '\r' && b != '\n');

                    if(b != '\n') bf.position(bf.position() + 1);

                    number *= (numberNature & 0b0000_0010) == 0b0000_0010 ? 1 : -1;

                    numbersSum += number;

                    lineCount++;

                }

                bf.position(completedLinesPosition);
                bf.compact();


            }

            return new TxtFileExtractorResult(true, numbersSum, Optional.empty());

        }
        catch ( IOException e ) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }

    private byte numberNature(byte b) {

        if(b == '-') return 0b0000_0001;

        if(b == '+') return 0b0000_0011;

        return 0b0000_0010;

    }

}