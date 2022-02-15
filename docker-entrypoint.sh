#!/usr/bin/env bash

export TASK_NAME=${1}

sbt assembly

if [ "$TASK_NAME" = "PartTwo" ]; then
  scala verve.jar  PartTwo

elif [ "$TASK_NAME" = "PartThree" ]; then
  /opt/spark/bin/spark-submit \
  --master local \
  --class calculator.Main \
  --conf spark.eventLog.enabled=true \
  verve.jar PartThree
fi

