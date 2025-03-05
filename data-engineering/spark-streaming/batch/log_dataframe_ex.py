from pyspark.sql import SparkSession
from pyspark.sql.functions import col, to_timestamp, max, min, mean, date_trunc, collect_set, hour, minute, count

from pyspark.sql.types import *


def load_data(ss: SparkSession, from_file, schema):
    if from_file:
        return ss.read.schema(schema).csv("data/batch.csv")

    log_data_inmemory = [
        ["130.31.184.234", "2023-02-26 04:15:21", "PATCH", "/users", "400", 61],
        ["28.252.170.12", "2023-02-26 04:15:21", "GET", "/events", "401", 73],
        ["180.97.92.48", "2023-02-26 04:15:22", "POST", "/parsers", "503", 17],
        ["73.218.61.17", "2023-02-26 04:16:22", "DELETE", "/lists", "201", 91],
        ["24.15.193.50", "2023-02-26 04:17:23", "PUT", "/auth", "400", 24],
    ]

    return ss.createDataFrame(log_data_inmemory, schema)


if __name__ == "__main__":
    ss: SparkSession = SparkSession.builder \
        .master("local") \
        .appName("batch dataframe ex") \
        .getOrCreate()

    # define schema
    fields = StructType([
        StructField("ip", StringType(), False),
        StructField("timestamp", StringType(), False),
        StructField("method", StringType(), False),
        StructField("endpoint", StringType(), False),
        StructField("status_code", StringType(), False),
        StructField("latency", IntegerType(), False),  # 단위 : milliseconds
    ])

    from_file = True

    df = load_data(ss, from_file, fields)


    # 데이터 확인
    # df.show()

    # 스키마 확인
    # df.printSchema()

    # latency (millisecond -> second)
    def milliseconds_to_seconds(n):
        return n / 1000


    df = df.withColumn("latency_seconds", milliseconds_to_seconds(df.latency))

    # timestamp (String -> Timestamp)
    df = df.withColumn("timestamp", to_timestamp(col("timestamp")))

    # filter: status_code = 400, endpoint = "/users"
    df.where((df.status_code == "400") & (df.endpoint == "/users"))

    # group by: method, endpoint 별 latency 최대,최소,평균
    group_cols = ["method", "endpoint"]
    df.groupby(group_cols) \
    .agg(
        max("latency").alias("max_latency"),
        min("latency").alias("min_latency"),
        mean("latency").alias("mean_latency"),
    ).show()

    # group by: 분 단위, 중복 제거한 ip 리스트 개수
    group_cols = ["hour", "minute"]
    df \
     .withColumn("hour", hour(date_trunc("hour", col("timestamp")))) \
     .withColumn("minute", minute(date_trunc("minute", col("timestamp")))) \
     .groupby(group_cols).agg(
        collect_set("ip").alias("ip_list"),
        count("ip").alias("ip_count"),
    ).sort(group_cols).show()

