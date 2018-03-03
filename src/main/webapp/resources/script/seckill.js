//交互逻辑
//javascript模块化
//类似java分包
var seckill={
		//URL封装
	URL : {
		now : function(){
			return '/SecKill/seckill/time/now';
		},
		exposer : function(seckillId){
			return '/SecKill/seckill/'+seckillId+'/exposer';
		},
		execution : function(seckillId,md5){
			return '/SecKill/seckill/'+seckillId+'/'+md5+'/execution';
		}
		
	},
	handleSeckill:function(seckillId,node){
		//获取秒杀地址，控制显示逻辑，执行秒杀
		
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
		$.post(seckill.URL.exposer(seckillId),{},function(result){
			if(result&&result['success']){
				var exposer = result['data'];
				if(exposer['exposed']){
					//开启秒杀，获取秒杀地址
					var md5 = exposer['md5'];
					var killUrl = seckill.URL.execution(seckillId,md5);
					console.log('killUrl:'+killUrl);
					//绑定一次点击事件
					$('#killBtn').one('click',function(){
						//执行秒杀请求
						$(this).addClass('disabled');
						//发送秒杀请求
						$.post(killUrl,{},function(result){
							if(result&&result['success']){
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								node.html('<span class="label label-success">'+stateInfo+'</span>');
							}else{
								console.log('result:'+result);
							}
						});
					});
					node.show();
				}else{
					
					//未开始秒杀,机器的时间偏差
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					//重新计算计时逻辑
					seckill.countdown(seckillId,now,start,end);
				}
			}else{
				console.log('result:'+result);
			}
		},'json');
		
	},
	validatePhone : function(phone){
		if(phone&&phone.length==11&&!isNaN(phone)){
			return true;
		}else{
			return false;
		}
		
	},
	countdown:function(seckillId,nowTime,startTime,endTime){
		//时间判断
		
		var seckillBox = $('#seckill-box');
		if(nowTime>endTime){
			//秒杀结束
			seckillBox.html('秒杀结束');
		}else if(nowTime<startTime){
			//秒杀未开始，计时事件绑定
			var killTime = new Date(startTime+1000);
			seckillBox.countdown(killTime,function(event){
				var format = event.strftime('秒杀倒计时：%D天  %H时  %M分  %S秒');
				seckillBox.html(format);
				//时间完成后回调事件
			}).on('finish.countdown',function(){
				//获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSeckill(seckillId,seckillBox);
			});
			
		}else{
			//秒杀开始
			
			seckill.handleSeckill(seckillId,seckillBox);
		}
	},
	detail:{
		init : function(params){
			//用户手机验证登陆，计时交互
			var killPhone = $.cookie('killPhone');
			
			//验证手机号
			if(!seckill.validatePhone(killPhone)){
				//绑定手机号
				var killPhoneModal = $('#killPhoneModal');
				killPhoneModal.modal({
					show : true,//显示弹出层
					backdrop : false,//禁止位置关闭
					keyboard : false//关闭键盘事件
					
				});
				
				$('#killPhoneBtn').click(function(){
					var inputPhone = $('#killPhoneKey').val();
					if(seckill.validatePhone(inputPhone)){
						//手机号写入cookie
						$.cookie('killPhone',inputPhone,{expires:7,path:'/SecKill'});
						//刷新页面
						window.location.reload();
					}else{
						//先隐藏再显示
						$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
					}
				});
			}
			//已经登录，计时交互
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			$.get(seckill.URL.now(),{},function(result){
				if(result&&result['success']){
					var nowTime = result['data'];
					//时间判断
					
					seckill.countdown(seckillId,nowTime,startTime,endTime);
				}else{
					console.log('result:'+result);
				}
			},'json');
				
			}
		}
		
	
		
		
};

