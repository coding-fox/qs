/**
 * 成员辅助数据处理函数
 * @param list
 * @returns {___anonymous520_613}
 */
function memberAssistDataArray(list) {
	var bearM = 0, cureM = 0, assistQ = 0, max_bearM = 0, max_cureM = 0, max_assistQ = 0;
	var d = [];
	var len = list.length;
	while (--len >= 0) {
		if ((bearM = list[len].bearM) > max_bearM) {
			max_bearM = bearM;
		}
		if ((cureM = list[len].cureM) > max_cureM) {
			max_cureM = cureM;
		}
		if ((assistQ = list[len].assistQ) > max_assistQ) {
			max_assistQ = assistQ;
		}
		d
				.push([ bearM, cureM, assistQ, list[len].level,
						list[len].memberName ]);
	}
	return {
		data : d,
		max_bearM : max_bearM,
		max_cureM : max_cureM,
		max_assistQ : max_assistQ
	};
}
/**
 * 成员攻击数据整理函数，返回附加最大承受，最大输出和最大杀敌数
 * @param list
 * @returns {___anonymous1124_1217}
 */
function memberDataArray(list) {
	var bearM = 0, outputM = 0, killQ = 0, max_bearM = 0, max_outputM = 0, max_killQ = 0;
	var d = [];
	var len = list.length;
	while (--len >= 0) {
		if ((bearM = list[len].bearM) > max_bearM) {
			max_bearM = bearM;
		}
		if ((outputM = list[len].outputM) > max_outputM) {
			max_outputM = outputM;
		}
		if ((killQ = list[len].killQ) > max_killQ) {
			max_killQ = killQ;
		}
		d
				.push([ bearM, outputM, killQ, list[len].level,
						list[len].memberName ]);
	}
	return {
		data : d,
		max_bearM : max_bearM,
		max_outputM : max_outputM,
		max_killQ : max_killQ
	};
}
/**
 * 后勤汇总指标
 * @param obj
 * @returns {Array}
 */
function summaryBackToArray(obj) {
	return [ obj.carryQ, obj.tankKillQ, obj.count, obj.count - obj.deadCount ]
}
/**
 * 战斗汇总指标
 * @param obj
 * @returns {Array}
 */
function summaryToArray(obj) {
	return [ obj.outputM, obj.killQ, obj.assistQ, obj.corpseQ, obj.cureM,
			obj.reliveQ ]
}
/**
 * 英雄比率计算函数
 * @param h
 * @param a
 * @returns {Array}
 */
function heroToArray(h, a) {
	var res = [];
	res.push(Math.round(h.outputM / a.outputM * 100));
	res.push(Math.round(h.killQ / a.killQ * 100));
	res.push(Math.round(h.assistQ / a.assistQ * 100));
	res.push(Math.round(h.bearM / a.bearM * 100));
	res.push(Math.round(h.cureM / a.cureM * 100));
	res.push(Math.round(h.reliveQ / a.reliveQ * 100));
	return res;
}
/**
 * 将数组各项值增加一定比率的整数
 */
function accumlateInt(arr, ratio) {
	if (!arr) {
		return arr;
	}
	for (var i = 0; i < arr.length; i++) {
		arr[i] = Math.round(arr[i] * (1 + ratio));
	}
	return arr;
}

/**
 * 返回两个数组的最大值数组
 */
