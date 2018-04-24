'use strict';

var chartCtx;
var resourceChart;
var labels = [];
var memData = [];
var cpuData = [];
var chartError;


$(document).ready(function(){

	chartCtx = $("#chart");
	chartError = $("#chartError");
	drawChart();
	setInterval(getData, 10000);

});


var drawChart = function(){

	resourceChart = new Chart(chartCtx, {
		type: 'line',
		data: {
			labels: labels,
			datasets : [{label : "Memory", data : memData, lineTension : 0,borderColor: '#b34ad6',backgroundColor: '#b34ad6',fill: false},
			{label : "CPU", data : cpuData, lineTension : 0,borderColor: '#14c4ff',backgroundColor: '#14c4ff', fill:false}]
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
		labels.shift();
		memData.shift();
		cpuData.shift();
	}

	resourceChart.update();
}

var getData = function(){

	$.ajax({
		url: "/OAS/admin/ajax/systeminfo",
		dataType: "json",
		method: "post",
		success: function (sysdata) {
			addData(sysdata.memoryUsedPerc,sysdata.cpuUsedPerc);
			chartError.hide();
		},
		error: function (){
			chartError.show();
		}
	});
}