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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/landingPage1.css?<%=request.getAttribute("version")%>" />
    <script src="${pageContext.request.contextPath}/js/web/integration.js?<%=request.getAttribute("version")%>"></script>
</head>
  
  <body class="whiteBkg">
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
    <div class="banner">
        <div class="bannerCotent positionR blockC">
            <img class="positionA landingImg" src="${pageContext.request.contextPath}/pic/web/wojiatisu/2.png" height="480px">
            <div class="positionA loginBox radius5 outHide">
                <div class="PB20">
                    <h4 class="whiteTex redBkg font20 PT15 PB15">免费注册</h4>
                    <div class="PLR10">
                        <div class="positionR MT20">
                            <label class="loginIcon positionA" for="phoneNum">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPage/loginIcon1.png" width="15px">
                            </label>
                            <input class="inputNoborder bkgNone radius5 PL40 PTB10 width240 height20" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" placeholder="请输入手机号" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" />
                        </div>
                        <div class="positionR MT10 clearfix">
                            <label class="loginIcon positionA" for="verifycode">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPage/loginIcon2.png" width="15px">
                            </label>
                            <input type="text" class="inputNoborder PL40 radius5 PTB10 fl bkgNone width145 height20" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4"/>
                            <img class="fr radius5 cursor" id="imgcode" src="${pageContext.request.contextPath}/imgcode" width="80px" height="45px">
                        </div>
                        <div class="positionR MT10 clearfix">
                            <label class="loginIcon positionA" for="checkCode">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPage/loginIcon3.png" width="15px">
                            </label>
                            <input id="checkCode" maxlength="4" name="checkCode" value="${userDto.checkCode}" class="inputNoborder PL40 radius5 PTB10 fl bkgNone width145 height20" placeholder="请输入验证码" oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);"/>
                            <div class="smallBtn1 fr font14 redBkg whiteTex radius5 cursor" id="gainCode">获取验证码</div>
                        </div>
                        <!-- 登录密码-->
                        <div class="positionR MT10">
                            <label class="loginIcon positionA" for="loginPassword">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPage/loginIcon6.png" width="15px">
                            </label>
                            <input type="password" class="inputNoborder bkgNone radius5 PL40 PTB10 width240 height20" name="loginPassword" id="loginPassword" value="" maxlength="16" placeholder="请设置登录密码"/>
                        </div>
                        <!-- 交易密码-->
                       <!--  <div class="positionR MT10">
                            <label class="loginIcon positionA" for="password">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPage/loginIcon4.png" width="15px">
                            </label>
                            <input type="password" class="inputNoborder bkgNone radius5 PL40 PTB10 width240 height20" name="password" id="password" value="${userDto.phoneNum}" maxlength="6"  oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);"  placeholder="请设置交易密码（6位数字）"/>
                        </div> -->
                        <!-- <div class="positionR MT10">
                            <label class="loginIcon positionA" for="passwordAgin">
                                <img src="${pageContext.request.contextPath}/pic/web/landingPage/loginIcon4.png" width="15px">
                            </label>
                            <input type="password" class="inputNoborder bkgNone radius5 PL40 PTB10 width240 height20" name="passwordAgin" id="passwordAgin" value="${userDto.phoneNum}" maxlength="6"  oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);" placeholder="请再次输入交易密码（6位数字）"/>
                        </div> -->
                        <p class="textL PL40 PR20 blockC grayTex font14 MT10 positionR">
                            <img class="positionA checkboxImg cursor" src="${pageContext.request.contextPath}/pic/web/landingPage/loginIcon5.png" id="checkboxImg" width="14">
                            我同意
                            <a href="${pageContext.request.contextPath}/wxabout/regAgreement" class="grayTex">《用户注册服务协议》</a>
                        </p>
                        <div class="whiteTex MT15 forbid font20 PTB10 redBkg width80 blockC radius5 cursor" id="loginSubmit">立即注册</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="redBkgBox MT70"></div>
    <div class="redBkg textL PT60 PB40">
        <div class="width60 MLRA lineHeight2x whiteTex newwidth">
            <div class="whiteBkg PLR30 heigh40 redTex whiteBox MLRA textC MB20">活动细节</div>
            <p class="strong">1、本活动仅限本页面注册的联璧金融新用户参与，活动截止到2016.7.9号24：00整。</p>
            <p class="strong">2、活动参与流程：在本页面注册联璧金融，下载联璧金融app，在联璧金融app活动页指定的链接购买斐讯k2路由器，获得路由器k码，激活k码获得理财大礼包。</p>
            <p class="strong">3、赠送的419元一个月理财大礼包到期后即可本息提现，无任何费用，相当于0元购路由器。</p>
            <p class="strong">4、联璧金融客服电话：<span class="JsPhoneCall"></span></p>
            <p class="strong">5、本活动最终解释权归联璧金融平台所有；</p>
        </div>
        
    </div>
    <div class="redLine redBkg MT80 width100 positionR">
        <img class="positionA titleImg" src="${pageContext.request.contextPath}/pic/web/landingPage/titleImg.png" height="70px">
    </div>
    <div class="product blockC MT100" id="productList">

    </div>
    <div class="MT70 PT60 PB70 yellowBkgBox">
        <div class="center blockC">
            <h2 class="blackTex1 strong font20">我们的优势</h2>
            <ul class="advantageList clearfix MT30">
                <li class="fl whiteBkg">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPage/advantageListIcon1.png" width="45%">
                    <h3 class="blackTex1 strong font14 MT30">高收益 更胜一筹</h3>
                    <p class="grayTex font12 MT10 lineHeight2x">高出同类产品利率 秒杀各种"宝"</br>活期年化利率更高 随心投轻松赚</br>产品收益率浮动小 更稳定更安心</p>
                </li>
                <li class="fl whiteBkg">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPage/advantageListIcon2.png" width="45%">
                    <h3 class="blackTex1 strong font14 MT30">低门槛 百元起投</h3>
                    <p class="grayTex font12 MT10 lineHeight2x">微小资金累计增长 小投资大收益</br>最低百元即可投资 理财门槛更低</br>零钱变身投资本金 小金融稳稳的</p>
                </li>
                <li class="fl whiteBkg">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPage/advantageListIcon3.png" width="45%">
                    <h3 class="blackTex1 strong font14 MT30">零风险 资金监管</h3>
                    <p class="grayTex font12 MT10 lineHeight2x">国际信息安全标准 强力保护数据</br>资金透明同卡进出 由第三方监管</br>产品资金安全保障 上市公司背书</p>
                </li>
                <li class="fl whiteBkg">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPage/advantageListIcon4.png" width="45%">
                    <h3 class="blackTex1 strong font14 MT30">更灵活 随存随取</h3>
                    <p class="grayTex font12 MT10 lineHeight2x">一键购买省时省力 更快捷更便利</br>下午三点之前提现 当日即可到账</br>个人资金灵活运用 投资随心所欲</p>
                </li>
            </ul>
        </div>
    </div>
    <div class="MT70">
        <h2 class="blackTex1 strong font20">理财频道</h2>
        <h3 class="font14 blackTex1 strong MT20">专业的互联网金融理财专家为投资理财人士提供安全、高收益的投资理财产品</h3>
        <div class="compare PT40 PB40 MT20">
            <div class="center blockC">
                <h3 class="font22 blackTex positionR"><span class="line1 positionA"></span>用10,000元投资，哪种理财渠道1年为您赚取 <span class="redTex">最多</span><span class="line2 positionA"></span></h3>
                <div class="clearfix MT50">
                    <div class="fl textR width25">
                        <img src="${pageContext.request.contextPath}/pic/web/landingPage/smallLogo.png" height="30px">
                    </div>
                    <div class="fr textL width70">
                        <img src="${pageContext.request.contextPath}/pic/web/landingPage/compareImg1.png" height="30px">
                    </div>
                </div>
                <div class="clearfix MT50">
                    <div class="fl textR blackTex width25 font18">
                        银行理财
                    </div>
                    <div class="fr textL width70">
                        <img src="${pageContext.request.contextPath}/pic/web/landingPage/compareImg2.png" height="30px">
                    </div>
                </div>
                <div class="clearfix MT50">
                    <div class="fl textR width25 blackTex font18" >
                        宝宝类/货币基金
                    </div>
                    <div class="fr textL width70">
                        <img src="${pageContext.request.contextPath}/pic/web/landingPage/compareImg3.png" height="30px">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="redBkgBox MT70"></div>
    <div class="PT60 PB40 redBkg">
        <ul class="center blockC">
            <li class="clearfix width90 blockC">
                <div class="fl width30 PT20 PB20">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPage/photo1.png" width="100%">
                </div>
                <div class="fr width60">
                    <h4 class="redTex subtitle font18 whiteBkg">联璧金融</h4>
                    <p class="font14 whiteTex MT20 textL lineHeight3x">诞生于科技基因强大的联璧电子，核心团队由金融数学、统计精算、及互联网技术精英组成。受上海市松江区政府扶持，入驻区内产业园区，主营互联网金融资产管理服务，依托联璧科技的移动通信及大数据技术，帮助传统行业实现互联网转型的过程中，带动资本正向流动力量，让金融，惠民生。</p>
                </div>
            </li>
            <li class="clearfix width90 blockC MT70">
                <div class="fr width30 PT20 PB20">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPage/photo2.png" width="100%">
                </div>
                <div class="fl width60">
                    <h4 class="redTex subtitle font18 whiteBkg">平台理念</h4>
                    <p class="font14 whiteTex MT20 textL lineHeight3x">联璧带来了互联网金融服务产品—联璧钱包。在联璧产品发布环节，联璧首席执行官侬锦先生指出：“联璧对未来十年企业和企业之间的竞争思考，提出未来竞争的关键是供应链的竞争、是平台化服务的竞争。联璧用互联网金融+O2O+一体化服务运营建立了联璧的移动互联网服务平台，它是面向未来的，承载实体商业的，利国利民的。</p>
                </div>
            </li>
            <li class="clearfix width90 blockC MT70">
                <div class="fl width30 PT20 PB20">
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPage/photo3.png" width="100%">
                </div>
                <div class="fr width60">
                    <h4 class="redTex subtitle font18 whiteBkg">平台宗旨</h4>
                    <ul class="circle textL whiteTex font14 lineHeight3x MT20">
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
                    <img class="block blockC" src="${pageContext.request.contextPath}/pic/web/landingPage/photo4.png" width="100%">
                </div>
                <div class="fl width60">
                    <h4 class="redTex subtitle font18 whiteBkg">战略合作</h4>
                    <p class="font14 whiteTex MT20 textL lineHeight3x">联璧科技成立于2012年，注册资本1亿元，实缴资金 1亿元，总部位于中国上海，是上海市松江区电子商务协会会长单位，受政府扶持，利用自有园区产业，致力于“帮助传 统行业实现O2O转型”，于2015年与上海某知名通信企业联手，实现资源战略对接，构建了以“端管、应用、云、运营”五大元素为核心的O2O服务平台。</p>
                </div>
            </li>
        </ul>
    </div>

    <div class="footer PT20 PB20 blackTex1 MB80">
        <p class="font16">上海联璧电子科技有限公司</p>
        <p class="font14 MT10">联璧金融版权所有 2015 沪ICP备15009293号-2</p>
    </div>
    <div class="relation positionF width100">
        <div class="center blockC">
            <div class="fl">
                <img class="MT10 fl" src="${pageContext.request.contextPath}/pic/web/landingPage/phoneIcon.png" height="60px">
                <div class="fl ML10 textL">
                    <p class="whiteTex font30 MT10 JsPhoneCall">4006-999-211</p>
                    <p class="whiteTex font14">服务时间  09:00-21:00</p>
                </div>
            </div>
            <div class="fr">
                <div class="fl MR20 MT10 redTex font14 positionR">
                    <img class="positionA redPhoneIcon" src="${pageContext.request.contextPath}/pic/web/landingPage/phoneIcon2.png" width="20px">
                    扫码下载APP
                </div>
                <div class="fl MR20 margin_40">
                    <img class="redPhoneIcon" src="${pageContext.request.contextPath}/pic/web/landingPage/twoCode2.jpg" width="100px" alt="扫码下载联璧金融APP，想投就投，随时随地">
                    <p class="font14 whiteTex">下载APP</p>
                </div>
                <div class="fl MR20 margin_40">
                    <img class="redPhoneIcon" src="${pageContext.request.contextPath}/pic/web/landingPage/twoCode1.jpg" width="100px" alt="关注联璧金融微信公众号，及时查看官网和了解最新产品信息">
                    <p class="font14 whiteTex">关注我们</p>
                </div>
            </div>
        </div>
    </div>
     <%@  include file="../baiduStatistics.jsp"%>
  </body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/web/page/landingPageWojiatisu.js?<%=request.getAttribute("version")%>"></script>
  <script src="https://s4.cnzz.com/z_stat.php?id=1259544535&web_id=1259544535" language="JavaScript"></script>
</html>
