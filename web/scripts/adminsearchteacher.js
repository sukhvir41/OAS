
$(document).ready(function(){

	$("#searchname").click(function(){
		searchByName();
	});

	$("#search").click(function(){
		search();
	});
});


var searchByName =  function(){
	var name = $("#teachername").val(); 
	var body = $("#tablebody");
	var template = '<tr><td>{{id}}</td><td><a href="/OAS/admin/teachers/detailteacher?teacherid={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td>';
	if (name.length>=1) {
		$.ajax({
			url: "/OAS/admin/ajax/teachers/searchteacherbyname",
			dataType: "json",
			data : {
				name : name
			},
			method : "post",
			success: function(data){
				body.empty();
				$.each(data,function(i,teacher){
					body.append(Mustache.render(template,teacher));
				});
			}
		});
	}
}


var search = function(){
	var departmentId = $("#department").val();
	var filter = $('input[name=filter]:checked').val();
	var body = $("#tablebody");
	var template = '<tr><td>{{id}}</td><td><a href="/OAS/admin/teachers/detailteacher?teacherid={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td>';
	$.ajax({
			url: "/OAS/admin/ajax/teachers/searchteacher",
			dataType: "json",
			data : {
				departmentId : departmentId,
				verified : filter
			},
			method : "post",
			success: function(data){
				body.empty();
				$.each(data,function(i,teacher){
					body.append(Mustache.render(template,teacher));
				});
			}
		});
}