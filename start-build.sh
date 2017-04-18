#!/bin/bash
s2i build --incremental=true --loglevel 5 -s image:///usr/libexec/s2i . appuio/shop-example-api-builder shop-example-api
