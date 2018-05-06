<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>值得炫耀</title>

<link href="${pageContext.request.contextPath}/static/manageLogin/css/main.css" rel="stylesheet" type="text/css" />

</head>
<body>


<div class="login">
    <div class="box png">
		<div class="logo png"></div>
		<div class="input">
			<div class="log">
				<div class="name">
					<label>用户名</label><input type="text" class="text" id="name" placeholder="用户名" name="name" onfocus="clearCheck();" tabindex="1"></input>
				</div>
				<div class="pwd">
					<label>密　码</label><input type="password" class="text" id="password" placeholder="密码" name="password" onfocus="clearCheck();" tabindex="2"></input>
					<input type="button" class="submit" tabindex="3" value="登录" onclick="login();"></input>
					<div><span class="check" style="color:red;"></span></div>
				</div>
				<div class="tip"></div>
			</div>
		</div>
	</div>
    <div class="air-balloon ab-1 png"></div>
	<div class="air-balloon ab-2 png"></div>
    <div class="footer"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/manageLogin/js/jQuery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/manageLogin/js/fun.base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/manageLogin/js/script.js"></script>


<!--[if IE 6]>
<script src="${pageContext.request.contextPath}/static/manageLogin/js/DD_belatedPNG.js" type="text/javascript"></script>
<script>DD_belatedPNG.fix('.png')</script>
<![endif]-->
<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>

<script>
	function login(){
		var name = $.trim($("#name").val());
		var password = $.trim($("#password").val());
		var $check = $(".check");
		
		if (name.length === 0 || password.length === 0) {
			$check.text('用户名和密码不能为空！');
		} else if (name.length > 8 || password.length > 6) {
			$check.text('用户名或密码过长！');
		} else {
			$.post("/user/login",{username:name,password:password},function(data) {
				if (data.status === 200){
					// 登录成功
					window.location.href="/index";
				} else {
					// 登录失败
					$check.text(data.msg);
				}
			});
		}
	}
	
	function clearCheck(){
		$(".check").text("");
	}
</script>
</html>