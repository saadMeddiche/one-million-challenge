# NOTES

- All TxtFileGenerator children are using `FileWriter`.
We will use `BufferedWriter` instead for faster writing speed.

## NOTE 1: StringBuilderTxtFileGenerator

- Using `FileWriter` & `37M lines` (before)
    - **Time:** 20_261ms

- Using `BufferedWriter` & `37M lines` (after)
    - **Time:** 18_901ms

## NOTE 2: SimpleTxtFileGenerator

- Using `FileWriter` & `37M lines` (before)
    - **Time:** 21_194ms

- Using `BufferedWriter` & `37M lines` (after)
    - **Time:** 7_039ms

## NOTE 3: ChunkTxtFileGenerator 

- Using `FileWriter` & `37M lines` (before)
    - **Time:** 10_452ms

- Using `BufferedWriter` & `37M lines` (after)
    - **Time:** 7_395ms

# CONCLUSION

- Writing/reading content in Disk is `expensive` in terms of time.
  - Because the disk is designed to hold data for a long time, it takes a longer period to read/write data compared to other components (e.g., RAM)

- `BufferWriter` is a wrapper **(wrap Writer classes)** that has an internal buffer (8KB by default). It only writes to the file when the buffer is full.
  - Meaning less request to the OS

- `FileWriter` is a basic class that write content directly to the disk — write to OS File system cache, and OS decides when to flush that to the disk
  - Meaning more request to the OS.