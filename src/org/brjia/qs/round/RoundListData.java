package org.brjia.qs.round;

import java.util.List;
/**
 * 本场战斗列表数据
 * @author hujiawei
 *
 */
public class RoundListData {

	private List<MemberListData> primary;
	private List<MemberListData> enemy;
	public List<MemberListData> getPrimary() {
		return primary;
	}
	public void setPrimary(List<MemberListData> primary) {
		this.primary = primary;
	}
	public List<MemberListData> getEnemy() {
		return enemy;
	}
	public void setEnemy(List<MemberListData> enemy) {
		this.enemy = enemy;
	}
	
}
