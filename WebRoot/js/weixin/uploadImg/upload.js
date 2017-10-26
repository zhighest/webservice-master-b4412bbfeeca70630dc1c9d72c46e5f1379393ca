    /**
     * 获得base64
     * @param {Object} o
     * @param {Number} [o.width] 图片需要压缩的宽度
     * @param {Number} [o.height] 图片需要压缩的高度，为空则会跟随宽度调整
     * @param {Number} [o.quality=0.8] 压缩质量，不压缩为1
     * @param {Number} [o.mixsize] 上传图片大小限制
     * @param {Number} [o.type] 上传图片格式限制
     * @param {Function} [o.before(blob)] 处理前函数,this指向的是input:file
     * @param {Function} o.success(obj) 处理后函数
     * @param {Function} o.error(obj) 处理后函数
     * @example
     *
     * $('#test').UploadImg({
            url : '/upload.php',
            width : '320',
            //height : '200',
            quality : '0.8', //压缩率，默认值为0.8
            // 如果quality是1 宽和高都未设定 则上传原图
            mixsize : '1',
            //type : 'image/png,image/jpg,image/jpeg,image/pjpeg,image/gif,image/bmp,image/x-png',
            before : function(blob){
                //上传前返回图片blob
                $('#img').attr('src',blob);
            },
            error : function(res){
                //上传出错处理
                $('#img').attr('src','');
                $('#error').html(res);
            },
            success : function(res){
                //上传成功处理
                $('#imgurl').val(res);
            }
        });
     */


    $.fn.UploadImg = function(o){
        this.change(function(){
        	$("#loading").show();
            var file = this.files['0'];
            console.log(file);
            if (file) {
                if(file.size && file.size > o.mixsize){
        			$("#loading").hide();
                    o.error('图片大小超过限制');
                    this.value='';
                }else if(o.type && (file.type == "" || o.type.indexOf(file.type) < 0)){
        			$("#loading").hide();
                    o.error('图片格式不正确');
                    this.value='';
                }else{
                    var URL = window.URL || window.webkitURL;
                    var blob = URL.createObjectURL(file);
                    o.before(blob);
                    _compress(blob,file);
                    this.value='';
                }
            } else {
    			$("#loading").hide();
            }
        });


        function _compress(blob,file){
            var img = new Image();
            img.src = blob;
            img.onload = function(){
                var canvas = document.createElement('canvas');
                var ctx = canvas.getContext('2d');
                if(!o.width && !o.height && o.quality == 1){
                    var w = this.width;
                    var h = this.height;
                }else{
                    var w = o.width || this.width;
                    var h = o.height || w/this.width*this.height;
                }
                $(canvas).attr({width : w, height : h});
                ctx.drawImage(this, 0, 0, w, h);
                var base64 = canvas.toDataURL(file.type, (o.quality || 0.8)*1 );
                if( navigator.userAgent.match(/ipad|iphone|mac/i) ) {
                    var mpImg = new MegaPixImage(img);
                    mpImg.render(canvas, { maxWidth: w, maxHeight: h, quality: o.quality || 0.8, orientation: 6 });
                    base64 = canvas.toDataURL(file.type, o.quality || 0.8 );
                }

                // 修复android
                if( navigator.userAgent.match(/Android/i) ) {
                    var encoder = new JPEGEncoder();
                    base64 = encoder.encode(ctx.getImageData(0,0,w,h), o.quality * 100 || 80 );
                }

                _ajaximg(base64,file.type);
            };
        }

        function _ajaximg(base64,type){
        	var ajaxTimeOut = $.ajax({
                url: o.url,
                timeout: 10000, //10秒后请求超时
                type: "post",
                dataType:"json",
                // data: port.encrypt(str),
                data: {"imgData":base64},
        		success : function(data) {
                    o.success(data);
        		},
        		error : function(e) {
        			o.error("上传失败,请稍候再试");
        		},
        		complete : function(XMLHttpRequest,status){ // 请求完成后最终执行参数
        			$("#loading").hide();
	        		if(status=='timeout'){// 超时,status还有success,error等值的情况
	        			ajaxTimeOut.abort(); // 取消请求
	        			o.error("请求超时,请稍候再试");
	        		}
        		}
            });

        }
    };

