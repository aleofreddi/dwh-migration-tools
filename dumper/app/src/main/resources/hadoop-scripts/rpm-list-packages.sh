#!/bin/bash

if command -v rpm > /dev/null 2>&1 ; then
  rpm -qa | grep "^\(docker\|g++\|gcc\|hadoop\|hbase\|hive\|mysql\|oozie\|perl\|pig\|python\|r-\|ranger\|ruby\|scala\|spark\|sqoop\|tez/\|zookeeper\)"
fi
