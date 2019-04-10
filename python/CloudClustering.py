from pyspark.sql import SparkSession

def init_spark():
    spark = SparkSession \
        .builder \
        .appName("Python Spark SQL basic example") \
        .config("spark.some.config.option", "some-value") \
        .getOrCreate()
    return spark


def centroid_generator(train_data, y_data, n):
    holders = [[], [], [], [], []]
    spark = init_spark()
    sc = spark.sparkContext
    rdd = sc.textFile(train_data)    #Read data_file into RDD
    dataList = rdd.collect()    #Convert RDD into Python List, each element contains a string of the time,value pair
    dataList = dataList[1:]     #Remove first row as it only has column info, no actual values

    rdd2 = sc.textFile(y_data)
    yList = rdd2.collect()

    for i in range(n):
        temp = []
        for j in range(178):
            j_split = dataList[j].split(",")   #Split datalist at j into array of two values
            temp.append(int(j_split[1]))         #We append the second entry as that is the value
        dataList = dataList[178:]           #No longer need these values so remove them

        #Instead of appending temp array, append difference array of size 177
        diff_array = []
        for j in range(177):
            diff_array.append(abs(temp[j+1] - temp[j]))

        diff_total = 0
        for j in diff_array:
            diff_total = diff_total + j

        holders[int(yList[i]) - 1].append(diff_total)

    print(holders)

    centroids = [0, 0, 0, 0, 0]

    for i in range(len(holders)):  #for each holder in holder list
        for j in holders[i]:      #for each entry in holder
            centroids[i] = centroids[i] + j

    for i in range(len(centroids)):
        centroids[i] = centroids[i] / len(holders[i])

    for i in centroids:
        print(i)

    return centroids


def distance(int1, int2):
    return abs(int1 - int2)


def test_centroids(test_data, y_data, centroids):
    test_sum = 0
    seiz_sum = 0
    y_sum = 0
    spark = init_spark()
    sc = spark.sparkContext
    rdd = sc.textFile(test_data)  # Read data_file into RDD
    dataList = rdd.collect()  # Convert RDD into Python List, each element contains a string of the time,value pair
    dataList = dataList[1:]  # Remove first row as it only has column info, no actual values

    rdd2 = sc.textFile(y_data)
    yList = rdd2.collect()

    for i in range(4500):
        temp = []
        temp_y = 1
        for j in range(178):
            split_array = dataList[j].split(",")
            temp.append(int(split_array[1]))     #array of next 178 values
        dataList = dataList[178:]

        #Instead of appending temp array, append difference array of size 177
        diff_array = []
        for j in range(177):
            diff_array.append(abs(temp[j + 1] - temp[j]))

        diff_total = 0
        for j in diff_array:
            diff_total += j

        min_dist = distance(diff_total, centroids[0]) #set min_distance to 1's value as default
        if diff_total < 3500:
            for j in range(5):
                if distance(diff_total, centroids[j]) < min_dist:
                    min_dist = distance(diff_total, centroids[j])     #set new min_distance
                    temp_y = j + 1                              #change y value to correspond to new min_distance

        #Sum to test accuracy of all values
        if int(temp_y) == int(yList[7000 + i]):
            test_sum = test_sum + 1

        #Sum to count how many actual y=1 values there were
        if int(yList[7000 + i]) == 1:
            y_sum = y_sum + 1

        #Sum to test accuracy of detecting seizures
        if (int(temp_y) == int(yList[7000 + i])) and int(temp_y) == 1:
            seiz_sum = seiz_sum + 1

        test_rate = test_sum / 4000 * 100
        test_rate = "{0:.2f}".format(test_rate)

        seizure_rate = seiz_sum / y_sum * 100
        seizure_rate = "{0:.2f}".format(seizure_rate)

    return "The program sorted " + test_rate + "% of y values correctly and correctly predicted " + seizure_rate + "% of seizures."

#centroids = centroid_generator("train_data.csv", "y_data.csv", 7000)

centroids = [13040.672413793103, 1618.5073787772312, 1508.3789398280803, 3533.7969295184926, 2160.4601769911505]

print(test_centroids("test_data.csv", "y_data.csv", centroids))

