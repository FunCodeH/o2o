/**
 * 
 */
$(function(){
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	getShopInitInfo();
	function getShopInitInfo(){
		$.getJSON(initUrl, function(data){
			if(data.success){
				//获取后台数据并拼接
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item,index){
					tempHtml += '<option data-id="' + item.shopCategoryId
						+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item,index){
					tempAreaHtml += '<option data-id="' + item.areaId
						+ '">' + item.areaName + '</option>';
				});
				//塞到html控件中
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}
	// 提交按钮的事件响应，分别对店铺注册和编辑操作做不同响应
	$('#submit').click(function(){
		var shop = {};
		// 获取表单里的数据并填充进对应的店铺属性中
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		// 选择选定好的店铺类别
		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		// 选择选定好的区域信息
		shop.area = {
			areaId : $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		// 获取上传的图片文件流
		var shopImg = $('#shop-img')[0].files[0];
		// 生成表单对象，用于接收参数并传递给后台
		var formData = new FormData();
		// 添加图片流进表单对象里
		formData.append('shopImg', shopImg);
		// 将shop json对象转成字符流保存至表单对象key为shopStr的的键值对里
		formData.append('shopStr', JSON.stringify(shop));
		
		var verifyCodeActual = $('#j_captcha').val();
		if(!verifyCodeActual){
			$.toast("请输入验证码！");
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		// 将数据提交至后台处理相关操作
		$.ajax({
			url : registerShopUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
				} else {
					$.toast('提交失败！' + data.errMsg);
				}
				//提交成功与否都需要更好验证码
				$('#captcha_img').click();
			}
		});
	});	
	
})