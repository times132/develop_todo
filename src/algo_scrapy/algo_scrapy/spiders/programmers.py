import scrapy
import re
from algo_scrapy.items import AlgoScrapyItem
from scrapy.spiders import CrawlSpider, Rule
from scrapy.linkextractors import LinkExtractor

class ProgrammersSpider(CrawlSpider):
    name = 'programmers'
    programmers_url = 'https://programmers.co.kr'
    beakjoon_url = 'https://www.acmicpc.net'
    start_urls = ['https://programmers.co.kr/learn/challenges?tab=algorithm_practice_kit', 'https://www.acmicpc.net/problem/tags']

    # algo_ids = set(['7', '25', '33', '11', '158', '120', '97', '12', '126', '127', '71', '8', '72'])
    algo_ids = ['7', '25', '33', '11', '158', '120', '97', '12', '126', '127', '71', '8', '72']
    
    category_dic = {'해시': ['해시', 'HASH', 'HASHING', '해쉬', '해싱'], 
                    'DFS/BFS': ['DFS', 'DEPTH', '깊이', 'BFS', 'BREADTH', '너비'], 
                    '스택/큐': ['STACK', '스택', 'QUEUE', '큐'],
                    '힙': ['HEAP', '힙'],
                    '정렬': ['정렬', 'SORT', 'SORTING'],
                    '그리디': ['그리디', 'GREEDY', '트리', 'TREE'],
                    '그래프/트리': ['그래프', 'GRAPH'],
                    '동적계획법': ['동적', 'DYNAMIC'],
                    '문자열': ['문자', 'STRING'],
                    '이분탐색': ['이분', 'BINARY']
            }

    # url_matcher = re.compile(r'^https://programmers.co.kr/learn/courses/30/parts/')
    rules = (
        # Rule(LinkExtractor(allow=r'/learn/courses/30/parts/.*'), callback='parse_programmers', follow=True),
        Rule(LinkExtractor(allow=('/problem/tag/'+i for i in algo_ids[1:2])), callback='parse_beakjoon', follow=True),
        # Rule(LinkExtractor(allow=('/problem/tag/25',)), callback='parse_beakjoon', follow=True, process_request=test_processor), # url 넘겨서 value값 확인
    )

    # 프로그래머스 crawling
    def parse_programmers(self, response):
        category = response.xpath('//h1[@class="headline"]/text()').get().strip()
        title = response.xpath('//h4[@class="title"]/text()').getall()
        level = response.xpath('//h6[@class="level"]/text()').getall()
        algo_url = response.xpath('//div[@class="col-item"]/div/a/@href').getall()
        
        for i in range(len(title)):
            yield AlgoScrapyItem(title=title[i], category=self.category_integration(category), level=level[i].split()[-1], algo_url=self.programmers_url+algo_url[i])

    # 백준 crawling
    def parse_beakjoon(self, response):
        print(response.text)
        print("====================================================")
        print("URL은 : ", response.url)
        # category = response.css('')
        # title = response.xpath('//*[@id="problemset"]/tbody/tr/td[2]/a/text()').getall()
        # level = response.xpath('//*[@id="problemset"]/tbody/tr/td[6]/text()').getall()
        # algo_url = response.xpath('//*[@id="problemset"]/tbody/tr/td[2]/a/@href').getall()
        # print('알고리즘 이름: ', title)
        # print('정답 비율: ', level)
        # print('URL: ', algo_url)

    def category_integration(self, category):
        # 1. 해시
        for a in ProgrammersSpider.category_dic['해시']:
            if a in category:
                return '해시'
        # 2. DFS/BFS
        for a in ProgrammersSpider.category_dic['DFS/BFS']:
            if a in category:
                return 'DFS/BFS'
        # 3. 스택/큐
        for a in ProgrammersSpider.category_dic['스택/큐']:
            if a in category:
                return '스택/큐'
        # 4. 힙
        for a in ProgrammersSpider.category_dic['힙']:
            if a in category:
                return '힙'
        # 5. 정렬
        for a in ProgrammersSpider.category_dic['정렬']:
            if a in category:
                return '정렬'
        # 6. 그리디
        for a in ProgrammersSpider.category_dic['그리디']:
            if a in category:
                return '그리디'
        # 7. 그래프/트리
        for a in ProgrammersSpider.category_dic['그래프/트리']:
            if a in category:
                return '그래프/트리'
        # 8. 동적계획법
        for a in ProgrammersSpider.category_dic['동적계획법']:
            if a in category:
                return '동적계획법'
        # 9. 문자열
        for a in ProgrammersSpider.category_dic['문자열']:
            if a in category:
                return '문자열'
        # 10. 이분탐색
        for a in ProgrammersSpider.category_dic['이분탐색']:
            if a in category:
                return '이분탐색'
        
        return category