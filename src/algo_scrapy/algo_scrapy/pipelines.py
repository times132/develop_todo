import mariadb
import datetime
from scrapy.exceptions import DropItem

class AlgoScrapyPipeline(object):
    next_id = 0
    
    def __init__(self):
        try:
            self.conn = mariadb.connect(
                user="root",
                password="dkdldb9498",
                host="127.0.0.1",
                port=3306,
                database="algorithm"
            )
        except mariadb.Error as e:
            print(f"DB 커넥션 오류: {e}")

        self.cur = self.conn.cursor()

    def open_spider(self, spider):
        print("스파이더 파이프라인 시작.")
        self.cur.execute('CREATE TABLE if not exists algo(aid bigint primary key auto_increment, title varchar(100) unique, category varchar(100), level int(5), algo_url varchar(100), crawled_time datetime)')
        self.cur.execute('SELECT AUTO_INCREMENT FROM information_schema.tables where table_name = "algo" and table_schema = database()')
        AlgoScrapyPipeline.next_id = self.cur.fetchone()[0]

    def process_item(self, item, spider):
        if not item.get('title') is None:
            try:
                crawled_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

                item['crawled_time'] = crawled_time
                
                self.cur.execute('INSERT INTO algo(aid, title, category, level, algo_url, crawled_time) values(?,?,?,?,?,?);', (AlgoScrapyPipeline.next_id, item.get('title'), item.get('category'), item.get('level'), item.get('algo_url'), item.get('crawled_time')))
                self.conn.commit()
                AlgoScrapyPipeline.next_id += 1
                print('db insert')
                
                return item
            except:
                print('이미 존재하는 문제입니다.')
        else:
            raise DropItem('Dropped item')

    def close_spider(self, spider):
        print('스파이더 파이프라인 종료')

        # self.conn.commit()

        self.conn.close()