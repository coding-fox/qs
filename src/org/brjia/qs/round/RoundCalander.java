package org.brjia.qs.round;

import java.util.Calendar;
import java.util.Date;
/**
 * 回合日历
 * @author hujiawei
 *
 */
public class RoundCalander {
	protected Calendar current = getInstance();
	protected int year;
	protected int week;
	protected int round;
	public RoundCalander(){
		init();
	}
	
	public RoundCalander(int year, int week, int round) {
		super();
		this.year = year;
		this.week = week;
		this.round = round;
	}

	public RoundCalander(Date d) {
		current.setTime(d);
		init();
	}

	public void setDate(Date d){
		current.setTime(d);
		init();
	}
	public Date getDate(){
		return current.getTime();
	}
	public String getDataBandStr(){
		Calendar start = getInstance();
		start.set(Calendar.YEAR, year);
		start.set(Calendar.WEEK_OF_YEAR, week);
		start.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		Date time = start.getTime();
		start.add(Calendar.DAY_OF_MONTH, 6);
		Date endtime = start.getTime();
		return String.format("%tm月%<td日－%2$tm月%<td日",time,endtime);
		
	}
	private Calendar getInstance() {
		Calendar ins = Calendar.getInstance();
		//联赛时间为周五，方便统计
		ins.setFirstDayOfWeek(Calendar.FRIDAY);
		//每年的第一周必需是一个完整周，否则记为上年的最后一周
		ins.setMinimalDaysInFirstWeek(7);
		return ins;
	}
	/**
	 * 计算
	 */
	private void init() {
		year = current.get(Calendar.YEAR);
		week =current.get(Calendar.WEEK_OF_YEAR);
		shiftYear();
		round=calculateRound(current);
	}
	@Override
	public String toString() {
		return "RoundCalander [year=" + year + ", week=" + week + ", round=" + round + "]";
	}

	/**
	 * 当每年的第一周不够7天，视为上一年的最后一周，此时week自动计算（约为50），相应的year需要减去一年
	 * 代码已经优化
	 */
	private void shiftYear() {
		if(week>40){
			if(current.get(Calendar.MONTH)==0){
				year = year-1;
			}
		}
	}
	/**
	 * 根据当前时间段计算当前场次
	 * 周五是新一期统计的开始，周五、周六之外均为第三场
	 * 周五21点45分之前第一场，之后为第二场
	 * 周六20点之前为第二场，之后为第三场
	 * @param calendar
	 * @return
	 */
	protected int calculateRound(Calendar calendar) {
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==Calendar.FRIDAY){
			int round;
			if(hour<21){
				round=1;
			}else if(hour==21){
				if(min<45){
					round =1;
				}else{
					round =2;
				}
			}else{
				round = 2;
			}
			return round;
		}else if(dayOfWeek==Calendar.SATURDAY){
			if(hour<=20){
				return 2;
			}
		}
		return 3;
		
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	/**
	 * 根据上传文件标题时间推测战斗持续时间
	 * @param calendar
	 * @param round
	 * @return
	 */
	public int getOffsetMinute() {
		int f_hour = 0,f_min = 0;
		if(round==1 || round ==3){
			f_hour = 20;
			
		}else{
			f_hour =21;
			f_min = 45;
		}
		Calendar clone = (Calendar) current.clone();
		clone.set(Calendar.HOUR_OF_DAY, f_hour);
		clone.set(Calendar.MINUTE,f_min);
		return (int) ((current.getTimeInMillis()-clone.getTimeInMillis())/60000);
	}
	public static void main(String[] args) {
		RoundCalander c = new RoundCalander();
		
		System.out.printf("year:%s,week:%s,round:%s  str:%s",c.getYear(),c.getWeek(),c.getRound(),c.getDataBandStr());
		System.out.println();
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, 2015);
		instance.set(Calendar.MONTH, 0);
		instance.set(Calendar.HOUR_OF_DAY, 22);
		instance.set(Calendar.DAY_OF_MONTH, 1);
		c.setDate(instance.getTime());
		System.out.printf("year:%s,week:%s,round:%s  str:%s",c.getYear(),c.getWeek(),c.getRound(),c.getDataBandStr());
		
		
	}
	
}
