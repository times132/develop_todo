import mariadb
import datetime
from scrapy.exceptions import DropItem

class test_db(object):
    next_id = 0
    algo_ids = set(['7', '25', '33', '11', '158', '120', '97', '12', '126', '127', '71', '8', '72'])
    category_dic = {'해시': ['해시', 'hash', 'hashing', '해쉬', '해싱'], 'DFS': ['DFS', 'dfs', 'depth', '깊이'], 'BFS': ['BFS', 'bfs', 'breadth', '너비']}
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

    def open_spider(self):
        print("스파이더 파이프라인 시작.")
        self.cur.execute('CREATE TABLE if not exists algo(aid bigint primary key auto_increment, title varchar(100) unique, category varchar(20), level int(5), algo_url varchar(100), crawled_time datetime)')
        self.cur.execute('SELECT AUTO_INCREMENT FROM information_schema.tables where table_name = "algo" and table_schema = database()')
        test_db.next_id = self.cur.fetchone()[0]
        print(test_db.next_id)

    def process(self, item):
        if not item.get('title') is None:
            try:
                crawled_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

                item['crawled_time'] = crawled_time
                print("현재 id값: ",item.get('aid'))
                self.cur.execute('INSERT INTO algo(aid, title, category, level, algo_url, crawled_time) values(?,?,?,?,?,?);', (item.get('aid'), item.get('title'), item.get('category'), item.get('level'), item.get('algo_url'), item.get('crawled_time')))
                self.conn.commit()
                
                print('db insert')
                test_db.next_id += 1
                print("다음 id값: ", test_db.next_id)

                return item
            except:
                print('이미 존재하는 문제입니다.')
        else:
            raise DropItem('Dropped item')

    def close_spider(self):
        print('스파이더 파이프라인 종료')

        self.conn.commit()

        self.conn.close()

    def category_integration(self, category):
        for a in test_db.category_dic['해시']:
            if a in category:
                # print('카테고리는 해시 입니다.')
                return '해시'

        for a in test_db.category_dic['DFS']:
            if a in category:
                # print('카테고리는 DFS 입니다.')
                return 'DFS'

        for a in test_db.category_dic['BFS']:
            if a in category:
                # print('카테고리는 BFS 입니다.')
                return 'BFS'
        
        return False

a = test_db()
cate_dic = ['해쉬', 'hashing1', 'depth', 'DFS', 'breadth-first-search', '깊이', '너비 우선 탐색', '깊이/너비 우선 탐색(DFS/BFS)']
# for i in cate_dic:    
#     result = a.category_integration(i)
#     print('==========================')
#     print(i, ' : ' ,result)
#     print('==========================')

# for i in range(1,5):
#     item = dict()
#     item['title'] = '위장21'
#     item['category'] = '해시'
#     item['level'] = 4
#     item['algo_url'] = 'www.hello.world'

#     a = test_db()
#     a.open_spider()
#     item['aid'] = a.next_id
#     a.process(item)
#     a.close_spider()

b = ('problem/tag/'+i for i in a.algo_ids)
for i in b:
    print(i)