function maxInArrays(arr1, arr2) {
	if (!arr1) {
		return arr2;
	}
	if (!arr2) {
		return arr1;
	}
	var length = Math.min(arr1.length, arr2.length);
	var res = [];
	for (var i = 0; i < length; i++) {
		res[i] = Math.max(arr1[i], arr2[i]);
	}
	return res;
}
var vs_options = {
	title : {
		subtext : '各项指数对比一览'
	},
	tooltip : {
		trigger : 'item'
	},
	legend : {
		y : 'bottom'
	},
	toolbox : {
		show : true,
		feature : {
			dataZoom : {},
			dataView : {
				readOnly : false
			},
			restore : {},
			saveAsImage : {}
		}
	},
	radar : [ {
		indicator : [ {
			name : '输出'
		}, {
			name : '杀敌'
		}, {
			name : '助攻'
		}, {
			name : '暴尸'
		}, {
			name : '治疗'
		}, {
			name : '救人'
		} ],
		center : [ '30%', '50%' ],
		radius : '65%'
	}, {
		indicator : [ {
			text : '维修'
		}, {
			text : '战车杀敌'
		}, {
			text : '参战人数'
		}, {
			text : '活跃人数'
		} ],
		radius : '40%',
		center : [ '80%', '50%' ],
	} ],
	series : [ {
		name : '正面对战',
		type : 'radar',
		itemStyle : {
			normal : {
				areaStyle : {
					type : 'default'
				}
			}
		}
	}, {
		name : '后勤',
		radarIndex : 1,
		type : 'radar',
		itemStyle : {
			normal : {
				areaStyle : {
					type : 'default'
				}
			}
		}
	} ]
};
var hreo_options = {
	title : {
		text : "英雄实力",
		subtext : "英雄与全体的比率"
	},
	tooltip : {
		trigger : "axis",
		formatter : function(params) {
			if (params.length > 1) {
				return params[0].seriesName + ": " + params[0].value + "%<br/>"
						+ params[1].seriesName + ": " + params[1].value + "%";
			} else {
				return params[0].seriesName + ": " + params[0].value + "%"
			}
		}
	},
	legend : {
		y : 'bottom'
	},
	toolbox : {
		show : true,
		feature : {
			dataZoom : {},
			dataView : {
				readOnly : false
			},
			magicType : {
				type : [ 'line', 'bar' ]
			},
			restore : {},
			saveAsImage : {}
		}
	},
	xAxis : {
		type : "category",
		boundaryGap : false,
		data : [ "输出", "杀敌", "助攻", "承受攻击", "治疗", "救人" ]
	},
	yAxis : {
		type : "value",
		max : 100,
		axisLabel : {
			formatter : '{value} %'
		}
	},
	series : [ {
		name : "帮会实力",
		type : "line",
		smooth : true,
		itemStyle : {
			normal : {
				areaStyle : {
					type : "default"
				}
			}
		}
	},{
		name : "帮会实力2",
		type : "line",
		smooth : true,
		itemStyle : {
			normal : {
				areaStyle : {
					type : "default"
				}
			}
		}
	}]
};
var output_options = {
	title : {
		text : '进攻能力',
		subtext : ''
	},
	tooltip : {
		trigger : 'axis',
		showDelay : 100,
		formatter : function(params) {
			if (params.value.length > 1) {
				return params.value[4] + ' <br/>' + '杀敌:' + params.value[2]
						+ ' <br/>' + '承受:' + params.value[0] + ' 万<br/>'
						+ '输出:' + params.value[1] + ' 万';
			} else {
				return params.seriesName + ' :<br/>' + '输出' + params.name
						+ ' : ' + params.value;
			}
		},
		axisPointer : {
			show : true,
			type : 'cross',
			lineStyle : {
				type : 'dashed',
				width : 1
			}
		}
	},
	legend : {},
	visualMap : [ {
		min : 0,
		max : 100,
		top : '10%',
		dimension : 2,
		calculable : true,
		inRange : { // 映射杀敌
			symbolSize : [ 15, 40 ]
		},
		outOfRange : {
			symbolSize : [ 15, 40 ],
			color : [ 'rgba(255,255,255,.4)' ]
		},
		controller : {
			inRange : {
				color : [ '#b2dfdb' ]
			},
			outOfRange : {
				color : [ 'rgba(0,0,0,0.1)' ]
			}
		}
	}, {
		type : 'piecewise',
		bottom : '10%',
		itemSymbol : 'diamond',
		pieces : [ {
			min : 130,
			label : '130+'
		}, {
			min : 110,
			max : 130,
			label : '129级'
		}, {
			min : 90,
			max : 110,
			label : '109级'
		}, {
			min : 70,
			max : 90,
			label : '89级'
		}, {
			max : 70,
			label : '69级'
		} ],
		dimension : 3,// 映射等级
		inRange : {
			colorLightness : [ .7, 0.3 ]
		},
		outOfRange : {
			color : [ 'rgba(0,0,0,0.1)' ]
		},
		controller : {
			inRange : {
				color : [ '#b2dfdb' ]
			},
			outOfRange : {
				color : [ 'rgba(0,0,0,0.1)' ]
			}
		}
	} ],
	toolbox : {
		show : true,
		feature : {
			dataZoom : {},
			dataView : {
				readOnly : true
			},
			restore : {},
			saveAsImage : {}
		}
	},
	xAxis : {
		name : '承受攻击 (万)',
		nameLocation : 'middle'
	},
	yAxis : {
		name : '输出 (万)',
		position : 'right',
		axisTick : {
			show : false
		}
	},
	series : [ {
		type : 'scatter',
		markLine : {
			data : [ {
				type : 'average',
				valueIndex : 1,// Y轴维度
				name : '平均线'
			} ]
		}
	}, {
		type : 'scatter',
		markLine : {
			data : [ {
				type : 'average',
				valueIndex : 1,// Y轴维度
				name : '平均线'
			} ]
		}
	} ]
};
var assist_options = {
	title : {
		text : '辅助能力',
		subtext : ''
	},
	tooltip : {
		trigger : 'axis',
		showDelay : 100,
		formatter : function(params) {
			if (params.value.length > 1) {
				return params.value[4] + ' <br/>' + '助攻:' + params.value[2]
						+ ' <br/>' + '治疗:' + params.value[1] + ' 万<br/>'
						+ '承受:' + params.value[0] + ' 万';
			} else {
				return params.seriesName + ' <br/>' + '治疗' + params.name
						+ ' : ' + params.value;
			}
		},
		axisPointer : {
			show : true,
			type : 'cross',
			lineStyle : {
				type : 'dashed',
				width : 1
			}
		}
	},
	visualMap : [ {
		min : 0,
		top : '10%',
		type : 'continuous',
		dimension : 1,
		calculable : true,
		inRange : { // 映射治疗量
			symbolSize : [ 15, 45 ]
		},
		outOfRange : {
			symbolSize : [ 15, 45 ],
			color : [ 'rgba(255,255,255,.4)' ]
		},
		controller : {
			inRange : {
				color : [ '#b2dfdb' ]
			},
			outOfRange : {
				color : [ 'rgba(0,0,0,0.1)' ]
			}
		}
	}, {
		type : 'piecewise',
		bottom : '10%',
		pieces : [ {
			min : 130,
			label : '130+'
		}, {
			min : 110,
			max : 130,
			label : '129级'
		}, {
			min : 90,
			max : 110,
			label : '109级'
		}, {
			min : 70,
			max : 90,
			label : '89级'
		}, {
			max : 70,
			label : '69级'
		} ],
		dimension : 3,// 映射等级
		inRange : {
			colorLightness : [ .7, 0.3 ]
		},
		outOfRange : {
			color : [ 'rgba(0,0,0,0.1)' ]
		},
		controller : {
			inRange : {
				color : [ '#b2dfdb' ]
			},
			outOfRange : {
				color : [ 'rgba(0,0,0,0.1)' ]
			}
		}
	} ],
	legend : {},
	toolbox : {
		show : true,
		feature : {
			dataZoom : {},
			dataView : {
				readOnly : true
			},
			restore : {},
			saveAsImage : {}
		}
	},
	xAxis : {
		nameLocation : 'middle',
		name : '承受攻击 (万)'
	},
	yAxis : {
		name : '治疗 (万)',
		axisTick : {
			show : false
		},
		position : 'right'
	},
	series : [ {
		type : 'scatter',
		markLine : {
			data : [ {
				type : 'average',
				valueIndex : 1,// 助攻维度
				name : '平均线'
			} ]
		}
	}, {
		type : 'scatter',
		markLine : {
			data : [ {
				type : 'average',
				valueIndex : 1,// 助攻维度
				name : '平均线'
			} ]
		}
	} ]
};
var upload_option = {
	    title: {
	        text: '帮会数据上传情况',
	        subtext: ''
	    },
	    tooltip: {
	        trigger: 'axis',
	        formatter:function(p){
	        	if(!p[0].data){
	        		return null;
	        	}
	            return "战斗期次: "+p[0].data.ywr+"</br>"+
	            "时间: "+p[0].data.fightTime+"</br>"+
	            "敌对帮会: "+p[0].data.eid+"</br>"+
	            "上传者: "+p[0].data.uploadUserName+"</br>"+
	            "声势值: "+p[0].data.impetus+"</br>";
	        }
	    },
	    legend: {
	        data:['数据上传']
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            dataZoom: {show:false},
	            dataView: {readOnly: false},
	            magicType: {type: ['line', 'bar']},
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    xAxis:  {
	        type: 'category',
	        boundaryGap: true,
	        data: ['2016-17-1','2016-17-2','2016-17-3','周四','周五','周六','周日']
	    },
	    yAxis: {
	        type: 'value',
	        name:"声势值",
	        top:50
	    },
	    series: [
	        {
	            name:'数据上传',
	            type:'line',
	            data:[{value:11,uploadMan:'野渡',uploadTime:new Date}, 11, 15, 13, 12, 13, 10],
	            markPoint: {
	                data: [
	                    {
	                        coord:["2016-17-2",11]
	                    }
	                ]
	            }
	        }
	    ]
	};