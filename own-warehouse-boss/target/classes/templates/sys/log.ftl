<!DOCTYPE html>
<html>
<head>
	<title>系统日志</title>
	<#include "header.ftl">
</head>
<body>
<div id="rrapp" v-cloak>
	<div>
		<div class="grid-btn">
			<div class="form-group col-sm-2">
				<input type="text" class="form-control" v-model="q.key" @keyup.enter="query" placeholder="用户名、用户操作">
			</div>
			<a class="btn btn-default" @click="query">查询</a>
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
</div>

<script src="${request.contextPath}/js/sys/log.js?_${.now}"></script>
</body>
</html>