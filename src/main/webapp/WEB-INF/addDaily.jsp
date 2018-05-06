<%--
  添加日报
  User: wuchenxi
  Date: 2018/4/30
  Time: 12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="margin:20px 30px 20px 30px;">

    <table id="dg" class="easyui-datagrid" title="日报填写" style="width:100%;height:auto"
           data-options="
				iconCls: 'icon-edit',
				toolbar: '#tb',
				onClickCell: onClickCell,
				onEndEdit: onEndEdit,
				nowrap:false
			">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th width="10%" data-options="field:'matter',align:'center',editor:{
							type:'textbox',
							options:{
								required:true,
								multiline:true,
								height:50
							}
						}">事项
            </th>
            <th width="45%" data-options="align:'center',field:'content',multiline:true,
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
            <th width="25%" data-options="field:'remark',align:'center',editor:{type:'textbox',options:{multiline:true,height:50}}">备注</th>
        </tr>
        </thead>
    </table>

    <div id="tb" style="height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
           onclick="append()">新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:true"
           onclick="removeit()">移除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
           onclick="accept(0)">草稿</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
           onclick="accept(1)">保存</a>
    </div>
</div>
<script type="text/javascript">
    var editIndex = undefined;

    function endEditing() {
        if (editIndex == undefined) {
            return true
        }
        if ($('#dg').datagrid('validateRow', editIndex)) {
            $('#dg').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }

    function onClickCell(index, field) {
        if (editIndex != index) {
            if (endEditing()) {
                $('#dg').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                var ed = $('#dg').datagrid('getEditor', {index: index, field: field});
                if (ed) {
                    ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                }
                editIndex = index;
            } else {
                setTimeout(function () {
                    $('#dg').datagrid('selectRow', editIndex);
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
            $('#dg').datagrid('appendRow', {status: 'P'});
            editIndex = $('#dg').datagrid('getRows').length - 1;
            $('#dg').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
        }
    }

    function removeit() {
        if (editIndex == undefined) {
            return
        }
        $('#dg').datagrid('cancelEdit', editIndex)
            .datagrid('deleteRow', editIndex);
        editIndex = undefined;
    }

    function accept(flag) {
        if (endEditing()) {
            var rows = $('#dg').datagrid('getChanges');
            var effectRows = JSON.stringify(rows);
            if (rows) {
                if (flag === 1) {
                    // 保存
                    $.ajax({
                        url: "/daily/add-daily",
                        method: 'post',
                        data: {
                            param: effectRows,
                            flag: 1
                        },
                        success: function (data) {
                            $.messager.show({
                                title: '消息提示',
                                msg: data.msg,
                                timeout: 2000,
                                showType: 'slide'
                            });
                            if (data.status === 200) {
                                reject();
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
                            flag: 0
                        },
                        success: function (data) {
                            $.messager.show({
                                title: '消息提示',
                                msg: data.msg,
                                timeout: 2000,
                                showType: 'slide'
                            });
                            if (data.status === 200) {
                                reject();
                                $("#view-dg").datagrid('reload');
                            }
                        }

                    });
                }

            }
        }
    }

    function reject() {
        $('#dg').datagrid('rejectChanges');
        editIndex = undefined;
    }

    function getChanges() {
        var rows = $('#dg').datagrid('getChanges');
        alert(rows.length + ' rows are changed!');
    }
</script>
