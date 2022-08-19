package com.hangbo.calendar.handler;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FestivalHandler {

	/**
	 * 农历-阳历 映射表 数据来源于: 香港天文台 https://www.hko.gov.hk/tc/gts/time/calendar/text/files/T2022c.txt
	 */

	private static final String[] numMap = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
	private static final String[] monthMap = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
	private static final String[] lunarMap = {"初", "十", "廿"};


	/**
	 * 农历节日:
	 * 春节: 正月初一
	 * 元宵节: 正月十五
	 * 清明节: 公历4月5日前后, 依照24节气判断
	 * 端午节: 五月初五
	 * 七夕节: 七月初七
	 * 中元节: 七月十五
	 * 中秋节: 八月十五
	 * 重阳节: 九月初九
	 * 下元节: 十月十五
	 * 冬至节: 依照24节气判断
	 * 北方小年: 十二月二十三
	 * 南方小年: 十二月二十四
	 * 除夕: 十二月廿九或者十二月三十
	 *
	 * 公历节日：
	 * 新年：1.1
	 * 情人节：2.14
	 * 妇女节：3.8
	 * 植树节：3.12
	 * 劳动节：5.1
	 * 儿童节：6.1
	 * 建党节：7.1
	 * 建军节：8.1
	 * 教师节：9.10
	 * 国庆节：10.1
	 * 圣诞节：12.25
	 */
	
	public static HashMap<String, String> festivalLunarOfYear(String year){
		// key : 节日名称  value : 阳历节日时间
		HashMap<String, String> map = new HashMap<>();

        // 农历节假日
        map.put("春节", lunarToSolarDate(year + "0101"));
        map.put("元宵节", lunarToSolarDate(year + "0115"));
        map.put("端午节", lunarToSolarDate(year + "0505"));
        map.put("七夕节", lunarToSolarDate(year + "0707"));
        map.put("中元节", lunarToSolarDate(year + "0715"));
        map.put("中秋节", lunarToSolarDate(year + "0815"));
        map.put("重阳节", lunarToSolarDate(year + "0909"));
        map.put("下元节", lunarToSolarDate(year + "1015"));
        map.put("北方小年", lunarToSolarDate(year + "1223"));
        map.put("南方小年", lunarToSolarDate(year + "1224"));
        
        // 清明/冬至/除夕
		
		List<String> ans = loadLunarSolarMap();
		if (ans == null) {
			return null;
		}
		for(int i = 0; i < ans.size(); i ++ ){
			String[] s = ans.get(i).split("\t");
			if (year.equals(s[0].substring(0, s[0].indexOf("年"))) && s.length > 3){
				if ("清明".equals(s[3])){
					map.put("清明节", chineseConvertOfSolar(s[0]));
				}
				if ("冬至".equals(s[3])){
					map.put("冬至节", chineseConvertOfSolar(s[0]));
				}
			}
			
			if ("正月".equals(s[1])) {
				int currentYear = Integer.parseInt(year);
				if (Integer.parseInt(s[0].substring(0, s[0].indexOf("年"))) == currentYear + 1){
					String cur = ans.get(i - 1);
					map.put("除夕", chineseConvertOfSolar(cur.split("\t")[0]));
				}
			}
		}
		
		return map;
	}

	public static List<String> loadLunarSolarMap(){
		try{
			String path = "/utils/lunar_solar_map.txt";
			ClassPathResource resource = new ClassPathResource(path);
			InputStream inputStream = resource.getInputStream();
			return IOUtils.readLines(inputStream, "utf-8");
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 给定一个农历日期 19991122 -> 阳历日期 19991229
	 */

	public static String lunarToSolarDate(String date){
		try{
			List<String> ans = loadLunarSolarMap();
			if (ans == null) {
				return null;
			}
			/**
			 * 先从映射表中找到农历(19991122) -> 阳历 19991122 十五 星期一,
			 *
			 * 农历一般比阳历迟20-50左右, 向后遍历
			 */
			for(int i = 0; i < ans.size(); i ++ ){
				String s = ans.get(i);
				String[] strings = s.split("\t");
				int year = Integer.parseInt(strings[0].substring(0, strings[0].indexOf("年")));
				int month = Integer.parseInt(strings[0].substring(strings[0].indexOf("年") + 1, strings[0].indexOf("月")));
				int day = Integer.parseInt(strings[0].substring(strings[0].indexOf("月") + 1, strings[0].indexOf("日")));

				if(year == Integer.parseInt(date.substring(0, 4)) && month == Integer.parseInt(date.substring(4, 6))
						&& day == Integer.parseInt(date.substring(6, 8))){
					int k = i;
					int j = i;
					String afterDuration;
					String beforeDuration;
					String temp = "";
					boolean flag = false;

					// 判断是否在前面
					
					while (!flag) {
						afterDuration = ans.get(k).split("\t")[1];
						beforeDuration = ans.get(j).split("\t")[1];
						if (afterDuration.contains("月") && month == chineseConvertMonthOfLunar(afterDuration.substring(0, afterDuration.indexOf("月")))){
							flag = true;
							temp = ans.get(k - 1 + day).split("\t")[0];
						} else if (beforeDuration.contains("月") && month == chineseConvertMonthOfLunar(beforeDuration.substring(0, beforeDuration.indexOf("月")))) {
							flag = true;
							temp = ans.get(j - 1 + day).split("\t")[0];
						} else{
							j --;
							k ++;
						}
					}
					String solarYear = (temp.substring(0, temp.indexOf("年")));
					String solarMonth = (temp.substring(temp.indexOf("年") + 1, temp.indexOf("月")));
					String solarDay = (temp.substring(temp.indexOf("月") + 1, temp.indexOf("日")));
					if(solarMonth.length() == 1){
						solarMonth = "0" + solarMonth;
					}
					if(solarDay.length() == 1){
						solarDay = "0" + solarDay;
					}

					return solarYear + solarMonth + solarDay;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 给定一个阳历日期 -> 转为 农历
	 * @param date 20000229 -> 20000125
	 * @return
	 */
	public static String solarToLunarDate(String date) {
		 // 直接根据 阳历的映射关系 找到 对应的农历 然后处理下年份
		List<String> ans = loadLunarSolarMap();
		if (ans == null) {
			return null;
		}
		for(int i = 0; i < ans.size(); i ++ ){
			String s = ans.get(i);
			String[] strings = s.split("\t");
			int year = Integer.parseInt(strings[0].substring(0, strings[0].indexOf("年")));
			int month = Integer.parseInt(strings[0].substring(strings[0].indexOf("年") + 1, strings[0].indexOf("月")));
			int day = Integer.parseInt(strings[0].substring(strings[0].indexOf("月") + 1, strings[0].indexOf("日")));

			if(year == Integer.parseInt(date.substring(0, 4)) && month == Integer.parseInt(date.substring(4, 6))
					&& day == Integer.parseInt(date.substring(6, 8))){

				int j = i;

				String res = "";
				while (!ans.get(j).split("\t")[1].contains("月")) {
					j --;
				}

				// ans.get(j).split("\t")[1]获取的是月份
				if( ans.get(j).split("\t")[1].equals(strings[1]) ){
					res = strings[1];
				} else {
					// 这里是做了拼接: 比如十一月初二
					res += ans.get(j).split("\t")[1] + strings[1];
				}

				int lunarYear = year;

        		String calendar = lunarToSolarDate(year + "0101");
        		LocalDate newYearOfSolar = LocalDate.of(year, 1, 1);
        		LocalDate newYearOfLunar = LocalDate.of(Integer.parseInt(calendar.substring(0, 4))
					,Integer.parseInt(calendar.substring(4, 6))
					,Integer.parseInt(calendar.substring(6, 8)));
        		LocalDate curDate = LocalDate.of(Integer.parseInt(date.substring(0, 4))
						,Integer.parseInt(date.substring(4, 6))
						,Integer.parseInt(date.substring(6, 8)));

        		if ((curDate.isAfter(newYearOfSolar) && curDate.isBefore(newYearOfLunar) ||
						curDate.isEqual(newYearOfSolar))) {
        			lunarYear --;
				}
				res = lunarYear + chineseConvertNumberOfLunar(res);
				return res;

			}
		}

		return null;
	}


	/**
	 * 十二月初一 -> 1201
	 * @param date
	 * @return
	 */
	public static String chineseConvertNumberOfLunar(String date){
		String month = date.substring(0, date.indexOf("月"));
		String day = date.substring(date.indexOf("月") + 1);
		String resMonth = chineseConvertMonthOfLunar(month) + "";
		String resDay = chineseConvertDayOfLunar(day);

		if(resMonth.length() == 1){
			resMonth = "0" + resMonth;
		}

		if(resDay.length() == 1){
			resDay = "0" + resDay;
		}
		return resMonth + resDay;
	}

	/**
	 * date = '初一', '初二', '二十','廿五', '三十'
	 * @param date
	 * @return 1 2 25 30
	 */
	public static String chineseConvertDayOfLunar(String date){

		// 映射表中 只有六月 / 闰六月 代表 六月初一/闰六月初一
		if(StringUtils.isBlank(date)){
			return "01";
		}
		if("初十".equalsIgnoreCase(date)) {
			return "10";
		}
		if("二十".equalsIgnoreCase(date)) {
			return "20";
		}

		if("三十".equalsIgnoreCase(date)){
			return "30";
		}

		String day = date.substring(date.length() - 1);
		int i = 0;
		while(!day.equalsIgnoreCase(numMap[i])){
			i ++;
		}
		if(date.startsWith(lunarMap[0])) {
			return "0" + (i + 1);
		} else if (date.startsWith(lunarMap[1])) {
			return 11 + i + "";
		} else if (date.startsWith(lunarMap[2])) {
			return 21 + i + "";
		}
		return "";
	}


	/**
	 * 正|冬|腊(月) 但是在映射表中只有 正月, 11月 12月 还是用 十一月 和 十二月代替了
	 * @param date
	 * @return 正 ->  1 二 -> 2
	 */
	public static int chineseConvertMonthOfLunar(String date){
		if (date.contains("闰")) {
			date = date.substring(1);
		}
		for(int i = 0; i < monthMap.length; i ++ ){
			if(date.equalsIgnoreCase(monthMap[i])){
				return i + 1;
			}
		}
		return -1;
	}
	
	public static String chineseConvertOfSolar(String date){
		String year = date.substring(0, date.indexOf("年"));
		String month = date.substring(date.indexOf("年") + 1, date.indexOf("月"));
		String day = date.substring(date.indexOf("月") + 1, date.indexOf("日"));
		if (Integer.parseInt(month) < 10 && month.length() != 2){
			month = "0" + month;
		}
		if (Integer.parseInt(day) < 10 && day.length() != 2){
			day = "0" + day;
		}
		return year + month + day;
	}

	public static void main(String[] args){
		// 阳历转阴历
		System.out.println(solarToLunarDate("19991229"));

		// 阴历转阳历
		System.out.println(lunarToSolarDate("19991122"));

		/**
		 * 七夕节 20220804 中元节 20220812 除夕 20230121 下元节 20221108 北方小年 20230114 重阳节 20221004 元宵节 20220215
		 * 南方小年 20230115 中秋节 20220910 清明节 20220405 春节 20220201 端午节 20220603 冬至节 20221222
		 */
    	HashMap<String, String> map = festivalLunarOfYear("2022");
		for (Map.Entry<String, String> m : map.entrySet()){
            System.out.println(m.getKey() + " " + m.getValue());
		}

		/**
		 * 农历2020年 4月是有闰4月  如果输入的农历日期 这里默认的是没有闰月的情况
		 * 农历："20200401" 阳历："20200423"
		 * 农历："20200430" 阳历："20200522"
		 * 农历："20200501" 阳历："20200621" (其中的间隔就是闰四月一个月的日期)
		 *
		 * 这里看到 如果想找到闰4月的对应的 阳历， 先得考虑如何输入闰四月呢
		 */
		lunarToSolarDate("20200401");
		lunarToSolarDate("20200430");
		lunarToSolarDate("20200501");

		LocalDate localDate = LocalDate.of(2020, 1, 1);
		int step = 1;

		for(int i = 0; i < 700; i ++ ){
      		System.out.println(formatLocalDate(localDate) + " " + solarToLunarDate(formatLocalDate(localDate)));
			localDate = localDate.plusDays(step);
		}

		/**
		 * 农历2020年 4月是闰4月
		 * 阳历: "20200423" 农历: "20200401"
		 * 阳历: "20200523" 农历: "20200401" (该日期为闰4月，和上面未做区分)
		 * 阳历: "20200621" 农历: "20200501"
		 */
		System.out.println(solarToLunarDate("19991117"));
    	System.out.println(solarToLunarDate("20201216"));
    	System.out.println(solarToLunarDate("20210212"));
    	System.out.println(solarToLunarDate("20200101"));
	}

	public static String formatLocalDate(LocalDate localDate){
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();

		String date = String.valueOf(year);
		if (month < 10) {
			date += "0" + month;
		} else {
			date += month;
		}

		if (day < 10) {
			date += "0" + day;
		} else {
			date += day;
		}
		return date;

	}
}
