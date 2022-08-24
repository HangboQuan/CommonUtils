# calendarConvert
目前支持的功能((1900, 2100]的阳历和农历互转,已经输入阳历或者农历日期, 返回json格式的数据, 其中包括)<br/>
solarToLunar("20220101"), 阳历转农历(输入："20220101", 输出："20211129")<br/>
lunarToSolar("20220101"), 农历转阳历(输入："20220101", 输出："20220201")<br/>
inputSolarDate("19991229"), 输出json数据(输入："19991229", 输出：
```json
{
    "solarDate":"19991229", // 阳历日期
    "lunarDate":"19991122", // 农历日期
    "dayOfWeek":"星期三", // 星期几
    "chineseZodiac":"卯兔", // 属相
    "constellation":"摩羯座", // 星座
    "ganZhi":"已卯", // 天干地支：如甲午, 辛亥, 戊戌等
    "solarTerm":"", // 节气：如清明, 大暑, 冬至等
    "lunarChinese":"冬月廿二" //农历中文日期 
}
```
)<br/>
inputLunarDate("19991122"), 输出json数据(输入："19991122", 输出：
```json
{
    "solarDate":"19991229",
    "lunarDate":"19991122",
    "dayOfWeek":"星期三",
    "chineseZodiac":"卯兔",
    "constellation":"摩羯座",
    "ganZhi":"已卯",
    "solarTerm":""
}
```
)<br/>

实现思路：<br/>
1.从香港天文台 爬取(1900,2100]的阳历和阴历的对照表 https://www.hko.gov.hk/tc/gts/time/calendar/text/files/T2022c.txt, 项目中放在resouces/utils/lunar_solar_map<br/>
2.根据第一步的数据，做查表功能实现<br/>

待补充功能：<br/>
1.能够支持给定一个农历中文的读法来, 输出对应的结果<br/>
  a.比如：输入"二零二零年闰四月初一" 和 "二零二零年四月初一"对应的公里日期应该是不一致的, 但是目前并无法区分开来<br/>
2.计算两个日期之间间隔的天数
