<!DOCTYPE html>
<html>
<head>
	<title>角色管理</title>
	<#include "header.ftl">
</head>
<body>
<div id="rrapp" v-cloak>
	<div v-show="showList">
		<div class="grid-btn">
			<div class="form-group col-sm-2">
				<input type="text" class="form-control" v-model="q.roleName" @keyup.enter="query" placeholder="角色名称">
			</div>
			<a class="btn btn-default" @click="query">查询</a>
			
			<@shiro.hasPermission name="sys:role:save">
			<a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:role:update">
			<a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:role:delete">
			<a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
			</@shiro.hasPermission>
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
    <div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">角色名称</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="role.roleName" placeholder="角色名称"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">备注</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="role.remark" placeholder="备注"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">授权</div>
			   	<div class="col-sm-10">
			      <ul id="menuTree" class="ztree"></ul>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div> 
				<input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
				&nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
			</div>
		</form>
	</div>
</div>
   
<script src="${request.contextPath}/js/sys/role.js?_${.now}"></script>
</body>
</html>