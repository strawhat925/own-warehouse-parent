<!DOCTYPE html>
<html>
<head>
    <title>系统用户</title>
<#include "header.ftl"/>
</head>
<body>
<div id="rrapp">
    <div v-show="showList">
        <div class="grid-btn">
            <div class="form-group col-sm-2">
                <input type="text" class="form-control" v-model="q.username" @keyup.enter="query"
                       placeholder="用户名">
            </div>
            <a class="btn btn-default" @click="query">查询</a>
            <a class="btn btn-primary" @click="add"><i
                    class="fa fa-plus"></i>&nbsp;新增</a>
            <a class="btn btn-primary" @click="update"><i
                    class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
            <a class="btn btn-primary" @click="del"><i
                    class="fa fa-trash-o"></i>&nbsp;删除</a>
        </div>
        <table id="jqGrid"></table>
        <div id="jqGridPager"></div>
    </div>
    <div v-show="!showList" class="panel panel-default">
        <div class="panel-heading">{{title}}</div>
        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2 control-label">用户名</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.username" placeholder="用户名"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">密码</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.password" placeholder="密码"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">邮箱</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.email" placeholder="邮箱"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">手机号</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.mobile" placeholder="手机号"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">角色</div>
                <div class="col-sm-10" v-for="item in roleList">
                    <label class="checkbox-inline">
                        <input type="checkbox" v-model="user.roleIdList" v-bind:value="item.roleId">{{item.roleName}}
                    </label>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">状态</div>
                <label class="radio-inline">
                    <input type="radio" v-model="user.status" value="0">禁用
                </label>
                <label class="radio-inline">
                    <input type="radio" v-model="user.status" value="1">正常
                </label>
            </div>

            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
                &nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/js/sys/user.js?_${.now}"></script>
</body>
</html>