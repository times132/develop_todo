# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
import datetime
import mariadb
from itemadapter import ItemAdapter


class ScrapyExamplePipeline:
    def __init__(self):
        try:
            self.conn = mariadb.connect(
                user="root",
                password="dkdldb9498",
                host="127.0.0.1",
                port=3306,
                database="scrapy"
            )
        except mariadb.Error as e:
            print(f"DB 커넥션 오류: {e}")

        self.cur = self.conn.cursor()

    def process_item(self, item, spider):
        return item
