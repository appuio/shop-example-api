#!/bin/bash
docker build ../../s2i/play -t scala-play-s2i
s2i build . scala-play-s2i shop-example-api
