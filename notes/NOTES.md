# 05-03-2026

## NOTE 1: content_generation_speed_and_memory based on the tools that concatenate the lines `StringBuilder` and `List and String.join()`
- I was able to create `TxtFileGenerator` that will generate the .txt file for the challenge.
- Also, I tested `StringBuilder` against `List and String.join()` in the speed and memory consumption for generating content.
- Generally `StringBuilder` is faster than `List and String.join()` is the aspect of generating content. But both use the same heap size at the end.

##### List_and_String.join() 25M lines
- Time taken: 
  - **pre-allocated capacity:** 18_382 ms, 20250 ms
  - **no pre-allocated capacity:** 19_886 ms, 22_963 ms
- The whole time the heap usage was always 4GB

![heap-25M_lines-List_and_String.join().png](images/heap-25M_lines-List_and_String.join%28%29.png)

-------

##### StringBuilder 25M lines
- Time taken:
    - **pre-allocated capacity:** 15_661 ms, 27_187 ms
    - **no pre-allocated capacity:** 17_211 ms, 17_767 ms
- In the end, the heap usage was 4GB, but before that there were some spikes higher than 4GB.

![heap-25M_lines-StringBuilder.png](images/heap-25M_lines-StringBuilder.png)

## NOTE 2: content_generation_speed based on the tools that generate the content
- There was a huge time and memory difference when I removed `UUID.randomUUID()` and `random.nextInt()`.
- Time dropped `from 20s to 1,3s`. Memory dropped `from 4GB to 2GB`.

## TxtFileGenerator weak points
- The more lines to generate the more memory and time are required. `(25M lines required around 4GB heap)`
- The way of generating content is taking a lot of resources and time. 