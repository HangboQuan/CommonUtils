import requests
from bs4 import BeautifulSoup as bs
import re
import time
import random
if __name__ == '__main__':

    # 北京市 (直辖市)
    # province = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/index.html"
    # zone = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/11/1101.html"
    # street = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/11/01/110101.html"
    # stay = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/11/01/01/110101001.html"

    # 陕西省
    # province = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/index.html"
    # city = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/61.html"
    # zone = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/61/6101.html"
    # street = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/61/01/610102.html"
    # stay = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/61/01/02/610102001.html"

    provinceHeader = {
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
        'Accept-Encoding': '_trs_uv=l17hrq5w_6_jjy; SF_cookie_1=37059734; _trs_ua_s_1=l7enwk8j_6_386x',
        'Accept-Language': 'zh-CN,zh;q=0.9',
        'Cache-Control': 'max-age=0',
        'Cookie': '_trs_uv=l17hrq5w_6_jjy; SF_cookie_1=37059734; _trs_ua_s_1=l7enwk8j_6_386x',
        'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36'
    }


    zoneHeader = {
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
        'Accept-Encoding': '_trs_uv=l17hrq5w_6_jjy; SF_cookie_1=37059734; _trs_ua_s_1=l7enwk8j_6_386x',
        'Accept-Language': 'zh-CN,zh;q=0.9',
        'Cache-Control': 'max-age=0',
        'Cookie': 'SF_cookie_1=37059734; _trs_uv=l7bqi49y_6_6t9u',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36'
    }

    stayHeader = {
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
        'Accept-Encoding':'_trs_uv=l17hrq5w_6_jjy; SF_cookie_1=37059734; _trs_ua_s_1=l7enwk8j_6_386x',
        'Accept-Language':'zh-CN,zh;q=0.9',
        'Cache-control':'max-age=0',
        'Cookie':'SF_cookie_1=15502425',
        'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36'
    }

    stayHeader2 = {
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
        'Accept-Encoding': '_trs_uv=l17hrq5w_6_jjy; SF_cookie_1=37059734; _trs_ua_s_1=l7enwk8j_6_386x',
        'Accept-Language': 'zh-CN,zh;q=0.9',
        'Cache-control': 'max-age=0',
        'Cookie': 'SF_cookie_1=15502425',
        'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36'
    }
    provinceCode = ["11","12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43","44", "45", "46", "50", "51", "52", "53", "54", "61", "62", '63', '64', "65"]

    # province = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/index.html"
    # provinceTr =  requests.get(province,  provinceHeader)
    # provinceTr.encoding = 'utf-8'
    # # print(provinceTr.text)
    #
    # soup = bs(provinceTr.text, 'lxml')
    # provinceLists = soup.find_all("a")
    # # print(provinceLists)
    # provinceList = []
    # for province in provinceLists:
    #     href = province.get("href")
    #     text = province.get_text()
    #     provinceList.append(text)
    # ['北京市', '天津市', '河北省', '山西省', '内蒙古自治区', '辽宁省', '吉林省', '黑龙江省', '上海市', '江苏省', '浙江省', '安徽省', '福建省', '江西省', '山东省', '河南省', '湖北省', '湖南省', '广东省', '广西壮族自治区', '海南省', '重庆市', '四川省', '贵州省', '云南省', '西藏自治区', '陕西省', '甘肃省', '青海省', '宁夏回族自治区', '新疆维吾尔自治区', '京ICP备05034670号']
    # print(provinceList)

    provinceUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/index.html"
    zoneUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/11/1101.html"
    streetUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/11/"
    stayUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/11/"


    # print(requests.get(zoneUrl).text.encode('utf-8', 'ignore'))
    # print(requests.get(zoneUrl).headers['content-type'])
    # print(requests.get(zoneUrl).encoding)
    # print(requests.get(zoneUrl).apparent_encoding)
    cnt = 0
    for url in provinceCode:
        ## 城市
        ## http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/61.html (下面包含了所有的城市：西安、宝鸡、咸阳等)
        prov = provinceUrl[0 : provinceUrl.rindex('/')] + "/" + url + ".html"
        try:
            provGet = requests.get(prov, headers=zoneHeader, timeout=5)
            if provGet.status_code == 200:
                provGet.encoding = 'utf-8'

                provSoup = bs(provGet.text, 'lxml')
                tagsProv = provSoup.find_all("a")
                prevProv = ""
                for tagProv in tagsProv:
                    zone = prov[0:prov.rindex("/")] + "/" + tagProv.get('href')
                    if not (zone.endswith("html") or zone.__eq__(prevProv)):
                        continue
                    prevProv = zone
                    try:
                        zoneGet = requests.get(zone, headers=stayHeader, timeout=5)
                        if zoneGet.status_code == 200:
                            zoneGet.encoding = 'utf-8'

                            zoneSoup = bs(zoneGet.text, 'lxml')
                            zoneTags = zoneSoup.find_all("a")
                            prevStreet = ""
                            for zoneTag in zoneTags:
                                # print("zoneTag:" + zoneTag.get("href"))
                                street = zone[0: zone.rindex("/")] + "/" + zoneTag.get('href')

                                if not (street.endswith("html")) or street.__eq__(prevStreet):
                                    continue
                                # print(street)
                                prevStreet = street
                                try:
                                    streetGet = requests.get(street, headers=zoneHeader, timeout=5)
                                    if streetGet.status_code == 200:
                                        streetGet.encoding = 'utf-8'
                                        streetSoup = bs(streetGet.text, 'lxml')
                                        streetTags = streetSoup.find_all("a")
                                        prevStay = ""

                                        for streetTag in streetTags:
                                            stay = street[0: street.rindex("/")] + "/" + streetTag.get('href')
                                            if not (stay.endswith("html")) or stay.__eq__(prevStay):
                                                continue
                                            prevStay = stay
                                            try:
                                                stayGet = requests.get(stay, headers=stayHeader2, timeout=5)
                                                stayList = []
                                                stayList.append(streetTag.get_text())
                                                if (stayGet.status_code == 200):
                                                    stayGet.encoding = 'utf-8'
                                                    staySoup = bs(stayGet.text, 'lxml')
                                                    for tr in staySoup.find_all('tr', {'class': 'villagetr'}):
                                                        for td in tr('td'):
                                                            if not re.match('<td>[0-9]*</td>', str(td)):
                                                                res = str(td).replace("<td>", "").replace("</td>", "")
                                                                stayList.append(res)
                                                    with open('/home/work/quanhangbo/spider/city.txt', 'a+', encoding='utf-8') as f:
                                                        f.write('\t'.join(stayList))
                                                        f.write("\n")
                                                    print(stayList)

                                                else:
                                                    # 继续再发一次请求
                                                    prevStay = ""
                                                    print("request again:", stay)
                                                    time.sleep(60 * 3)
                                            except Exception as e:
                                                print(e)

                                    else:
                                        prevStreet = ""
                                        print("request again:", street)
                                        time.sleep(2 * 60)
                                except Exception as e:
                                    print("streetGet failed")
                        else:
                            prevProv = ""
                            print("request again:", prov)
                            time.sleep(60)
                    except Exception as e:
                        print(e)
            else:
                print("request failed:", prov)
                time.sleep(60)
        except Exception as e:
            print(e)