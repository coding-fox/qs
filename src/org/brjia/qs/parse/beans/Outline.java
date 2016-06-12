/**
 * 
 */
package org.brjia.qs.parse.beans;

import java.util.List;

/**
 * 联赛详情
 * @author Administrator
 * 2015年9月20日
 */
public class Outline {

	/**
	 * 帮会名称
	 */
	private String gang;
	/**
	 * 辅助
	 */
	private List<Auxiliary> assist;
	/**
	 * 综合
	 */
	private List<Comprehensive> main;
	/**
	 * 输出
	 */
	private List<OutputAmount> out;
	/**
	 * 承认攻击
	 */
	private List<OutputAmount> bear;
	/**
	 * 治疗
	 */
	private List<OutputAmount> cure;
	/**
	 * 战车
	 */
	private List<Tank> tank;
	/**
	 * @return the gang
	 */
	public String getGang() {
		return gang;
	}
	/**
	 * @param gang the gang to set
	 */
	public void setGang(String gang) {
		this.gang = gang;
	}
	/**
	 * @return the assist
	 */
	public List<Auxiliary> getAssist() {
		return assist;
	}
	/**
	 * @param assist the assist to set
	 */
	public void setAssist(List<Auxiliary> assist) {
		this.assist = assist;
	}
	/**
	 * @return the main
	 */
	public List<Comprehensive> getMain() {
		return main;
	}
	/**
	 * @param main the main to set
	 */
	public void setMain(List<Comprehensive> main) {
		this.main = main;
	}
	/**
	 * @return the out
	 */
	public List<OutputAmount> getOut() {
		return out;
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(List<OutputAmount> out) {
		this.out = out;
	}
	/**
	 * @return the bear
	 */
	public List<OutputAmount> getBear() {
		return bear;
	}
	/**
	 * @param bear the bear to set
	 */
	public void setBear(List<OutputAmount> bear) {
		this.bear = bear;
	}
	/**
	 * @return the cure
	 */
	public List<OutputAmount> getCure() {
		return cure;
	}
	/**
	 * @param cure the cure to set
	 */
	public void setCure(List<OutputAmount> cure) {
		this.cure = cure;
	}
	/**
	 * @return the tank
	 */
	public List<Tank> getTank() {
		return tank;
	}
	/**
	 * @param tank the tank to set
	 */
	public void setTank(List<Tank> tank) {
		this.tank = tank;
	}
	
	
}
