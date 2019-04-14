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

from pandas import Series
from matplotlib import pyplot

#Initialize a spark session.
def init_spark():
    spark = SparkSession \
        .builder \
        .appName("Python Spark SQL basic example") \
        .config("spark.some.config.option", "some-value") \
        .getOrCreate()
    return spark

spark = init_spark()

# data.csv size: 11500 records. Each contains 178 time points per second.
# train_data.csv size: 1246000 (=7000*178)
# test_data.csv size: 801000 (=4500*178)
# data_new.csv size: 2047000 (=11500*178)
filename = './data/data_new.csv'
# origDataFilename = '../data/train_data.csv'
## Read the files
df = spark.read.format("csv").option("header", "true").load(filename)
# df_orig = spark.read.format("csv").option("header", "true").load(origDataFilename)


# # df_orig.show()
# # class1Count = df_orig.rdd.map(lambda x: x[-1]).filter(lambda x: x == 1).count()
dataCount = df.count()
print(dataCount)
# class1Count = df_orig.select('y').filter('y == 1').count()
# print(df.count())
# # row1 = df.agg({"value": "max"}).collect()[0]
# # print('max is: ' + row1["max()"])
# # print('max is: ' + str(row1))

# print('All data count: ' + str(dataCount))
# print('Class 1 (epilepsy seizure) count: ' + str(class1Count))

# series = Series.from_csv('../data/data_new.csv', header=1)
# series.iloc[:178].plot()
# pyplot.show()