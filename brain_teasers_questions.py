import requests
from bs4 import BeautifulSoup as bs
import json
import time


def is_json(my_json):
    try:
        json.loads(my_json)
    except ValueError:
        return False
    return True


def download_brain_teaser_from_baidu():
    page = 0
    stayList = []
    while page <= 200:
        try:
            url = "https://hanyu.baidu.com/hanyu/ajax/twisters_list?class=%E5%85%A8%E9%83%A8&ptype=brain_twister&pn=" + str(
                page)
            page += 1
            response = requests.get(url, timeout=5)
            if response.status_code == 200:
                response.encoding = 'unicode_escape'
                responseSoup = bs(response.text, 'lxml')
                print(responseSoup.p.text)
                if not is_json(responseSoup.p.text):
                    continue
                brain_teasers_html = json.loads(responseSoup.p.text)
                with open("/home/h/brain_teaser_abstract.txt", 'a+', encoding='utf-8') as f:
                    for list in brain_teasers_html["ret_array"]:
                        answer = list["entity"]["kvinfo"]
                        questions_answers = (list["entity"]["name"] + "\t" + str(answer[0]["value"]))
                        print(questions_answers)
                        f.write(questions_answers)
                        f.write("\n")
                    time.sleep(1)
            else:
                stayList.append(url)
        except Exception as result:
            page = page - 1
            print(result)
            continue
    return


def deduplication():
    with open("/home/h/brain_teaser_abstract.txt", 'r', encoding='utf-8') as f:
        questions_answers_set = set()
        cnt = 0
        values = []
        for line in f.readlines():
            questions_answers_set.add(line)
        for key in questions_answers_set:
            if len(key) == 0:
                continue
            answer = key.split('\t')[1].strip()
            answer = answer.strip("[\\'")
            answer = answer.strip("\\']")
            value = key.split("\t")[0] + "\t" + answer
            values.append(value)
        return values


def build_finally_brain_teaser_abstract(values):
    with open("/home/h/brain_teaser_finally_abstract.txt", "a+", encoding='utf-8') as f:
        for value in values:
            print(value)
            f.write(value)
            f.write("\n")


def other_deduplication():
    values = set()
    questions_answers = []
    cnt = 0
    with open("/home/h/questions_answers.txt", "r", encoding='utf-8') as f:
        for line in f.readlines():
            cnt += 1
            values.add(line)
        print(cnt)
        print(len(values))
    with open("/home/h/questions_answers_finally.txt", "a+", encoding='utf-8') as f1:
        for value in values:
            f1.write(value)



if __name__ == '__main__':
    # download_brain_teaser_from_baidu()
    # values = deduplication()
    # build_finally_brain_teaser_abstract(values)
    other_deduplication()
