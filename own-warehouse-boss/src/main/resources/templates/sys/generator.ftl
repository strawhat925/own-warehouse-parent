<!DOCTYPE html>
<html>
<head>
    <title>代码生成器</title>
    <#include "header.ftl"/>
</head>
<body>
<div id="rrapp">
    <div class="grid-btn">
        <div class="form-group col-sm-2">
            <input type="text" class="form-control" v-model="q.tableName" @keyup.enter="query"
                   placeholder="表名">
        </div>
        <a class="btn btn-default" @click="query">查询</a>
        <a class="btn btn-primary" @click="generator"><i
                class="fa fa-file-code-o"></i>&nbsp;生成代码</a>
    </div>
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div>

<script src="${request.contextPath}/js/sys/generator.js?_${.now}"></script>
</body>
</html>