# Best solution to write content into a file

- BufferedWriterFileGenerator is the fastest and best memory efficient generator.

---
- With `5M` max heap it took around `11_667ms` to generate `37M lines`.
- With `7M` max heap it took around `7_112ms` to generate `37M lines`.
- With `10M` max heap it took around `5_480ms` to generate `37M lines`.
- With `100M` max heap it took around `4_590ms` to generate `37M lines`
- With `200M` max heap it took around `4_662ms` to generate `37M lines`
- With `300M` max heap it took around `4_702ms` to generate `37M lines`
- With `500M` max heap it took around `4_638ms` to generate `37M lines`
- With `1G` max heap it took around `4_721ms` to generate `37M lines`
- With `5G` max heap it took around `5_077ms` to generate `37M lines`
- With `10G` max heap it took around `5_220ms` to generate `37M lines`
- With `14G` max heap it took around `5_320ms` to generate `37M lines`
---

## NOTES:

- Anything less than `5M` heap caused the JVM to not start. So it is safe to say that `5M` is the lower memory we can achieve,
but at the cost of time.

- The content generation part (UUID and radom number) is taking about 70% of the time. **I need a solution for that !!**

## CONCLUSION

- In the range of `5M` and `10M`, the CPU is busy cleaning the string objects. — **Large GC cycles (Garbage Collector)**
- In the range of `100M` and `500M`, the heap is big enough to reduce GC cycles. 
- In the range of `1G` and `14G`, there a slight increase in time. Maybe do to the overhead of managing heap by the GC. — **Management Overhead**
  - When the heap size is massive, the JVM will be less aggressive about cleaning up, leading to an increase in memory footprint that eventually requires a more expensive scan.