import scrapy

class ProgrammersSpider(scrapy.Spider):
    name = 'programmers'
    allowed_domains = ['https://programmers.co.kr/learn/challenges?tab=algorithm_practice_kit']
    start_urls = ['https://programmers.co.kr/learn/challenges?tab=algorithm_practice_kit']

    def parse(self, response):
        print(response.text)
        # pass