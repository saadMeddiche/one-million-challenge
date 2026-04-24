package org.saadMeddiche.processes.generators.impl;

import org.saadMeddiche.models.TxtFileGeneratorResult;
import org.saadMeddiche.processes.generators.TxtFileGenerator;
import org.saadMeddiche.utils.FasterRandom;
import org.saadMeddiche.utils.UUIDTool;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class SeekableByteChannelTxtFileGenerator extends TxtFileGenerator {

    private static final byte COLUMN_SEPARATOR = ';';
    private static final byte[] CRLF = {'\r', '\n'};

    private static final int ALLOCATION_SIZE = 5_000_000;

    @Override
    protected TxtFileGeneratorResult mainProcess(File file, long linesNumber) {
        try (SeekableByteChannel bc = Files.newByteChannel(file.toPath(), StandardOpenOption.WRITE)) {

            ByteBuffer buffer = ByteBuffer.allocateDirect(ALLOCATION_SIZE);

            for (int i = 0; i < linesNumber; i++) {

                // Approximate max line length: 11 (int) + 1 (;) + 36 (UUID) + 1 (;) + 11 (int) + 2 (CRLF) = ~62 bytes

                if (buffer.remaining() < 100) {

                    buffer.flip();
                    bc.write(buffer);
                    buffer.clear();

                }

                writeNumber(buffer, i);
                buffer.put(COLUMN_SEPARATOR);
                UUIDTool.writeUUID(buffer);
                //buffer.put(FasterRandom.uuid().toString().getBytes());
                buffer.put(COLUMN_SEPARATOR);
                writeNumber(buffer, FasterRandom.number());
                buffer.put(CRLF);
            }

            buffer.flip();
            while (buffer.hasRemaining()) {
                bc.write(buffer);
            }

        } catch (IOException e) {
            return new TxtFileGeneratorResult(false, Optional.of(e.getMessage()));
        }

        return new TxtFileGeneratorResult(true, Optional.empty());
    }

    private void writeNumber(ByteBuffer buffer, int n) {

        if (n == 0) {
            buffer.put((byte) '0');
            return;
        }

        // prevent overflow in below condition
        if (n == Integer.MIN_VALUE) {
            buffer.put("-2147483648".getBytes());
            return;
        }

        if (n < 0) {
            buffer.put((byte) '-');
            n = -n;
        }

        // Ugly but fast ;)
        int length = (n < 10) ? 1 : (n < 100) ? 2 : (n < 1000) ? 3 : (n < 10000) ? 4 :
                (n < 100000) ? 5 : (n < 1000000) ? 6 : (n < 10000000) ? 7 :
                        (n < 100000000) ? 8 : (n < 1000000000) ? 9 : 10;

        int pos = buffer.position() + length;

        for (int i = pos - 1; i >= buffer.position(); i--) {
            buffer.put(i, (byte) ((n % 10) + '0'));
            n /= 10;
        }

        buffer.position(pos);
    }
}