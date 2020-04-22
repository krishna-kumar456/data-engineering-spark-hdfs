# ETL via Apache Spark with HDFS

## Description

This project aims to provide a simple solution to run adhoc analytical queries from a list of sources stored in HDFS and dump the result to HDFS.

All ETL related information needs to be parameterised and spark code should be optimized to achieve high throughput.

Above requirements are achieved by using a json file to store all ETL related paramters. By reading in the parameters, a pool
of dynamic dataframes is created, each being represented by a view.

Adhoc Analytical queries which can be submitted via the json config, can be run against these views to generate insights.

The flow has been tested by using open source GoodReads data (provided in the repo).


## Instructions to run

#### Populate jobConfig.json

cluster - Allows you to set Spark cluster information.

source - Accepts a list of sources, each source specifies information regarding the source data

sourceId - Unique identifier for the source, used for aliasing within analytical query processing.
sourceType - Allows you to specify the type of source - HDFS/Local.
fileType - Allows you to specify the type of file which needs to be read.
path - Specify the path where the file is being stored.


destination - Accepts a list of of sinks, adhoc analytical queries can be run from collected source data and then dumped into sinks

destinationId - Unique identifier for sink.
destinationType - Similar to sourceType - HDFS/Local.
fileType - Similar to fileType in source.
path - Destination path where data needs to be dumped.
analyticalQuery - Specify SparkSQL query which will be run against the collected source datasets, the output from this query will be dumped within the destination provided. Query should be Apache SparkSQL compliant.



#### Submit SparkETL jar to Spark cluster with jobConfig.json as parameter

bin/spark-submit  --master <spark-master-url> --class ETL.SparkETL /path/to/sparketl_2.12-0.1.jar /path/to/jobConfig.json

Note: sparketl_2.12-0.1.jar can be found at https://github.com/redfruitt/DataEngineering-Spark-HDFS/tree/master/SparkETL/target/scala-2.12


## Scope for improvements

Add support for mutliple source/destinations types such as S3/Redshift/PostgresSQL
Provide additional support for pushdown queries at source level
Provide support for additional options for writing data to different mediums.