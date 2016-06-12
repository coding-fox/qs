package org.brjia.qs.round;

/**
 * 玩家列表数据
 * 
 * @author hujiawei
 *
 */
public class MemberListData {

	private Integer summaryId;
	private String level;
	private Integer memberId;
	private String memberName;
	private Integer outputM;
	private Integer bearM;
	private Integer cureM;
	private Integer killQ;
	private Integer assistQ;
	private Integer carryQ;
	private Integer corpseQ;

	private Integer reliveQ;

	private Integer killedQ;

	private String hero;


	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}

	public Integer getCarryQ() {
		return carryQ;
	}

	public void setCarryQ(Integer carryQ) {
		this.carryQ = carryQ;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getOutputM() {
		return outputM;
	}

	public void setOutputM(Integer outputM) {
		this.outputM = outputM;
	}

	public Integer getBearM() {
		return bearM;
	}

	public void setBearM(Integer bearM) {
		this.bearM = bearM;
	}

	public Integer getCorpseQ() {
		return corpseQ;
	}

	public void setCorpseQ(Integer corpseQ) {
		this.corpseQ = corpseQ;
	}

	public Integer getReliveQ() {
		return reliveQ;
	}

	public void setReliveQ(Integer reliveQ) {
		this.reliveQ = reliveQ;
	}

	public Integer getKilledQ() {
		return killedQ;
	}

	public void setKilledQ(Integer killedQ) {
		this.killedQ = killedQ;
	}


	public String getHero() {
		return hero;
	}

	public void setHero(String hero) {
		this.hero = hero;
	}

	public Integer getCureM() {
		return cureM;
	}

	public void setCureM(Integer cureM) {
		this.cureM = cureM;
	}

	public Integer getKillQ() {
		return killQ;
	}

	public void setKillQ(Integer killQ) {
		this.killQ = killQ;
	}

	public Integer getAssistQ() {
		return assistQ;
	}

	public void setAssistQ(Integer assistQ) {
		this.assistQ = assistQ;
	}

}
