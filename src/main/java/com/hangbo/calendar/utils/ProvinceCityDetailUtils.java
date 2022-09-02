package com.hangbo.calendar.utils;

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
 * @date 2022/9/1 8:42
 */
public class ProvinceCityDetailUtils {

	public static void main(String[] args) throws Exception {
		List<String> provinceList = loadDataFromClassPathResource("utils/province.txt");
		List<String> cityList = loadDataFromClassPathResource("utils/city.txt");
		List<String> zoneList = loadDataFromClassPathResource("utils/zone.txt");
		List<String> streetList = loadDataFromClassPathResource("utils/street.txt");
		List<String> stayList = loadDataFromClassPathResource("utils/stayV5.txt");
//		for (int j = 0; j < cityList.size(); j ++ ) {
//			String[] cityStr = cityList.get(j).split("\t");
//			City city = new City();
//			city.setCityCode(cityStr[0]);
//			city.setCityName(cityStr[1]);
//			List<Zone> subZoneList = new ArrayList<>();
//			for (int k = 0; k < zoneList.size(); k ++ ) {
//				String[] zoneStr = zoneList.get(k).split("\t");
//				if (cityStr[0].substring(0, 4).equalsIgnoreCase(zoneStr[0].substring(0, 4))) {
//					Zone zone = new Zone();
//					zone.setZoneCode(zoneStr[0]);
//					zone.setZoneName(zoneStr[1]);
//					List<Street> subStreetList = new ArrayList<>();
//					for (int m = 0; m < streetList.size(); m ++ ) {
//						String[] streetStr = streetList.get(m).split("\t");
//						if (zoneStr[0].substring(0, 6).equalsIgnoreCase(streetStr[0].substring(0, 6))) {
//							Street street = new Street();
//							street.setStreetCode(streetStr[0]);
//							street.setStreetName(streetStr[1]);
//							List<Stay> resStayList = new ArrayList<>();
//							for (int n = 0; n < stayList.size(); n ++ ) {
//								String[] stayStr = stayList.get(n).split("\t");
//								if (streetStr[0].equalsIgnoreCase(stayStr[0])) {
//									List<String> subStayList = new ArrayList<>();
//									for (int s = 1; s < stayStr.length; s ++ ) {
//										subStayList.add(stayStr[s]);
//									}
//									Stay stay = new Stay();
//									stay.setStayCode(stayStr[0]);
//									stay.setStayDetail(subStayList);
//									resStayList.add(stay);
//									break;
//								}
//							}
//							street.setStay(resStayList);
//							subStreetList.add(street);
//							zone.setStreet(subStreetList);
//							subZoneList.add(zone);
//							city.setZone(subZoneList);
//                            System.out.println(city.toString());
//						}
//					}
//				}
//			}
//		}
		
		List<Zone> subZoneList = zoneStreetStayInfo(zoneList, streetList, stayList);
//		for (int i = 0; i <provinceList.size(); i ++ ) {
//			Province province = new Province();
//			province.setProvinceCode(provinceList.get(i).)
//		}
		for (int j = 0; j < cityList.size(); j ++ ) {
			City city = new City();
			city.setCityCode(cityList.get(j).split("\t")[0]);
			city.setCityName(cityList.get(j).split("\t")[1]);
			
			
			for(int k = 0; k < subZoneList.size(); k ++ ) {
				if (cityList.get(j).split("\t")[0].substring(0, 4)
						    .equalsIgnoreCase(subZoneList.get(k).getZoneCode().substring(0, 4))) {
					subZoneList.add(subZoneList.get(k));
					city.setZone(subZoneList);
				}
			}
			
			System.out.println(new Gson().toJson(city, City.class));
		}
		
		
		for(Zone z : subZoneList) {
			System.out.println(new Gson().toJson(z, Zone.class));
		}
	}
	
	public static List<Zone> zoneStreetStayInfo(List<String> zoneList, List<String> streetList, List<String> stayList) {
		
		List<Zone> subZoneList = new ArrayList<>();
		for (int k = 0; k < zoneList.size(); k ++ ) {
			String[] zoneStr = zoneList.get(k).split("\t");
			Zone zone = new Zone();
			zone.setZoneCode(zoneStr[0]);
			zone.setZoneName(zoneStr[1]);
			List<Street> subStreetList = new ArrayList<>();
			for (int m = 0; m < streetList.size(); m ++ ) {
				String[] streetStr = streetList.get(m).split("\t");
				if (zoneStr[0].substring(0, 6).equalsIgnoreCase(streetStr[0].substring(0, 6))) {
					Street street = new Street();
					street.setStreetCode(streetStr[0]);
					street.setStreetName(streetStr[1]);
					List<Stay> resStayList = new ArrayList<>();
					for (int n = 0; n < stayList.size(); n ++ ) {
						String[] stayStr = stayList.get(n).split("\t");
						if (streetStr[0].equalsIgnoreCase(stayStr[0])) {
							List<String> subStayList = new ArrayList<>();
							for (int s = 1; s < stayStr.length; s ++ ) {
								subStayList.add(stayStr[s]);
							}
							Stay stay = new Stay();
							stay.setStayCode(stayStr[0]);
							stay.setStayDetail(subStayList);
							resStayList.add(stay);
							break;
						}
					}
					street.setStay(resStayList);
					subStreetList.add(street);
					zone.setStreet(subStreetList);
				}
			}
			subZoneList.add(zone);
			System.out.println(new Gson().toJson(zone, Zone.class));
		}
		return subZoneList;
	}
	
	
	public static List<String> loadDataFromClassPathResource(String path) {
		try {
			ClassPathResource resource = new ClassPathResource(path);
			InputStream inputStream = resource.getInputStream();
			return IOUtils.readLines(inputStream, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Data
	@Accessors(chain = true)
	static class Province {
		String provinceCode;
		String provinceName;
		List<City> city;
	}
	
	@Data
	@Accessors(chain = true)
	static class City {
		String cityCode;
		String cityName;
		List<Zone> zone;
	}
	
	@Data
	@Accessors(chain = true)
	static class Zone {
		String zoneCode;
		String zoneName;
		List<Street> street;
	}
	
	@Data
	@Accessors(chain = true)
	static class Street {
		String streetCode;
		String streetName;
		List<Stay> stay;
	}
	
	@Data
	@Accessors(chain = true)
	static class Stay {
		String stayCode;
		List<String> stayDetail;
	}
}
