'use strict';
var socket;
var studentId;
var mark;
var lectureId ;
var error;
var success;
var button;
var presentTable;
var absentTable;

$(document).ready(function(){
	error = $("#error");
	success = $("#success");
	lectureId = $("#lectureId").val();
	presentTable = $("#present");
	absentTable = $("#absent");
	if(lectureId.length > 0){
		socket = new WebSocket("ws:///OAS/teacher/websocket");
		$(".action").click(function(){
			button = $(this);
			sendMessage($(this));
		});
	}
});
socket.onopen = function (){
	openMessage();
}

socket.onerror = function(){	
	error.show();
}

socket.onmessage = onMessage;

var onMessage = function(event){
	if (event =="true") {
		var row = button.closest("tr");
		var rowtemp = button.closest("tr");

	}else if (event =="false") {
		error.show();
	}else{

	}

}

var sendMessage = function(data){
	studentId = data.val();

	if(data.text() ==="Absent"){
		mark = "absent";
		var message ={
			lectureId : lectureId,
			studentId : studentId,
			mark : false,
			markedByTeacher : true
		};
		socket.send(JSON.stringify(message));
	}else if(data.text() ==="Present"){
		var message ={
			lectureId : lectureId,
			studentId : studentId,
			mark : true,
			markedByTeacher : true
		};
		socket.send(JSON.stringify(message));
	}else{
		error.show();
	}
}

var openMessage = function(){
	socket.send(lectureId);
}