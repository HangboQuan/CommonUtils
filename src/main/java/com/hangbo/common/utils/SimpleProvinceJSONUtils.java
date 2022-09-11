package com.hangbo.common.utils;

import com.google.gson.Gson;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author quanhangbo
 * @date 22-9-9 下午7:57
 */
public class SimpleProvinceJSONUtils {

	public static void main(String[] args) {
		List<String> provinceList = loadDataFromClassPathResource("utils/province.txt");
		List<String> cityList = loadDataFromClassPathResource("utils/city.txt");
		List<String> zoneList = loadDataFromClassPathResource("utils/zone.txt");
		List<String> streetList = loadDataFromClassPathResource("utils/street.txt");
		List<String> stayList = loadDataFromClassPathResource("utils/stay.txt");
		provinceInfo(provinceList, cityList, zoneList, streetList, stayList);
	}
	
//	public static List<Province.City.Zone> zoneStreetStayInfo(List<String> zoneList, List<String> streetList, List<String> stayList) {
//		List<Province.City.Zone> zones = new ArrayList<>();
//		for(int i = 0; i < zoneList.size(); i ++ ) {
//			String[] zoneStr = zoneList.get(i).split("\t");
//			Province.City.Zone zone = new Province.City.Zone();
//			zone.setZone(zoneStr[1]);
//			List<Province.City.Zone.Street> streets = new ArrayList<>();
//			for(int j = 0; j < streetList.size(); j ++ ) {
//				String[] streetStr = streetList.get(j).split("\t");
//				if(zoneStr[0].substring(0, 6).equals(streetStr[0].substring(0, 6))) {
//					Province.City.Zone.Street street = new Province.City.Zone.Street();
//					street.setStreet(streetStr[1]);
//					List<Province.City.Zone.Street.Stay> stays = new ArrayList<>();
//					for(int k = 0; k < stayList.size(); k ++ ) {
//						String[] subStayList = stayList.get(k).split("\t");
//						Province.City.Zone.Street.Stay stay = new Province.City.Zone.Street.Stay();
//						if(subStayList[0].equals(streetStr[0])) {
//							List<String> subList = new ArrayList<>();
//							for(int m = 1; m < subStayList.length; m ++ ) {
//								subList.add(subStayList[m]);
//							}
//							stay.setStay(subList);
//							stays.add(stay);
//							break;
//						}
//					}
//					street.setStays(stays);
//					streets.add(street);
//				}
//			}
//			zone.setStreets(streets);
//			System.out.println(new Gson().toJson(zone, Province.City.Zone.class));
//			zones.add(zone);
//		}
//		return zones;
//	}
	
	public static void provinceInfo(List<String> provinceList, List<String> cityList, List<String> zoneList,
	                                List<String> streetList, List<String> stayList){
		List<String> provinces = new ArrayList<>();
		List<String> cityJSON = new ArrayList<>();
		for(int x = 0; x < provinceList.size(); x ++ ) {
			String[] provinceStr = provinceList.get(x).split("\t");
			Province province = new Province();
			province.setProvince(provinceStr[1]);
			List<Province.City> cities = new ArrayList<>();
			for(int y = 0; y < cityList.size(); y ++ ) {
				String[] cityStr = cityList.get(y).split("\t");
				if(provinceStr[0].substring(0, 2).equals(cityStr[0].substring(0, 2))) {
					Province.City city = new Province.City();
					city.setCity(cityStr[1]);
					List<Province.City.Zone> zones = new ArrayList<>();
					for(int i = 0; i < zoneList.size(); i ++ ) {
						String[] zoneStr = zoneList.get(i).split("\t");
						Province.City.Zone zone = new Province.City.Zone();
						zone.setZone(zoneStr[1]);
						if(cityStr[0].substring(0, 4).equals(zoneStr[0].substring(0, 4))) {
							List<Province.City.Zone.Street> streets = new ArrayList<>();
							for(int j = 0; j < streetList.size(); j ++ ) {
								String[] streetStr = streetList.get(j).split("\t");
								if(zoneStr[0].substring(0, 6).equals(streetStr[0].substring(0, 6))) {
									Province.City.Zone.Street street = new Province.City.Zone.Street();
									street.setStreet(streetStr[1]);
									List<Province.City.Zone.Street.Stay> stays = new ArrayList<>();
									for(int k = 0; k < stayList.size(); k ++ ) {
										String[] subStayList = stayList.get(k).split("\t");
										Province.City.Zone.Street.Stay stay = new Province.City.Zone.Street.Stay();
										if(subStayList[0].equals(streetStr[0])) {
											List<String> subList = new ArrayList<>();
											for(int m = 1; m < subStayList.length; m ++ ) {
												subList.add(subStayList[m]);
											}
											stay.setStay(subList);
											stays.add(stay);
											break;
										}
									}
									street.setStays(stays);
									streets.add(street);
								}
							}
							zone.setStreets(streets);
							zones.add(zone);
						}
					}
					city.setZones(zones);
					System.out.println(new Gson().toJson(city, Province.City.class));
					cityJSON.add(new Gson().toJson(city, Province.City.class));
					cities.add(city);
					
				}
			}
			province.setCities(cities);
			System.out.println(new Gson().toJson(province, Province.class));
			provinces.add(new Gson().toJson(province, Province.class));
			
		}
		try{
			IOUtils.writeLines(cityJSON, null, new FileOutputStream(new File("/home/h/cities.txt")));
			IOUtils.writeLines(provinces, null, new FileOutputStream(new File("/home/h/provinces.txt")));
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	@Accessors
	public static class Province {
		String province;
		List<City> cities;
		
		
		@Data
		@Accessors
		static class City {
			String city;
			List<Zone> zones;
			
			@Data
			@Accessors
			static class Zone {
				String zone;
				List<Street> streets;
				
				@Data
				@Accessors
				static class Street {
					String street;
					List<Stay> stays;
					
					
					@Data
					@Accessors
					static class Stay {
						List<String> stay;
					}
				}
			}
		}
	}
}