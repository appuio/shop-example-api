#!/bin/bash
docker build ../../s2i/play -t scala-play-s2i
s2i build --loglevel 5 -s image:///usr/libexec/s2i . scala-play-s2i shop-example-api
