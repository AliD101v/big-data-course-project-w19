import csv
import os
import sys
# Spark imports
from pyspark.rdd import RDD
from pyspark.sql import DataFrame
from pyspark.sql import SparkSession
from pyspark.sql.functions import desc
# Dask imports
import dask.bag as db
import dask.dataframe as df  # you can use Dask bags or dataframes
from csv import reader

#Initialize a spark session.
def init_spark():
    spark = SparkSession \
        .builder \
        .appName("Python Spark SQL basic example") \
        .config("spark.some.config.option", "some-value") \
        .getOrCreate()
    return spark

spark = init_spark()

filename = '../data/train_data.csv'
# ADD YOUR CODE HERE
df = spark.read.format("csv").option("header", "true").load(filename)
df.show()
df.summary()
print(df.count())
row1 = df.agg({"value": "max"}).collect()[0]
# print('max is: ' + row1["max()"])
print('max is: ' + str(row1))