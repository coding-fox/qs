/**
 * 
 */
package org.brjia.qs.parse.parser;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.brjia.qs.parse.beans.Auxiliary;
import org.brjia.qs.parse.beans.Comprehensive;
import org.brjia.qs.parse.beans.Outline;
import org.brjia.qs.parse.beans.OutputAmount;
import org.brjia.qs.parse.beans.Tank;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 2015年9月19日
 */
@Component
public class TextParser {
	private final static String BANGHUI_ST="的联赛统计信息:";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

	public Outline parse(String str){
    	return parseOutline(fetchContent(str));
	}
	public List<String> fetchContent(String str){
		
		List<String> titles = initTitles();
		
		List<String> result = new ArrayList<String>();
		
		int currentIndex = 0;
		
		for(String title:titles){
			
			currentIndex = str.indexOf(title);
			
			result.add(str.substring(currentIndex, str.length()));
			
			str = str.substring(0,currentIndex);
		}
		
		//帮会名称 
		result.add(str.substring(0,str.indexOf(BANGHUI_ST)));
		
		return result;
		
	}
	
	/**
	 * @return
	 */
	private List<String> initTitles() {
		return Arrays.asList("战车统计","治疗量","承受攻击量","输出量","辅助信息","综合信息");
	}

	public Date parseDate(String str) throws ParseException{
		
		return sdf.parse(str,new ParsePosition(str.length()-23));
	}
	public Outline parseOutline(List<String> data){
		
		List<Tank> tank = parserTank(data.get(0));
		List<OutputAmount> cure = parserOutput(data.get(1));
		List<OutputAmount> bear = parserOutput(data.get(2));
		List<OutputAmount> out = parserOutput(data.get(3));
		List<Auxiliary> assist = parserAssist(data.get(4));
		String name = parserGangName(data.get(6));
		
		Outline result = new Outline();
		
		result.setAssist(assist);
		result.setCure(cure);
		result.setOut(out);
		result.setGang(name);
		result.setMain(parserMain(data.get(5)));
		result.setBear(bear);
		result.setTank(tank);
		
		return result;
		
	}
	
	/**
	 * @param string
	 * @return
	 */
	private String parserGangName(String string) {
		return string;
	}

	/**
	 * @param string
	 * @return
	 */
	private List<Comprehensive> parserMain(String string) {
		Pattern compile = Pattern.compile("^(\\S{1,})\\s+(\\d{1,3})\\s+(\\D{1,3})\\s+(\\d{1,3})\\s+(\\d{1,3})\\s+(\\d{1,3})\\s+(\\d{1,6})$", Pattern.MULTILINE);
		Matcher matcher = compile.matcher(string);
		List<Comprehensive> result = new ArrayList<Comprehensive>();
		Comprehensive item = null;
		while(matcher.find()){
			item = new Comprehensive();
			item.setName(matcher.group(1));
			item.setLevel(matcher.group(2));
			item.setProfession(matcher.group(3));
			item.setKill(Integer.parseInt(matcher.group(4)));
			item.setAssists(Integer.parseInt(matcher.group(5)));
			item.setRepair(Integer.parseInt(matcher.group(6)));
			item.setAccumulate(Integer.parseInt(matcher.group(7)));
			result.add(item);
		}
		return result;
	}

	/**
	 * @param string
	 * @return
	 */
	private List<Auxiliary> parserAssist(String string) {
		Pattern compile = Pattern.compile("^(\\S{1,})\\s+(\\d{1,3})\\s+(\\D{1,3})\\s+(\\d{1,3})\\s+(\\d{1,3})\\s+(\\d{1,3})$", Pattern.MULTILINE);
		Matcher matcher = compile.matcher(string);
		List<Auxiliary> result = new ArrayList<Auxiliary>();
		Auxiliary item = null;
		while(matcher.find()){
			item = new Auxiliary();
			item.setName(matcher.group(1));
			item.setLevel(matcher.group(2));
			item.setProfession(matcher.group(3));
			item.setKilledCount(Integer.parseInt(matcher.group(4)));
			item.setBaoshi(Integer.parseInt(matcher.group(5)));
			item.setRelive(Integer.parseInt(matcher.group(6)));
			result.add(item);
		}
		return result;
	}

	/**
	 * @param string
	 * @return
	 */
	private List<OutputAmount> parserOutput(String string) {
		
		Pattern compile = Pattern.compile("^\\d{1,3}\\s+(\\S{1,})\\s+(\\D{1,3})\\s+(\\d+)\\(\\d+\\.\\d+%\\)$", Pattern.MULTILINE);
		Matcher matcher = compile.matcher(string);
		List<OutputAmount> result = new ArrayList<OutputAmount>();
		OutputAmount item = null;
		while(matcher.find()){
			item = new OutputAmount();
			item.setName(matcher.group(1));
			item.setProfession(matcher.group(2));
			item.setAmount(Integer.parseInt(matcher.group(3)));
			result.add(item);
		}
		return result;
	}


	/**
	 * @param string
	 * @return
	 */
	private List<Tank> parserTank(String string) {
		Pattern compile = Pattern.compile("^(\\S{1,})\\s+(\\d{1,3})\\s+(\\D{1,3})\\s+(\\d{1,3})\\s+(\\d{1,3})$", Pattern.MULTILINE);
		Matcher matcher = compile.matcher(string);
		List<Tank> result = new ArrayList<Tank>();
		Tank item = null;
		while(matcher.find()){
			item = new Tank();
			item.setName(matcher.group(1));
			item.setLevel(matcher.group(2));
			item.setProfession(matcher.group(3));
			item.setDrive(Integer.parseInt(matcher.group(4)));
			item.setKill(Integer.parseInt(matcher.group(5)));
			result.add(item);
		}
		return result;
	}

}
