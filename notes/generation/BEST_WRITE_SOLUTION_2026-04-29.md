## Observations and Monitoring
- the sample that I will use for testing has 100M lines and weights `5787.15648 MB`

### BufferedWriterFileGenerator

- **Time:** `11_198 ms`
- **Resource usage:** we notice multiple spikes ↑↓

![memory_cpu_#1.png](images/BufferedWriterFileGenerator_memory_cpu_usage_0.png)
![memory_cpu_#2.png](images/BufferedWriterFileGenerator_memory_cpu_usage_1.png)

### SeekableByteChannelTxtFileGenerator

—————————————————

- **Time using HeapByteBuffer:**
 
—————————————————

---

- Using `UUIDTool.writeUUID()`: `8_287 ms`
- **Resource usage:** we notice a small bump in memory usage (~5MB) very close to allocation size (~5MB).

![memory_cpu_HeapByteBuffer_UUIDTool.writeUUID().png](images/SeekableByteChannelTxtFileGenerator_HeapByteBuffer_UUIDTool.writeUUID()_memory_cpu_usage.png)

- Using `FasterRandom.uuid().toString().getBytes()`: `7_778 ms`
- **Resource usage:** we notice that there are memory spikes ↑↓

![memory_cpu_HeapByteBuffer_FasterRandom.uuid().png](images/SeekableByteChannelTxtFileGenerator_HeapByteBuffer_FasterRandom.uuid()_memory_cpu_usage.png)

—————————————————

- **Time using DirectByteBuffer :**

—————————————————

---

 - Using `UUIDTool.writeUUID()`: `6_473 ms`
 - **Resource usage:** we notice that there are no spikes ↑↓ at all in memory

![memory_cpu_DirectByteBuffer_UUIDTool.writeUUID().png](images/SeekableByteChannelTxtFileGenerator_DirectByteBuffer_UUIDTool.writeUUID()_memory_cpu_usage.png)

 - Using `FasterRandom.uuid().toString().getBytes()`: `7_203 ms`
 - **Resource usage:** we notice that there are memory spikes ↑↓

![memory_cpu_DirectByteBuffer_FasterRandom.uuid().toString().getBytes().png](images/SeekableByteChannelTxtFileGenerator_DirectByteBuffer_FasterRandom.uuid()_memory_cpu_usage.png)

## CONCLUSIONS

- The reason why **BufferedWriterFileGenerator** `11_198 ms` is slower and consumes more memory than **SeekableByteChannelTxtFileGenerator->DirectByteBuffer->UUIDTool.writeUUID()** `6_473 ms` is mainly because:
  - It generates a full String line for every record `id + COLUMN_SEPARATOR + FasterRandom.uuid() + COLUMN_SEPARATOR + FasterRandom.number();`, which create many temporary objects and increases GC work.
  - Also, `BufferWriter` works on characters, so the output needs to be encoded to bytes before being written into disk.
  - `SeekableByteChannel` writes prebuilt bytes that represent the record into a reusable bytebuffer then batch the output.
  -  The `SeekableByteChannelTxtFileGenerator` uses `UUIDTool.writeUUID()` that writes UUID text bytes directly in the bytebuffer without the intermediate toString() and toBytes(), which explain the lower memory usage and better time.