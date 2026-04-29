## Observations and Monitoring
- the sample that I will use for testing has 100M lines and weights `5787.15648 MB`

### BufferedWriterFileGenerator

- **Time:** `11_198 ms`
- **Resource usage:** we notice multiple spikes ↑↓

![memory_cpu_#1.png](images/BufferedWriterFileGenerator_memory_cpu_usage_0.png)
![memory_cpu_#2.png](images/BufferedWriterFileGenerator_memory_cpu_usage_1.png)

### SeekableByteChannelTxtFileGenerator

- **Time using HeapByteBuffer:** `8_301 ms`
- **Resource usage:** we notice a small bump in memory usage (~5MB) very close to allocation size (~5MB).

![memory_cpu_HeapByteBuffer.png](images/SeekableByteChannelTxtFileGenerator_HeapByteBuffer_memory_cpu_usage.png)

- **Time using DirectByteBuffer:**

---
 - Using `UUIDTool.writeUUID()`: `6_618 ms`
 - **Resource usage:** we notice that there are no spikes ↑↓ at all in memory

![memory_cpu_DirectByteBuffer_UUIDTool.writeUUID().png](images/SeekableByteChannelTxtFileGenerator_DirectByteBuffer_UUIDTool.writeUUID()_memory_cpu_usage.png)

 - Using `FasterRandom.uuid().toString().getBytes()`: `7_203 ms`
 - **Resource usage:** we notice that there are memory spikes ↑↓

![memory_cpu_DirectByteBuffer_FasterRandom.uuid().toString().getBytes().png](images/SeekableByteChannelTxtFileGenerator_DirectByteBuffer_FasterRandom.uuid()_memory_cpu_usage.png)
