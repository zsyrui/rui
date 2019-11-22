layui.use(['form', 'layedit', 'laydate'], function(){
	var form = layui.form
		,layer = layui.layer
		,layedit = layui.layedit;
				
	var editIndex = layedit.build('LAY_demo_editor');
	
	form.on('submit(login)', function(data){
		var youxiang=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		var mima=/^[\S]{6,12}$/;
		var value=$("#login").val();
		var pass=$("#password").val();
		var log;
		var flag=0;
		if(value.length < 1){
        	log='请输入邮箱/用户名';
    	}
		if(value.length > 20){
        	log= '用户名最多20个字符';
    	}
		if(youxiang.test(value)){
//			$("input:text").attr("name",'email');
            var data={"email":value,"password":pass};
			flag=1;
		}
		if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
   			log= '用户名/邮箱格式不正确';
    	}		
		else if(/(^\_)|(\__)|(\_+$)/.test(value)){
      		log= '用户名/邮箱格式不正确';
    	}else if(/^\d+\d+\d$/.test(value)){		
    		log= '用户名/邮箱格式不正确';		
    	}else{
//    		$("input:text").attr("name",'name');
			var data={"name":value,"password":pass};
    		flag=1;
    	}
    	if(pass.length < 1){
        	log='请输入密码';
    	}
    	if(!mima.test(pass)){
    		flag=0;
    		log='密码是6到12位，且不能出现空格';
    	}
		if(flag==1){
			
    		$.ajax({
    			type:"post",
    			url:ul+"/dome/login",
    			contentType:"application/json",
    			data:JSON.stringify(data),
    			dataType:"json",
    			success:function (message) {//要修改
   					if( message == 100){ 
    					layer.msg( '登录成功' ,{
  							icon:6,
  							anim:0,
  							time:1500
  						},function (){
  							location.href="main.html";
  						});
    				}else if(message==1){
    					layer.msg('用户名/邮箱不存在',{
  							icon:2,
  							anim:0
  						});
    				}else if(message==0){
    					layer.msg('密码不正确',{
  							icon:2,
  							anim:0
  						});
    				}
                		
           	 	},
           		error:function () {
             	  	layer.msg('提交失败',{
  						icon:2,
  						anim:0
  					});
           		}
    		});
    		return false;
    	}
		
		else{
    		layer.msg(log,{
  				icon:2,
  				anim:0
  			});
  			
    	}
  });
});