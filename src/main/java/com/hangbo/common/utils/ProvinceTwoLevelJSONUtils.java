package com.hangbo.common.utils;

import com.google.gson.Gson;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author quanhangbo
 * @date 22-10-23 下午3:26
 */
public class ProvinceTwoLevelJSONUtils {

	/*public static void main(String[] args) {

		try {
			ClassPathResource resource = new ClassPathResource("utils/provinces_cities_zones.txt");
			InputStream inputStream = resource.getInputStream();
			List<String> info = IOUtils.readLines(inputStream, "utf-8");

			for(int i = 0; i < info.size(); i ++ ) {
				JSONObject provinceJson = JSON.parseObject(info.get(i));
				String province = (String)provinceJson.get("province");
				StringBuilder sb = new StringBuilder();
				sb.append(province).append("|");
				if("北京市".equals(province) || "天津市".equals(province) || "上海市".equals(province) || "重庆市".equals(province)) {
					// 单独处理直辖市
					JSONArray jsonArray = provinceJson.getJSONArray("cities");
					JSONObject jsonObject = (jsonArray.getJSONObject(0));
					JSONArray jsonArray1 = jsonObject.getJSONArray("zones");
					JSONArray jsonArray2 = jsonArray1.getJSONObject(0).getJSONArray("zone");

					for(int j = 0; j < jsonArray2.size(); j ++ ) {
						if("省直辖县级行政区划".equals(jsonArray2.get(j))) {
							continue;
						}
						sb.append(jsonArray2.get(j)).append("|");
					}

					sb.delete(sb.length() - 1, sb.length());
				} else {
					JSONArray jsonArray = provinceJson.getJSONArray("cities");
					for(int j = 0; j < jsonArray.size(); j ++ ) {
						JSONObject jsonObject = jsonArray.getJSONObject(j);
						String city = (String)jsonObject.get("city");

						sb.append(city).append("|");
						if("省直辖县级行政区划".equals(city)) {
							sb.delete(sb.indexOf("省直辖县级行政区划|"), sb.length());
							JSONArray jsonArray1 = jsonArray.getJSONObject(j).getJSONArray("zones");
							JSONArray jsonArray2 = jsonArray1.getJSONObject(0).getJSONArray("zone");
							for(int k = 0; k < jsonArray2.size(); k ++ ) {
								if("省直辖县级行政区划".equals(jsonArray2.get(k))) {
									continue;
								}
								sb.append(jsonArray2.get(k)).append("|");
							}
						}
						if("自治区直辖县级行政区划".equals(city)) {
							sb.delete(sb.indexOf("自治区直辖县级行政区划|"), sb.length());
							JSONArray jsonArray1 = jsonArray.getJSONObject(j).getJSONArray("zones");
							JSONArray jsonArray2 = jsonArray1.getJSONObject(0).getJSONArray("zone");
							for(int k = 0; k < jsonArray2.size(); k ++ ) {
								sb.append(jsonArray2.get(k)).append("|");
							}
						}
					}
					sb.delete(sb.length() - 1, sb.length());

				}
				System.out.println(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}



	}*/

	public static void main(String[] args) {
		try {
			ClassPathResource resource = new ClassPathResource("/utils/province_city_more_details.txt");
			InputStream inputStream = resource.getInputStream();
			List<String> info = IOUtils.readLines(inputStream, "utf-8");

			// 这个只有普通的省 + 市的json数据
//			for(int i = 0; i < info.size(); i ++ ) {
//				String[] ans = info.get(i).split("\\|");
//				Province province = new Province();
//				province.setProvince(ans[0]);
//
//				List<String> cities = new ArrayList<>();
//				for(int j = 1; j < ans.length; j ++ ) {
//					cities.add(ans[j]);
//				}
//				province.setCities(cities);
//				System.out.println(new Gson().toJson(province));
//			}

			// 加上中国的json数据
			Country country = new Country();
			country.setCountry("中国");

			List<Province> provinces = new ArrayList<>();
			for(int i = 0; i < info.size(); i ++ ) {
				String[] ans = info.get(i).split("\\|");
				Province province = new Province();
				province.setProvince(ans[0]);
				List<String> cities = new ArrayList<>();
				for(int j = 1; j < ans.length; j ++ ) {
					cities.add(ans[j]);
				}
				province.setCities(cities);
				provinces.add(province);
			}
			country.setProvinces(provinces);
			System.out.println(new Gson().toJson(country));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Data
	@Accessors
	public static class Province {
		private String province;
		private List<String> cities;
	}

	@Data
	@Accessors
	public static class Country {
		private String country;
		private List<Province> provinces;
	}
}
