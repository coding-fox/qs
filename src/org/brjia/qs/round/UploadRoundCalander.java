package org.brjia.qs.round;

import java.util.Calendar;
import java.util.Date;

public class UploadRoundCalander extends RoundCalander {
	public UploadRoundCalander(Date d){
		super(d);
	}

	@Override
	protected int calculateRound(Calendar calendar) {
		int round;
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
			if(hour==20){
				round=1;
			}else if(hour==21){
				if(min<45){
					round =1;
				}else{
					round =2;
				}
			}else if(hour>21 && hour<=23){
				round = 2;
			}else{
				throw new RuntimeException("不允许的小时范围");
			}
		}else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
			if(hour>=20 && hour<=23){
				round = 3;
			}else{
				throw new RuntimeException("不允许的小时范围");
			}
		}else{
			throw new RuntimeException("不允许的天范围");
		}
		return round;
	}

	
	
}
