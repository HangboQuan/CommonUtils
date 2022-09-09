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
 * @date 22-9-9 下午7:57
 */
public class SimpleProvinceJSONUtils {

	public static void main(String[] args) {
		List<String> provinceList = loadDataFromClassPathResource("utils/province.txt");
		List<String> cityList = loadDataFromClassPathResource("utils/city.txt");
		List<String> zoneList = loadDataFromClassPathResource("utils/zone.txt");
		List<String> streetList = loadDataFromClassPathResource("utils/street.txt");
		List<String> stayList = loadDataFromClassPathResource("utils/stay.txt");
		
		
		List<Province.City.Zone> subZoneList = zoneStreetStayInfo(zoneList, cityList, stayList);
		List<String> provinces = new ArrayList<>();
		List<String> cities = new ArrayList<>();
		for(int i = 0; i < provinceList.size(); i ++ ){
			String[] provinceStr = provinceList.get(i).split("\t");
			Province province = new Province();
			province.setProvince(provinceStr[1]);
			
			List<Province.City> subCityList = new ArrayList<>();
			for(int j = 0; j < cityList.size(); j ++ ) {
				String[] citiesStr = cityList.get(j).split("\t");
				Province.City city = new Province.City();
				city.setCity(citiesStr[1]);
				if(citiesStr[0].substring(0, 2).equals(provinceStr[0].substring(0, 2))) {
					
					for(int k = 0; k < subZoneList.size(); k ++ ) {
					
					}
					System.out.println(new Gson().toJson(city, Province.City.class));
					subCityList.add(city);
					cities.add(new Gson().toJson(city, Province.City.class));
					
				}
				
			}
			province.setCities(subCityList);
			System.out.println(new Gson().toJson(province, Province.class));
			provinces.add(new Gson().toJson(province, Province.class));
		}
		
	}
	
	public static List<Province.City.Zone> zoneStreetStayInfo(List<String> zoneList, List<String> streetList, List<String> stayList) {
		List<Province.City.Zone> zones = new ArrayList<>();
		for(int i = 0; i < zoneList.size(); i ++ ) {
			String[] zoneStr = zoneList.get(i).split("\t");
			Province.City.Zone zone = new Province.City.Zone();
			zone.setZone(zoneStr[1]);
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
		return zones;
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