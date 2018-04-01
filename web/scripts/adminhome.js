'use strict';

var memChartCtx;
var cpuChartCtx;
var memChart;
var cpuChart;
var labels = [];
var memData = [];
var cpuData = [];


$(document).ready(function(){

	memChartCtx = $("#memChart");
	cpuChartCtx = $("#cpuChart");

	drawChart();
	setInterval(getData, 10000);

});


var drawChart = function(){

	memChart = new Chart(memChartCtx, {
		type: 'line',
		data: {
			labels: labels,
			datasets : [{label : "Memory", data : memData, lineTension : 0.5}]
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						suggestedMin: 0,
						suggestedMax: 100
					}
				}]
			}
		}
	});

	cpuChart = new Chart(cpuChartCtx, {
		type: 'line',
		data: {
			labels: labels,
			datasets : [{label : "CPU", data : cpuData, lineTension : 0.5}]
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						suggestedMin: 0,
						suggestedMax: 100
					}
				}]
			}
		}
	});	
}


var addData = function(mem,cpu){

	for (var i = 0; i < labels.length; i++) {
		labels[i] = labels[i]-10;
	}

	labels.push(0);
	memData.push(mem);
	cpuData.push(cpu);

	if (labels.length == 20) {
		labels.shift(1);
		memData.shift(1);
		cpuData.shift(1);
	}

	memChart.update();
	cpuChart.update();

}

var getData = function(){

	$.ajax({
			url: "/OAS/admin/ajax/systeminfo",
			dataType: "json",
			method: "post",
			success: function (sysdata) {
				addData(sysdata.memoryUsedPerc,sysdata.cpuUsedPerc);
				
			},
			error: function (){
				
			}
		});
}