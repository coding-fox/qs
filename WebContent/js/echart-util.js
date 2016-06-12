$(document).ready(
		function() {
//			//
//			$.getJSON( "/qs/home/members", function( res ) {
//				if(res)
//				  var items = [];
//				  $.each( res.data, function( key, val ) {
//				    items.push( "<li id='" + key + "'>" + val + "</li>" );
//				  });
//				 
//				  $( "<ul/>", {
//				    "class": "my-new-list",
//				    html: items.join( "" )
//				  }).appendTo( "body" );
//				});
			
			
			
			
			var vs = echarts.init(document.getElementById('chart-vs'), "roma");
			vs.setOption(vsdata.options);
			var hero = echarts.init(document.getElementById('chart-hero'),
					"roma");
			hero.setOption(hreooptions);
			var player = echarts.init(document.getElementById('chart-player'),
					"roma");
			player.setOption(playeroption);

			$("#player-intro input").click(function() {
				if ($('#player-intro input:first')[0].checked) {
					player.setOption(playeroption);
				} else {
					player.setOption(playeroption2);
				}
			});

		});

var fdata = {
	primary : "十里平湖霜满天",
	sendary : "战",
	duration : 50,
	round : 1,
	year : "2016",
	week : 32
};
var vsdata = {

	"options" : {
		title : {
			text : '十里平湖霜满天 vs 战',
			subtext : '各项指数对比一览'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			y : 'bottom',
			data : [ '十里平湖霜满天', '战' ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		polar : [ {
			indicator : [ {
				text : '输出',
				max : 100
			}, {
				text : '杀敌',
				max : 150
			}, {
				text : '助攻',
				max : 150
			}, {
				text : '暴尸',
				max : 300
			}, {
				text : '治疗',
				max : 1000000
			}, {
				text : '维修',
				max : 100
			}, {
				text : '战车杀敌',
				max : 300
			} ],
			radius : 130
		} ],
		series : [ {
			name : '完全实况球员数据',
			type : 'radar',
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : [ {
				value : [ 970000, 42, 88, 90, 860000, 69, 134 ],
				name : '十里平湖霜满天'
			}, {
				value : [ 370000, 32, 74, 88, 920000, 99, 45 ],
				name : '战'
			} ]
		} ]
	}
};

var hreooptions = {
	"title" : {
		"text" : "英雄在帮会内实力",
		"subtext" : "纯属虚构"
	},
	"tooltip" : {
		"trigger" : "axis"
	},
	"legend" : {
		"y" : 'bottom',
		"data" : [ "帮会实力", "英雄实力" ]
	},
	"toolbox" : {
		"show" : true,
		"feature" : {
			"mark" : {
				"show" : true
			},
			"dataView" : {
				"show" : true,
				"readOnly" : false
			},
			"magicType" : {
				"show" : true,
				"type" : [ "line", "bar", "stack", "tiled" ]
			},
			"restore" : {
				"show" : true
			},
			"saveAsImage" : {
				"show" : true
			}
		}
	},
	"calculable" : true,
	"xAxis" : [ {
		"type" : "category",
		"boundaryGap" : false,
		"data" : [ "输出", "杀敌", "助攻", "治疗", "承受攻击", "暴尸", "复活" ]
	} ],
	"yAxis" : [ {
		"type" : "value"
	} ],
	"series" : [ {
		"name" : "帮会实力",
		"type" : "line",
		"smooth" : true,
		"itemStyle" : {
			"normal" : {
				"areaStyle" : {
					"type" : "default"
				}
			}
		},
		"data" : [ 900, 212, 412, 540, 260, 430, 50 ]
	}, {
		"name" : "英雄实力",
		"type" : "line",
		"smooth" : true,
		"itemStyle" : {
			"normal" : {
				"areaStyle" : {
					"type" : "default"
				}
			}
		},
		"data" : [ 300, 182, 334, 191, 190, 300, 10 ]
	} ]
};

function random() {
	var r = Math.round(Math.random() * 100);
	return r;
}

function randomDataArray() {
	var d = [];
	var len = 100;
	while (len-- > 0) {
		d.push([ random(), random(), Math.abs(random()), Math.abs(random()) ]);
	}
	return d;
}
var playeroption2 = {
	title : {
		text : '辅助',
		subtext : ''
	},
	tooltip : {
		trigger : 'axis',
		showDelay : 100,
		formatter : function(params) {
			if (params.value.length > 1) {
				return params.seriesName + ' :<br/>' + '承受攻击' + params.value[0]
						+ ' :<br/>' + '治疗' + params.value[1] + ' :<br/>' + '助攻'
						+ params.value[2];
			} else {
				return params.seriesName + ' :<br/>' + '治疗' + params.name
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
		max : 10000,
		top : '10%',
		dimension : 2,
		calculable : true,
		inRange : { // 映射治疗量
			symbolSize : [ 15, 40 ]
		},
		outOfRange : {
			symbolSize : [ 15, 40 ],
			color : [ 'rgba(255,255,255,.4)' ]
		},
		controller : {
			inRange : {
				color : [ 'pink' ]
			},
			outOfRange : {
				color : [ 'rgba(0,0,0,0.1)' ]
			}
		}
	}, {
		type : 'piecewise',
		bottom : '10%',
		pieces : [ {
			min : 130
		}, {
			min : 110,
			max : 130
		}, {
			min : 90,
			max : 110
		}, {
			min : 70,
			max : 90
		}, {

			max : 70
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
				color : [ 'pink' ]
			},
			outOfRange : {
				color : [ 'rgba(0,0,0,0.1)' ]
			}
		}
	} ],
	legend : {
		data : [ '十里平湖霜满天', '战' ]
	},
	toolbox : {
		show : true,
	},
	xAxis : {
		scale : true,
		name : '承受攻击'
	},
	yAxis : {
		scale : true,
		name : '治疗'
	},
	series : [ {
		name : '十里平湖霜满天',
		type : 'scatter',
		data : randomDataArray(),
		markLine : {
			data : [ {
				type : 'average',
				valueIndex : 1,// 助攻维度
				name : '平均线'
			} ]
		}
	}, {
		name : '战',
		type : 'scatter',
		data : randomDataArray(),
		markLine : {
			data : {
				type : 'average',
				valueIndex : 1,// 助攻维度
				name : '平均线'
			}
		}
	} ]
};
var playeroption = {
	title : {
		text : '输出',
		subtext : ''
	},
	tooltip : {
		trigger : 'axis',
		showDelay : 100,
		formatter : function(params) {
			if (params.value.length > 1) {
				return params.seriesName + ' :<br/>' + '承受攻击' + params.value[0]
						+ ' :<br/>' + '输出' + params.value[1] + ' :<br/>' + '杀敌'
						+ params.value[2];
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
	legend : {
		data : [ '十里平湖霜满天', '战' ]
	},
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
				color : [ 'pink' ]
			},
			outOfRange : {
				color : [ 'rgba(0,0,0,0.1)' ]
			}
		}
	}, {
		type : 'piecewise',
		bottom : '10%',
		pieces : [ {
			min : 130
		}, {
			min : 110,
			max : 130
		}, {
			min : 90,
			max : 110
		}, {
			min : 70,
			max : 90
		}, {

			max : 70
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
				color : [ 'pink' ]
			},
			outOfRange : {
				color : [ 'rgba(0,0,0,0.1)' ]
			}
		}
	} ],
	toolbox : {
		show : true
	},
	xAxis : {
		scale : true,
		name : '承受攻击'
	},
	yAxis : {
		scale : true,
		name : '输出'
	},
	series : [ {
		name : '十里平湖霜满天',
		type : 'scatter',
		data : randomDataArray(),
		markLine : {
			data : {
				type : 'average',
				valueIndex : 1,// Y轴维度
				name : '平均线'
			}
		}
	}, {
		name : '战',
		type : 'scatter',
		data : randomDataArray(),
		markLine : {
			data : {
				type : 'average',
				valueIndex : 1,// Y轴维度
				name : '平均线'
			}
		}
	} ]
};
