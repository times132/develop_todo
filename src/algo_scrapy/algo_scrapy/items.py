import scrapy

class AlgoScrapyItem(scrapy.Item):
    title = scrapy.Field()
    category = scrapy.Field()
    level = scrapy.Field()
    algo_url = scrapy.Field()
    crawled_time = scrapy.Field()