# Token Bucket Simulation

Java multithreaded exercise modelling packet arrivals, token generation and outgoing traffic.

## How it works

An arrival thread adds random packet sizes to a linked-list queue. A second thread adds one token per second, and an outgoing thread consumes tokens while sending queued packets within a ten-byte batch limit. The queue uses a fifty-byte capacity check.

## Usage

Requires a Java Development Kit. Run from the repository root:

```sh
javac -d out src/cn8/demo.java
java -cp out cn8.demo
```
Observe the console output and stop the process with `Ctrl+C`.

## Notes

The simulation uses unsynchronised shared state and continuously running threads. Queue-empty handling and waiting for tokens are simplified; this is not a production rate limiter.
