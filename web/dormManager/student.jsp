<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

$(document).ready(function(){
	$("ul li:eq(1)").addClass("active");
	$('.datatable').dataTable( {        				
		 "oLanguage": {
				"sUrl": "/DormManage/media/zh_CN.json"
		 },
		"bLengthChange": false, //改变每页显示数据数量
		"bFilter": false, //过滤功能
		"aoColumns": [
			null,
			null,
			null,
			{ "asSorting": [ ] },
			null,
			{ "asSorting": [ ] },
		]
	});
});

window.onload = function(){ 
	$("#DataTables_Table_0_wrapper .row-fluid").remove();
};
</script>
<style type="text/css">
	.span6 {
		width:0px;
		height: 0px;
		padding-top: 0px;
		padding-bottom: 0px;
		margin-top: 0px;
		margin-bottom: 0px;
	}

</style>
<div class="data_list">
		<div class="data_list_title">
			Управление студентами
		</div>
		<form name="myForm" class="form-search" method="post" action="student?action=search" style="padding-bottom: 0px">
				<span class="data_search">
					<span><font style="font-family: '黑体'; font-style: 'bold'; font-size: 20px" color="blue">${dormBuildName }&nbsp;&nbsp;</font></span>
					<select id="searchType" name="searchType" style="width: 80px;">
					<option value="name">name</option>
					<option value="number" ${searchType eq "number"?'selected':'' }>stunum</option>
					<option value="dorm" ${searchType eq "dorm"?'selected':'' }>dormitories</option>
					</select>
					&nbsp;<input id="s_studentText" name="s_studentText" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_studentText }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">search</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<thead>
					<tr>
					<th>stunum</th>
					<th>name</th>
					<th>sex</th>
					<th>dormitory building</th>
					<th>dormitories</th>
					<th>phone number</th>
					<th>operation</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach  varStatus="i" var="student" items="${studentList }">
					<tr>
						<%-- <td>${i.count+(page-1)*pageSize }</td> --%>
						<td>${student.userName }</td>
						<td>${student.name }</td>
						<td>${student.sex }</td>
						<td>${student.dormBuildName==null?"无":student.dormBuildName }</td>
						<td>${student.dormName }</td>
						<td>${student.tel }</td>
						<td>
							<button class="btn btn-mini btn-success" type="button" onclick="javascript:window.location='record?action=preSave&studentNumber=${student.userName }'">Add absence records</button>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
</div>