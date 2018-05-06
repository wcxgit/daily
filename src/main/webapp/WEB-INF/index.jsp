<%--
  Created by IntelliJ IDEA.
  User: wuchenxi
  Date: 2018/4/23
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>日报管理系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.5.4.5/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.5.4.5/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.5.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.5.4.5/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',border:false," style="height:60px;background:#B3DFDA;padding:10px">

</div>
<div data-options="region:'west',split:true,title:'菜单'" style="width:150px;padding:10px;">
    <ul id="tree" class="easyui-tree"
        style="margin-top: 10px; margin-left: 5px;">
        <li><span>日报管理</span>
            <ul>
                <li data-options="text:'日报查看',attributes:{'url':'${pageContext.request.contextPath}/view-daily'}">
                    日报查看
                </li> <li data-options="text:'日报填写',attributes:{'url':'${pageContext.request.contextPath}/add-daily'}">
                日报填写
            </li>
            </ul>
        </li>

        <%--<li data-options="state:'closed'"><span>学习库管理</span>--%>
        <%--<ul>--%>
        <%--<li data-options="text:'课程管理',attributes:{'url':'${pageContext.request.contextPath}/course-manage'}">--%>
        <%--课程管理--%>
        <%--</li>--%>
        <%--<li data-options="text:'单词库管理',attributes:{'url':'${pageContext.request.contextPath}/word-manage'}">--%>
        <%--单词库管理--%>
        <%--</li>--%>
        <%--<li data-options="text:'句子库管理',attributes:{'url':'${pageContext.request.contextPath}/sentence-manage'}">--%>
        <%--句子库管理--%>
        <%--</li>--%>
        <%--<li data-options="text:'课文库管理',attributes:{'url':'${pageContext.request.contextPath}/text-manage'}">--%>
        <%--课文库管理--%>
        <%--</li>--%>
        <%--<li data-options="text:'阅读文章库管理',attributes:{'url':'${pageContext.request.contextPath}/read-manage'}">--%>
        <%--阅读文章库管理--%>
        <%--</li>--%>
        <%--</ul>--%>
        <%--</li>--%>
    </ul>
</div>
<div data-options="region:'center',split:false"
     style="padding: 5px; background: #eee;">
    <div id="tabs" class="easyui-tabs" data-options="tools:'#tab-tools'">

    </div>
    <div id="tab-tools">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="margin-right:10px;" data-options="plain:true" onclick="logout()">注销</a>
    </div>
</div>
<%--<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>--%>
<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
<div data-options="region:'center',title:'Center'"></div>
</body>
<script type="text/javascript">

    // 注销
    function logout(){
        $.post("/user/logout",function(data){
            if (data.status === 200){
                location.href="/";
            }
        });
    }

    $(function () {
        $('#tree').tree({
            onClick: function (node) {
                if ($('#tree').tree("isLeaf", node.target)) {
                    var tabs = $("#tabs");
                    var tab = tabs.tabs("getTab", $.trim(node.text));
                    if (tab) {
                        tabs.tabs("select", $.trim(node.text));
                    } else {
                        tabs.tabs('add', {
                            title: $.trim(node.text),
                            href: node.attributes.url,
                            closable: true,
                            bodyCls: "content"
                        });
                    }
                }
            }
        });

        // 初始化首页内容
        $("#tabs").tabs('add', {
            title: '日报查看',
            href: '/view-daily'
        });
    });
</script>
</html>
