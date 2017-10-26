<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="no" />
    <meta name="apple-touch-fullscreen" content="yes" />
    <meta name="format-detection" content="telephone=no"/>
    <meta name="description" content="联璧金融为您提供个人理财产品，安全可靠、高利率、有保障，是您个人理财投资的理想选择！"/>
    <meta name="keywords" content="联璧金融，网络理财，个人理财，互联网理财"/>
    <meta name="sogou_site_verification" content="bbIOehiDPk"/>
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>免费注册，联璧金融值得信赖的网络理财平台</title>
    <link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/landingPageZhizhuwang.css?<%=request.getAttribute("version")%>" />
    <script src="${pageContext.request.contextPath}/js/web/integration.js?<%=request.getAttribute("version")%>"></script>
</head>
   <body class="grayBkg01">
    <div class="whiteBkg" id="header">
        <div class="header clearfix PT20 PB15 blockC">
            <div class="fl">
                <img src="${pageContext.request.contextPath}/pic/web/logo.png" height="38px">
            </div>
            <div class="fr PT10">
                <a class="grayTex" href="${pageContext.request.contextPath}/webindex/goIndex">进入联璧官网</a>
            </div>
        </div>
    </div>
    <div class="banner positionR">
         <div class="positionA landingBkgImg" id="banner">
             <img  src="${pageContext.request.contextPath}/pic/web/landingPageWeb/gewalaWebBanner.jpg?<%=request.getAttribute("version")%>">
         </div>
         <div class="bannerCotent positionR blockC">
            <div class="positionA loginBox radius5 outHide">
                <div class="PB20">
                    <h4 class="font20 PT15 redTex PB15 strong">免费注册</h4>
                    <div class="PLR15">
                        <div class="positionR MT10">
                            <label class="loginIcon positionA" for="phoneNum">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/loginIcon1.png" width="25px">
                            </label>
                            <input class="inputNoborder bkgNone radius10 PL40 PTB5 width230 height30" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" placeholder="请输入手机号" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" />
                        </div>
                         <!-- 登录密码-->
                        <div class="positionR MT20">
                            <label class="loginIcon positionA" for="loginPassword">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/loginIcon2.png" width="25px">
                            </label>
                            <input type="password" class="inputNoborder bkgNone radius10 PL40 PTB5 width230 height30" name="loginPassword" id="loginPassword" value="" maxlength="16" placeholder="密码由6-16个字符（字母+数字）"/>
                        </div>
                        <!-- 图形验证码 -->
                        <div class="positionR MT20 clearfix">
                            <label class="loginIcon positionA" for="verifycode">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/loginIcon3.png" width="25px">
                            </label>
                            <input type="text" class="inputNoborder PL40 radius10 PTB5 fl bkgNone width120 height30" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4"/>
                            <img class="fr radius10 cursor" id="imgcode" src="${pageContext.request.contextPath}/imgcode" width="110px" height="40px">
                        </div>
                        <!-- 手机验证码 -->
                        <div class="positionR MT20 clearfix">
                            <label class="loginIcon positionA" for="checkCode">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/loginIcon4.png" width="25px">
                            </label>
                            <input id="checkCode" maxlength="4" name="checkCode" value="${userDto.checkCode}" class="inputNoborder PL40 radius10 PTB5 fl bkgNone width120 height30" placeholder="手机验证码" oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);"/>
                            <div class="smallBtn1 fr font14 redBkg whiteTex radius10 cursor" id="gainCode">获取验证码</div>
                        </div>
                        <div class="whiteTex MT20 forbid font20 PTB10 redBkg width100 blockC radius10 cursor" id="loginSubmit">立即注册</div>
                        <p class="textL PL30 whiteTex  blockC grayTex font12 MT20 positionR">
                            <img class="positionA checkboxImg cursor" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/loginIcon5.png" id="checkboxImg" width="15">
                            我已阅读
                            <a href="${pageContext.request.contextPath}/wxabout/regAgreement" class=" whiteTex">《用户注册服务协议》</a>
                         </p>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- <div class='blockC cloudBkgBoxWrap'> -->
        <div class="PT40  advantageWrap blockC">
             <div class="center blockC">
            <h2 class="strong font30 advantage blockC MT20">联璧金融优势</h2>
            <ul class="advantageList clearfix MT50 whiteTex">
                <li class="fl">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/advantageListIcon1.png" width="80%">
                    <h3 class=" strong font24 MT30 blackTex">高收益 随存随取</h3>
                    <p class=" font18 MT10 lineHeight2x grayTex">8%活期理财 资金快速提现</p>
                </li>
                <li class="fl">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/advantageListIcon2.png" width="80%">
                    <h3 class=" strong font24 MT30 blackTex">低门槛 百元起投</h3>
                    <p class=" font18 MT10 lineHeight2x grayTex">100元起投 人人轻松理财</p>
                </li>
                <li class="fl">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/advantageListIcon3.png" width="80%">
                    <h3 class=" strong font24 MT30 blackTex">风险低 优质债权</h3>
                    <p class=" font18 MT10 lineHeight2x grayTex">供应链资产 实力平台担保</p>
                </li>
                <li class="fl">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/advantageListIcon4.png" width="80%">
                    <h3 class=" strong font24 MT30 blackTex">强保障 B轮融资</h3>
                    <p class=" font18 MT10 lineHeight2x grayTex">资金托管 1亿元实缴资金</p>
                </li>
            </ul>
          </div>
        </div>
    <!-- </div> -->
    <!-- 活动规则 -->
     <div class="activity">
     <h2 class="activityTitle font30 strong whiteTex">活 动 规 则</h2>
        <ul class="activityRules  textL ">
           <li>1、本活动仅限通过本页面注册联璧金融的新用户:</li>
	       <li> 2、注册联璧金融并成功首次投资铃铛宝定期（3月期或6月期）1000元（含）以上的用户即送185元格瓦拉电影大礼包一份，数量有限送完即止；</li>
	       <li>3、投资成功后，电影票电子码将在3个工作日内通过客服确认后以短信形式发送，节假日延后至工作日发放；</li>
	       <li>4、新用户注册送1%加息券，加息券可以在个人账户里查看。</li>
           </ul>
         <div class="copyrightOwn textC">本活动最终解释权归联璧金融平台所有</div>
    </div>
    <!-- <div class="heigh35"></div> -->
    <div class="productWrap outHide PB70">
        <div class="productBkg"> <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/blueLineBkg.png"/> </div>
        <h2 class="productTitle font30 strong whiteTex">联璧钱包，把零钱联起来</h2>
        <div class="product blockC MT50 " id="productList">

        </div>
    </div>
    <div class="choose">
        <h2 class="blackTex1 strong font20">为什么选择联璧金融</h2>
        <h3 class="font14 blackTex1 strong MT20">专业的P2P理财专家为投资理财人士提供安全、高收益的投资理财产品</h3>
        <div class="compare PT40 PB40">
            <div class="center blockC">
                <h3 class="font22 blackTex positionR"></span>用10,000元投资，哪种理财渠道1年为您赚取 <span class="redTex">最多</span></h3>
                <div class="clearfix MT50">
                    <div class="fl textR width25">
                        <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/smallLogo.png" height="30px">
                    </div>
                    <div class="fr textL width70">
                        <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/compareImg1.png" height="30px">
                    </div>
                </div>
                <div class="clearfix MT50">
                    <div class="fl textR blackTex width25 font18">
                        银行理财
                    </div>
                    <div class="fr textL width70">
                        <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/compareImg2.png" height="30px">
                    </div>
                </div>
                <div class="clearfix MT50">
                    <div class="fl textR width25 blackTex font18" >
                        宝宝类/货币基金
                    </div>
                    <div class="fr textL width70">
                        <img src="${pageContext.request.contextPath}/pic/web/landingPageWeb/compareImg3.png" height="30px">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="PT60 PB40 companyItroduce">
    <h2  class=" font30 strong whiteTex aboutLB">关于联璧</h2>
        <div class="introduceBkg">
            <img class="bkg01 bkg" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/blueBkg1.png">
            <img class="bkg02 bkg" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/blueBkg2.png">
            <img class="bkg03 bkg" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/blueBkg3.png">
            <img class="bkg04 bkg" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/blueBkg4.png">
            <ul class="center blockC">
                    <li class="clearfix width90 blockC">
                        <div class="fl width30 PT20 PB20">
                            <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/photo1.png" width="100%">
                        </div>
                        <div class="fr width60">
                            <h4 class="redTex subtitle font18 whiteBkg">联璧金融</h4>
                            <p class="font14  MT20 textL lineHeight3x">诞生于科技基因强大的联璧电子，核心团队由金融数学、统计精算、及互联网技术精英组成。受上海市松江区政府扶持，入驻区内产业园区，主营互联网金融资产管理服务，依托联璧科技的移动通信及大数据技术，帮助传统行业实现互联网转型的过程中，带动资本正向流动力量，让金融，惠民生。</p>
                        </div>
                    </li>
                    <li class="clearfix width90 blockC MT70">
                        <div class="fr width30 PT20 PB20">
                            <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/photo2.png" width="100%">
                        </div>
                        <div class="fl width60">
                            <h4 class="redTex subtitle font18 whiteBkg">平台理念</h4>
                            <p class="font14  MT20 textL lineHeight3x">联璧带来了互联网金融服务产品—联璧钱包。在联璧产品发布环节，联璧首席执行官侬锦先生指出：“联璧对未来十年企业和企业之间的竞争思考，提出未来竞争的关键是供应链的竞争、是平台化服务的竞争。联璧用互联网金融+O2O+一体化服务运营建立了联璧的移动互联网服务平台，它是面向未来的，承载实体商业的，利国利民的。</p>
                        </div>
                    </li>
                    <li class="clearfix width90 blockC MT70">
                        <div class="fl width30 PT20 PB20">
                            <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/photo3.png" width="100%">
                        </div>
                        <div class="fr width60 ">
                            <h4 class="redTex subtitle font18 whiteBkg">平台宗旨</h4>
                            <ul class="circle textL font14 lineHeight3x MT20 platFormPurpose">
                                <li>
                                    自省自律，明确平台性质，绝不碰触监管红线
                                </li>
                                <li>
                                    合法合规，注册资本金充足，管理机制完善，定期内审
                                </li>
                                <li>
                                    资产保障，风控体系严谨实际，交易对手优质稳健，资产保障多重多样
                                </li>
                                <li>
                                    资金安全，账户安全，交易安全，清算明晰，结算及时，实时监控
                                </li>
                            </ul>
         
                         </div>
                     </li>
                   <li class="clearfix width90 blockC MT70">
                      <div class="fr width30 PT20 PB20">
                          <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/photo4.png" width="100%">
                      </div>
                      <div class="fl width60">
                          <h4 class="redTex subtitle font18 whiteBkg">战略合作</h4>
                          <p class="font14 MT20 textL lineHeight3x">联璧科技成立于2012年，注册资本1亿元，实缴资金 1亿元，总部位于中国上海，是上海市松江区电子商务协会会长单位，受政府扶持，利用自有园区产业，致力于“帮助传 统行业实现O2O转型”，于2015年与上海某知名通信企业联手，实现资源战略对接，构建了以“端管、应用、云、运营”五大元素为核心的O2O服务平台。</p>
                       </div>
                   </li>
            </ul>
         </div> 
    </div>

    <div class="footer PT20 PB20 blackTex1">
        <p class="font16">上海联璧电子科技有限公司</p>
        <p class="font14 MT10">联璧金融版权所有 2015 沪ICP备15009293号-2</p>
    </div>
    <div class="relation positionF width100 ">
        <div class="center blockC positionR">
            <div class="fl">
                <img class="MT40 fl" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/phoneIcon.png" height="60px">
                <div class="fl ML10 MT30 textL">
                    <p class="whiteTex font30 MT10 JsPhoneCall"></p>
                    <p class="whiteTex font14">服务时间  09:00-21:00</p>
                </div>
            </div>
            <div class="fr MT50 ">
                <!-- <div class="fl MR20 MT10 redTex font14 positionA"> -->
                    <img class=" downloadApp positionA" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/downloadApp.png"/>
                <!-- </div> -->
                <div class="fl MR20 margin_40">
                    <img class="redPhoneIcon" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/twoCode2.jpg" width="100px" alt="扫码下载联璧金融APP，想投就投，随时随地">
                    <p class="font14 whiteTex">下载APP</p>
                </div>
                <div class="fl MR20 margin_40">
                    <img class="redPhoneIcon" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/twoCode1.jpg" width="100px" alt="关注联璧金融微信公众号，及时查看官网和了解最新产品信息">
                    <p class="font14 whiteTex">关注我们</p>
                </div>
            </div>
        </div>
         <div class="closeBtn positionA cursor"><img width="25" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/error.png"/></div>
    </div>
    <div class="positionF smallDownload outHide cursor"><img width="193" src="${pageContext.request.contextPath}/pic/web/landingPageWeb/download.png"/></div>
    <%@  include file="../baiduStatistics.jsp"%>
  </body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/web/page/slide.js?<%=request.getAttribute("version")%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/web/page/landingPageGewala.js?<%=request.getAttribute("version")%>"></script>
</html>
