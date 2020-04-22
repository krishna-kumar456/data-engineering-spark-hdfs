from pyspark.sql import SparkSession


spark = SparkSession.builder \
    .master('local[*]') \
    .appName('PR') \
    .getOrCreate()
    #.config('spark.sql.warehouse.dir', '/tmp/data/twitter') \
    

sc = spark.sparkContext

# using SQLContext to read parquet file

from pyspark.sql import SQLContext

sqlContext = SQLContext(sc)

# to read parquet file
print('trying to read from hdfs')

df_load = sqlContext.read.csv('hdfs://4d7b8c7d0f33:50010/hadoop/data/test.csv')
df_load.show()


# Define JDBC properties for DB Connection
# url = "jdbc:postgresql://172.20.128.2/postgres"
# properties = {
#    "user": "kk",
#    "password": "1234",
#    "driver": "org.postgresql.Driver"
#}


#Write the file to DataBase table test_bics
# df.write.mode("overwrite").jdbc(url=url, table="test_bics1", properties=properties)

