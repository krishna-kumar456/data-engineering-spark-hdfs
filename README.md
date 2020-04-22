# ETL via Apache Spark with HDFS







## Instructions to run


### Populate jobConfig.json

Fill jobConfig.json as per required

### Submit SparkETL jar to Spark cluster with jobConfig.json as parameter

bin/spark-submit  --master <spark-master-url> --class ETL.SparkETL /path/to/sparketl_2.12-0.1.jar /path/to/jobConfig.json

Note: sparketl_2.12-0.1.jar can be found at https://github.com/redfruitt/DataEngineering-Spark-HDFS/tree/master/SparkETL/target/scala-2.12
