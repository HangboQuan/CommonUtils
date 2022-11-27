package com.hangbo.common.utils;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * @author quanhangbo
 * @date 22-11-27 下午2:57
 */
public class PinyinConvertChinese {

	/**
	 * 试用拼音 -> 汉字 玩了玩 但是对于多音字就有问题了
	 *
	 * 思考如何能解决多音字的问题?
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String str = "好读书读好书好读书";
		// hǎo,dú,shū,dú,hǎo,shū,hǎo,dú,shū
		System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK));

		// rú,tīng,xiān,lè,ěr,zàn,míng
		String newString = "如听仙乐耳暂明";
		System.out.println(PinyinHelper.convertToPinyinString(newString, ",", PinyinFormat.WITH_TONE_MARK));


		// hàn,tǒng,shuāi,luò,huàn,guān,niàng,huò,guó,luàn,suì,xiōng,sì,fāng,rǎo,rǎng
		String oldString = "汉统衰落宦官酿祸国乱岁凶四方扰攘";
		System.out.println(PinyinHelper.convertToPinyinString(oldString, ",", PinyinFormat.WITH_TONE_MARK));
	}
}
