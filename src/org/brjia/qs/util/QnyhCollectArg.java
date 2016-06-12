package org.brjia.qs.util;

import org.brjia.qs.parse.beans.Outline;
import org.brjia.qs.round.RoundCalander;

public class QnyhCollectArg {

	private String filename;
	private String content;
	private String mdStr;
	private Outline outline;
	private RoundCalander fightTime;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public RoundCalander getFightTime() {
		return fightTime;
	}
	public void setFightTime(RoundCalander fightTime) {
		this.fightTime = fightTime;
	}
	public Outline getOutline() {
		return outline;
	}
	public void setOutline(Outline outline) {
		this.outline = outline;
	}
	
	public String getMdStr() {
		return mdStr;
	}
	public void setMdStr(String mdStr) {
		this.mdStr = mdStr;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
}

