# DataEngineering-Spark-HDFS

# get container id, assign to env variable
$ export CONTAINER_ID=$(sudo docker ps --filter name=master --format "{{.ID}}")

# copy count.py
$ sudo docker cp count.py $CONTAINER_ID:/tmp

# run spark
$ sudo docker exec $CONTAINER_ID \
  bin/spark-submit \
    --master spark://master:7077 \
    --class endpoint \
    /tmp/count.py
