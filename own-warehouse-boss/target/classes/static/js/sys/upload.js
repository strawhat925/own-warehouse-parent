$(function () {
    $("#jqGrid").jqGrid({
        url: '/sys/file/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 20, key: true },
            { label: 'URL地址', name: 'url', width: 160 },
			{ label: '创建时间', name: 'createDate', width: 40 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "data.list",
            page: "data.pageNum",
            total: "data.pages",
            records: "data.total"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

    new AjaxUpload('#upload', {
        action: '/sys/file/upload',
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if(vm.config.type == null){
                alert("云存储配置未配置");
                return false;
            }
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 200){
                alert(r.data);
                vm.reload();
            }else{
                alert(r.data);
            }
        }
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
        config: {

        }
	},
    created: function(){
        this.getConfig();
    },
	methods: {
		query: function () {
			vm.reload();
		},
		getConfig: function () {
            $.getJSON("/sys/file/config", function(r){
				vm.config = r.data;
            });
        },
		addConfig: function(){
			vm.showList = false;
			vm.title = "云存储配置";
			vm.config.type = 1;
		},
		saveOrUpdate: function () {
			var url = "/sys/file/save";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.config),
			    success: function(r){
			    	if(r.code === 200){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.data);
					}
				}
			});
		},
        del: function () {
            var ossIds = getSelectedRows();
            if(ossIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "/sys/file/delete",
                    data: JSON.stringify(ossIds),
                    success: function(r){
                        if(r.code === 200){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.data);
                        }
                    }
                });
            });
        },
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});