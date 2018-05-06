<%--
  查看日报
  User: wuchenxi
  Date: 2018/4/30
  Time: 12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="margin:20px 30px 20px 30px;">
    <input type="hidden" id="user-role" value="${sessionScope.user.role}">

    <table id="view-dg" class="easyui-datagrid" title="日报预览" style="width:100%;height:auto"
           data-options="
                url:'/daily/getDailies',
                method:'get',
				iconCls: 'icon-edit',
				toolbar: '#view-tb',
				singleSelect: true,
				nowrap:false,
				pagination:true,
                pageList:[20,40,60],
                pageSize:20
			">
        <thead>
        <tr>

            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'id',hidden:true"></th>

            <th data-options="field:'opUser',align:'center'">日报填写人</th>

            <th width="10%" data-options="field:'matter',align:'center',editor:{
							type:'textbox',
							options:{
								required:true,
								multiline:true,
								height:50
							}
						}">事项
            </th>
            <th width="40%" data-options="align:'center',field:'content',multiline:true,
						editor:{
							type:'textbox',
							options:{
								required:true,
								multiline:true,
								height:50
							}
						}">完成目标详述
            </th>
            <th width="10%"
                data-options="field:'workDate',align:'center',editor:{type:'datebox',options:{required:true,editable:false,height:50}}">
                日报日期
            </th>
            <%--<th data-options="field:'unitcost',width:80,align:'right',editor:'textbox'">目标</th>--%>
            <th width="10%"
                data-options="field:'complete',align:'center',editor:{type:'numberbox',options:{min:0,max:100,value:0,required:true,height:50}}">
                完成量(%)
            </th>
            <th width="17%"
                data-options="field:'remark',align:'center',editor:{type:'textbox',options:{multiline:true,height:50}}">
                备注
            </th>
            <th width="6%"
                data-options="field:'state',align:'center',editor:{type:'textbox',options:{multiline:true,height:50}}">
                状态
            </th>

        </tr>
        </thead>
    </table>

    <div id="view-tb" style="height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:true"
           onclick="removeit()">移除</a>
        <c:if test="${sessionScope.user.role == 2}">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
               onclick="edit();">编辑</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
               onclick="accept(0)">草稿</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
               onclick="accept(1)">保存</a>
        </c:if>


        <div>
            <c:if test="${sessionScope.user.id == 1}">
                日报填写人：<select id="userId" name="userId" class="easyui-combobox" data-options="url:'/user/getUsers',method:'get',width:100,valueField:'id',editable:false,
                textField:'text'"></select>
            </c:if>
            日报日期：<input  id="workDate" name="workDate" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser,editable:false"/>
            <a href="#" onclick="searchForm();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    // 根据条件查询
    function searchForm(){
        var userId = 0;
        if ($("#user-role").val() === '1') {
            userId = $("#userId").combobox('getValue');
        }
        var workDate = $("#workDate").datebox('getValue');
        $("#view-dg").datagrid('load',{
            userId:userId,
            workDate:workDate
        });
    }

    // 格式化日期
    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }
    function myparser(s){
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d);
        } else {
            return new Date();
        }
    }

    var editIndex = undefined;

    function endEditing() {
        if (editIndex == undefined) {
            return true
        }
        if ($('#view-dg').datagrid('validateRow', editIndex)) {
            $('#view-dg').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }

    function edit() {
        var rows = $('#view-dg').datagrid('getSelections');
        if (rows.length === 0 || rows.length > 1) {
            $.messager.alert('提示', '只能选择一行数据进行编辑', 'info');
            return;
        }
        var row = rows[0];
        // 只有草稿状态下的数据才能够编辑
        if (row.state === '草稿') {
            var index = $('#view-dg').datagrid('getRowIndex', row);
            var field = 'state';
            onClickCell(index, field);
        } else {
            $.messager.alert('提示', '非草稿日报不能编辑!', 'info');
        }


    }

    function onClickCell(index, field) {
        if (editIndex != index) {
            if (endEditing()) {
                $('#view-dg').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                var ed = $('#view-dg').datagrid('getEditor', {index: index, field: field});
                if (ed) {
                    ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                }
                editIndex = index;
            } else {
                setTimeout(function () {
                    $('#view-dg').datagrid('selectRow', editIndex);
                }, 0);
            }
        }
    }

    function onEndEdit(index, row) {
        var ed = $(this).datagrid('getEditor', {
            index: index,
            field: 'matter'
        });
        row.productname = $(ed.target).combobox('getText');
    }

    function append() {
        if (endEditing()) {
            $('#view-dg').datagrid('appendRow', {status: 'P'});
            editIndex = $('#view-dg').datagrid('getRows').length - 1;
            $('#view-dg').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
        }
    }

    function removeit() {
        var row = $("#view-dg").datagrid('getSelected');
        editIndex = $("#view-dg").datagrid('getRowIndex', row);
        if (editIndex == undefined) {
            return;
        }
        $.messager.confirm('确认', '您确认想要删除记录吗？', function (r) {
            if (r) {
                $('#view-dg').datagrid('cancelEdit', editIndex)
                    .datagrid('deleteRow', editIndex);
                editIndex = undefined;
                // 删除当前日报信息
                $.post("/daily/deleteDaily", {id: row.id}, function (data) {
                    $.messager.show({
                        title: '消息提示',
                        msg: data.msg,
                        timeout: 2000,
                        showType: 'slide'
                    });
                });
            }
        });

    }

    function accept(flag) {
        var row = $('#view-dg').datagrid('getSelected');
        var rows = $('#view-dg').datagrid('getChanges');
        if (endEditing()) {
            if (rows.length > 0) {
                var effectRows = JSON.stringify(rows);
                if (rows) {
                    if (flag === 1) {
                        // 保存
                        $.ajax({
                            url: "/daily/add-daily",
                            method: 'post',
                            data: {
                                param: effectRows,
                                flag: 1,
                                id: row.id
                            },
                            success: function (data) {
                                $.messager.show({
                                    title: '消息提示',
                                    msg: data.msg,
                                    timeout: 2000,
                                    showType: 'slide'
                                });
                                if (data.status === 200) {
                                    $("#view-dg").datagrid('reload');
                                }
                            }

                        });
                    } else {
                        //草稿
                        $.ajax({
                            url: "/daily/add-daily",
                            method: 'post',
                            data: {
                                param: effectRows,
                                flag: 0,
                                id: row.id
                            },
                            success: function (data) {
                                $.messager.show({
                                    title: '消息提示',
                                    msg: data.msg,
                                    timeout: 2000,
                                    showType: 'slide'
                                });
                                if (data.status === 200) {
                                    $("#view-dg").datagrid('reload');
                                }
                            }

                        });
                    }

                }
            } else {
                if (flag === 1) {
                    // 保存
                    $.ajax({
                        url: "/daily/update-daily",
                        method: 'post',
                        data: {
                            flag: 1,
                            id: row.id
                        },
                        success: function (data) {
                            $.messager.show({
                                title: '消息提示',
                                msg: data.msg,
                                timeout: 2000,
                                showType: 'slide'
                            });
                            if (data.status === 200) {
                                $("#view-dg").datagrid('reload');
                            }
                        }

                    });
                }
            }

        }
    }

    function reject() {
        $('#view-dg').datagrid('rejectChanges');
        editIndex = undefined;
    }

    function getChanges() {
        var rows = $('#view-dg').datagrid('getChanges');
        alert(rows.length + ' rows are changed!');
    }
</script>
