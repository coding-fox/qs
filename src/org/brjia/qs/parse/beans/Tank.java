/**
 * 
 */
package org.brjia.qs.parse.beans;

/**
 * 战车
 * @author Administrator
 * 2015年9月20日
 */
public class Tank {

	private String name;//名称
	private String level;//等级
	private String profession;//职业
	private Integer drive;
	private Integer kill;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the profession
	 */
	public String getProfession() {
		return profession;
	}
	/**
	 * @param profession the profession to set
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}
	/**
	 * @return the drive
	 */
	public Integer getDrive() {
		return drive;
	}
	/**
	 * @param drive the drive to set
	 */
	public void setDrive(Integer drive) {
		this.drive = drive;
	}
	/**
	 * @return the kill
	 */
	public Integer getKill() {
		return kill;
	}
	/**
	 * @param kill the kill to set
	 */
	public void setKill(Integer kill) {
		this.kill = kill;
	}
	
	
}
