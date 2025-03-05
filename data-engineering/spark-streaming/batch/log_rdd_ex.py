from datetime import datetime
from typing import List

from pyspark import SparkContext, RDD
from pyspark.sql import SparkSession

if __name__ == '__main__':
    ss: SparkSession = SparkSession.builder \
        .master("local") \
        .appName("rdd example ver") \
        .getOrCreate()

    sc: SparkContext = ss.sparkContext

    log_rdd: RDD[str] = sc.textFile("data/batch.txt")

    print(f"count of RDD ==> {log_rdd.count()}")


    # map
    # - batch.txt 의 각 행을 List[str]
    def parse_line(row: str):
        return row.strip().split(" | ")


    parsed_log_rdd: RDD[List[str]] = log_rdd.map(parse_line)


    # parsed_log_rdd.foreach(print)

    # filter
    # - status code: 404
    def get_only_404(row: List[str]) -> bool:
        status_code = row[3]
        return status_code == '404'


    rdd_404 = parsed_log_rdd.filter(get_only_404)


    # rdd_404.foreach(print)

    # - status code: 2xx
    def get_only_2xx(row: List[str]) -> bool:
        status_code = row[3]
        return status_code.startswith("2")


    rdd_normal = parsed_log_rdd.filter(get_only_2xx)


    # rdd_normal.foreach(print)

    # post 요청 / playbooks api
    def get_post_request_and_playbooks_api(row: List[str]) -> bool:
        log = row[2].replace("\"", "")
        return log.startswith("POST") and "/playbooks" in log


    rdd_post_playbooks = parsed_log_rdd.filter(get_post_request_and_playbooks_api)


    # rdd_post_playbooks.foreach(print)

    # reduce
    # api method 별 개수
    def extract_api_method(row: List[str]):
        log = row[2].replace("\"", "")
        api_method = log.split(" ")[0]
        return api_method, 1


    rdd_count_by_api_method = parsed_log_rdd.map(extract_api_method) \
        .reduceByKey(lambda x, y: x + y) \
        .sortByKey()


    # rdd_count_by_api_method.foreach(print)

    # 분 단위별 요청 횟수
    def extract_hour_and_minute(row: List[str]) -> tuple[str, int]:
        timestamp = row[1].replace("[", "").replace("]", "")
        date_format = "%d/%b/%Y:%H:%M:%S"
        date_time_obj = datetime.strptime(timestamp, date_format)
        return f"{date_time_obj.hour}:{date_time_obj.minute}", 1


    rdd_count_by_hour_and_minute = parsed_log_rdd.map(extract_hour_and_minute) \
        .reduceByKey(lambda x, y: x + y) \
        .sortByKey()


    # rdd_count_by_hour_and_minute.foreach(print)

    # group by
    # status code, api method 별 ip 리스트
    def extract_cols(row: List[str]) -> tuple[str, str, str]:
        ip = row[0]
        status_code = row[3]
        api_method = row[2].replace("\"", "").split(" ")[0]

        return status_code, api_method, ip


    result = parsed_log_rdd.map(extract_cols) \
        .map(lambda x: ((x[0], x[1]), x[2])) \
        .groupByKey() \
        .mapValues(list)

    # result.foreach(print)

    # reduceByKey
    result2 = parsed_log_rdd.map(extract_cols) \
        .map(lambda x: ((x[0], x[1]), x[2])) \
        .reduceByKey(lambda x, y: f"{x},{y}") \
        .map(lambda x: (x[0], x[1].split(",")))

    # result2.foreach(print)

    result2.collect()

    while True:
        pass