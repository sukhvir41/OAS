'use strict';

var chartCtx;
var resourceChart;
var labels = [];
var memData = [];
var cpuData = [];
var chartError;
var wsSocket;
var adminCount;
var teacherCount;
var studentCount;


$(document).ready(function () {

	chartCtx = $("#chart");
	chartError = $("#chartError");
	adminCount = $("#adminCount")
	studentCount = $("#studentCount");
	teacherCount = $("#teacherCount");
	chartError.show();
	wsSocket = new WebSocket(getURL());
	wsSocket.onopen = wsOnOpen();
	wsSocket.onmessage = function (event) { wsOnMessage(event) };
	wsSocket.onerror = function () { chartError.show() };
	drawChart();


});

$(window).unload(function () {
	if (wsSocket !== null) {
		wsSocket.close();
	}
});



var wsOnMessage = function (event) {
	try {
		var rawData = JSON.parse(event.data);
		if (rawData.type === "data") {
			var sysdata = rawData.message;
			var memoryPercentage = (1 - (sysdata.memoryAvailable / sysdata.memoryTotal)) * 100;
			addData(memoryPercentage, sysdata.cpuUsedPercentage);
			adminCount.text(sysdata.Admin);
			studentCount.text(sysdata.Student);
			teacherCount.text(sysdata.Teacher);
		} else if (rawData.type === "error") {
			chartError.show();
		}
	} catch (err) {
		console.log(err);
		chartError.show();
	}


}

var wsOnOpen = function () {
	chartError.hide();
}





var getURL = function () {
	var loc = window.location, new_uri;
	if (loc.protocol === "https:") {
		new_uri = "wss:";
	} else {
		new_uri = "ws:";
	}
	new_uri += "//" + loc.host;
	new_uri += loc.pathname + "/ws/systemInfo";
	return new_uri;
}

var drawChart = function () {

	resourceChart = new Chart(chartCtx, {
		type: 'line',
		data: {
			labels: labels,
			datasets: [
				{label: "Memory", 
				data: memData, 
				lineTension: 0, 
				borderColor: '#b34ad6', 
				backgroundColor: '#b34ad6', 
				fill: false },
				{label: "CPU", 
				data: cpuData, 
				lineTension: 0, 
				borderColor: '#14c4ff', 
				backgroundColor: '#14c4ff', 
				fill: false }
			]

		},
		options: {
			scales: {
				yAxes: [{
					position: 'left',
					ticks: {
						suggestedMin: 0,
						suggestedMax: 100
					},
					scaleLabel : {
						display: true,
						labelString : 'Useage in %'
					}
				},{
					position: 'right',
					ticks: {
						suggestedMin: 0,
						suggestedMax: 100
					},
					scaleLabel : {
						display: true,
						labelString : 'Useage in %'
					}
				}],
				xAxes: [{
					scaleLabel : {
						display: true,
						labelString :"Time scale"
					}
				}]
			},
		}
	});

}


var addData = function (mem, cpu) {

	for (var i = 0; i < labels.length; i++) {
		labels[i] = labels[i] - 15;
	}

	labels.push(0);
	memData.push(mem);
	cpuData.push(cpu);

	if (labels.length === 21) {
		labels.shift();
		memData.shift();
		cpuData.shift();
	}

	resourceChart.update();
}

var getData = function () {

	$.ajax({
		url: "/OAS/admin/ajax/systeminfo",
		dataType: "json",
		method: "post",
		success: function (sysdata) {
			addData(sysdata.memoryUsedPerc, sysdata.cpuUsedPerc);
			chartError.hide();
		},
		error: function () {
			chartError.show();
		}
	});
}


