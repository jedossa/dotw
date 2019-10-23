#!/bin/sh
curl -L https://piccolo.link/sbt-1.3.3.tgz > ~/sbt.tar.gz
tar -C ~ -xvf ~/sbt.tar.gz
alias sbt=~/sbt/bin/sbt