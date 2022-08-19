# calendarConvert
目前支持的功能((1900, 2100]的阳历和农历互转)<br/>
solarToLunar, 阳历转农历(输入："20220101", 输出："20211129")<br/>
lunarToSolar, 农历转阳历(输入："20220101", 输出："20220201")<br/>

实现思路：<br/>
1.从香港天文台 爬取(1900,2100]的阳历和阴历的对照表 https://www.hko.gov.hk/tc/gts/time/calendar/text/files/T2022c.txt, 项目中放在resouces/utils/lunar_solar_map<br/>
2.根据第一步的数据，做查表功能实现<br/>

后续待补充功能：<br/>
1.给定一个农历或者阳历的日期,返回的是一个对象,其中可以包含：农历+阳历+星期?+是否有24节气等<br/>
