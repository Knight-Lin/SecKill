<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="common/tag.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="common/head.jsp"%>
<title>秒杀详情</title>
</head>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>${seckill.name}</h2>
			</div>
			<div class="panel-body text-center">
				<h2 class="text-danger">
					<span class="glyphicon glyphicon-time"></span> <span
						class="glyphicon" id="seckill-box"></span>
				</h2>
			</div>
		</div>
	</div>
	<!-- 登陆弹出层，输入电话 -->
	<div id="killPhoneModal" class="modal false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>秒杀电话:
					</h3>
				</div>


				<div class="modal-body">

					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killPhone" id="killPhoneKey"
								placeholder="填写手机号码" class="form-control">
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<!-- 验证信息 -->
					<span id="killPhoneMessage" class="glyphicon"></span>
					<button type="button" id="killPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone"></span> 提交
					</button>
				</div>
			</div>
		</div>
	</div>
</body>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!-- 用CDN获取公共资源 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!--  <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js" ></script> -->
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
<script src="/SecKill/resources/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
		$(function(){
			seckill.detail.init({
				//EL表达式取值
				seckillId : ${seckill.seckillId},
				startTime : ${seckill.startTime.time},//转化为毫秒
				endTime : ${seckill.endTime.time}
			});
		});
		
	
</script>





</html>