<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
  <title>系统正在审查，请稍等...</title>
  <style type="text/css">
  
  *{
	margin:0px auto;
	padding:0px;
	font-family: '微软雅黑';
  }
 #root{
	width: 300px;
	height: 200px;
	text-align: center;
	background: #ff000000;
	border: 1px solid #eaeaea;
 }

.sk-cube-grid {
    /* background: url(a.png) no-repeat; */
    /* background-position: 7px 2px; */
    width: 95px;
    height: 95px;
	margin-top: 20px;
    /*margin: 80px auto;*/
  /*
   * Spinner positions
   * 1 2 3
   * 4 5 6
   * 7 8 9
   */
    /* background: red; */
    }
  .sk-cube-grid .sk-cube {
    background: url(${pageContext.request.contextPath}/res/hospital_common/img/logo.png) no-repeat;
    width: 33.33%;
    height: 33.33%;
    background-color: #ffffff;
    float: left;
    -webkit-animation: sk-cubeGridScaleDelay 1.3s infinite ease-in-out;
    /* animation: sk-cubeGridScaleDelay 1.3s infinite ease-in-out; */
    }
  .sk-cube-grid .sk-cube1 {
    -webkit-animation-delay: 0.2s;
    animation-delay: 0.2s;
    background-position: 0px 0px;
    }
  .sk-cube-grid .sk-cube2 {
    -webkit-animation-delay: 0.3s;
    animation-delay: 0.3s;
    /* background-position: 0px, 27px; */
    background-position: -32px 0px;
    }
  .sk-cube-grid .sk-cube3 {
    -webkit-animation-delay: 0.4s;
    animation-delay: 0.4s;
    background-position: -64px 0px;
    }
  .sk-cube-grid .sk-cube4 {
    -webkit-animation-delay: 0.1s;
    animation-delay: 0.1s;
    background-position: 0px -32px;
    }
  .sk-cube-grid .sk-cube5 {
    -webkit-animation-delay: 0.2s;
    animation-delay: 0.2s;
    background-position: -32px -32px;
    }
  .sk-cube-grid .sk-cube6 {
    -webkit-animation-delay: 0.3s;
    animation-delay: 0.3s;
    background-position: -64px -32px;
    }
  .sk-cube-grid .sk-cube7 {
    -webkit-animation-delay: 0.0s;
    animation-delay: 0.0s;
    background-position: 0px -64px;
    }
  .sk-cube-grid .sk-cube8 {
    -webkit-animation-delay: 0.1s;
    animation-delay: 0.1s;
    background-position: -32px -64px;
    }
  .sk-cube-grid .sk-cube9 {
    -webkit-animation-delay: 0.2s;
    animation-delay: 0.2s;
    background-position: -64px -64px;
    }

@-webkit-keyframes sk-cubeGridScaleDelay {
  0%, 70%, 100% {
    -webkit-transform: scale3D(1, 1, 1);
            transform: scale3D(1, 1, 1); }
  35% {
    -webkit-transform: scale3D(0, 0, 1);
            transform: scale3D(0, 0, 1); } }

@keyframes sk-cubeGridScaleDelay {
  0%, 70%, 100% {
    -webkit-transform: scale3D(1, 1, 1);
            transform: scale3D(1, 1, 1); }
  35% {
    -webkit-transform: scale3D(0, 0, 1);
            transform: scale3D(0, 0, 1); } }


	/* 文字部分 */
	.txt-des{
		margin-top: 20px;
		font-size: 20px;
		color: #22b0b1;
        text-align: center;
        background-image: -webkit-linear-gradient(left, #00a0e0, #009a52 25%, #00a0e0 50%, #009a55 75%, #00a0e0);
        -webkit-text-fill-color: transparent;
        -webkit-background-clip: text;
        -webkit-background-size: 200% 100%;
        -webkit-animation: txt-des-animation 4s infinite linear;
      }
    @-webkit-keyframes txt-des-animation {
         0%{ background-position: 0 0;}
         100% { background-position: -100% 0;}
    }
  </style>
</head>
<body>
	<div id="root">
		<div class="sk-cube-grid">
        <div class="sk-cube sk-cube1"></div>
        <div class="sk-cube sk-cube2"></div>
        <div class="sk-cube sk-cube3"></div>
        <div class="sk-cube sk-cube4"></div>
        <div class="sk-cube sk-cube5"></div>
        <div class="sk-cube sk-cube6"></div>
        <div class="sk-cube sk-cube7"></div>
        <div class="sk-cube sk-cube8"></div>
        <div class="sk-cube sk-cube9"></div>
      </div>
	  <div class="txt-des">
		系统正在审查，请稍等...
	  </div>
	</div>
      
</body>
</html>