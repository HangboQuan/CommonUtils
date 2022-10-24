package com.hangbo.common.utils;


import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author quanhangbo
 * @date 22-10-24 下午9:00
 */
public class ChineseConvertPinyinSort {

	public static void main(String[] args) {
		Collator cmp = Collator.getInstance(Locale.CHINA);
		// [安息, 爸爸, 北京, 纽约, 中国]
		String[] arr = {"安息", "北京", "爸爸", "中国", "纽约"};

		// [昌平区, 朝阳区, 大兴区, 东城区, 房山区, 丰台区, 海淀区, 怀柔区, 门头沟区, 密云区, 平谷区, 石景山区, 顺义区, 通州区, 西城区, 延庆区, 重庆市] 重庆市因为多音字的问题错误
		String[] cities = {"重庆市","东城区","西城区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","密云区","延庆区"};

		List<String> list = Arrays.asList(arr);
		List<String> citiesList = Arrays.asList(cities);

		Collections.sort(list, cmp);
		Collections.sort(citiesList, cmp);
		System.out.println(list);
		System.out.println(citiesList);

	}
}
