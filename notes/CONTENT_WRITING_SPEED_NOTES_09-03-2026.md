# NOTES

- All TxtFileGenerator children are using `FileWriter`.
We will use `BufferedWriter` instead for faster writing speed.

## NOTE 1: StringBuilderTxtFileGenerator

- Using `FileWriter` & `37M lines` (before)
    - **Time:** 20_261ms

- Using `BufferedWriter` & `37M lines` (after'
    - **Time:** 18_901ms

## NOTE 2: SimpleTxtFileGenerator

- Using `FileWriter` & `37M lines` (before)
    - **Time:** 21_194ms

- Using `BufferedWriter` & `37M lines` (after)
    - **Time:** 7_039ms

## NOTE 3: ChunkTxtFileGenerator 

- Using `FileWriter` & `37M lines` (before)
    - **Time:** ???

- Using `BufferedWriter` & `37M lines` (after)
    - **Time:** ???