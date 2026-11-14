FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y --no-install-recommends \
    python3 \
    python3-apt \
    curl \
    procps \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

CMD ["sleep", "infinity"]
