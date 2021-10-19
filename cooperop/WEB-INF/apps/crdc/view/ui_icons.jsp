<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="图标库" ismodal="true" dispermission="true">
	<link
		href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css"
		rel="stylesheet" type="text/css" />
	<link
		href="${pageContext.request.contextPath}/theme/plugins/simple-line-icons/simple-line-icons.min.css"
		rel="stylesheet" type="text/css" />
	<link
		href="${pageContext.request.contextPath}/theme/plugins/bootstrap/css/bootstrap.min.css"
		rel="stylesheet" type="text/css" />
	<link
		href="${pageContext.request.contextPath}/theme/plugins/uniform/css/uniform.default.css"
		rel="stylesheet" type="text/css" />
	<link
		href="${pageContext.request.contextPath}/theme/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
		rel="stylesheet" type="text/css" />
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN THEME STYLES -->
	<link
		href="${pageContext.request.contextPath}/theme/css/components.css"
		id="style_components" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/theme/css/plugins.css"
		rel="stylesheet" type="text/css" />
	<link
		href="${pageContext.request.contextPath}/theme/layout/css/layout.css"
		rel="stylesheet" type="text/css" />
	<%-- <link id="style_color" href="${pageContext.request.contextPath}/theme/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css" /> --%>
	<link href="${pageContext.request.contextPath}/theme/layout/css/custom.css"
		rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" href="favicon.ico" />
	<s:row>

		<h3>71 New Icons in 4.1</h3>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-automobile"></i>fa fa-automobile <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bank"></i>fa fa-bank <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-behance"></i>fa fa-behance
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-behance-square"></i>fa fa-behance-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bomb"></i>fa fa-bomb
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-building"></i>fa fa-building
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cab"></i>fa fa-cab <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-car"></i>fa fa-car
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-child"></i>fa fa-child
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle-o-notch"></i>fa fa-circle-o-notch
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle-thin"></i>fa fa-circle-thin
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-codepen"></i>fa fa-codepen
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cube"></i>fa fa-cube
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cubes"></i>fa fa-cubes
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-database"></i>fa fa-database
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-delicious"></i>fa fa-delicious
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-deviantart"></i>fa fa-deviantart
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-digg"></i>fa fa-digg
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-drupal"></i>fa fa-drupal
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-empire"></i>fa fa-empire
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-envelope-square"></i>fa fa-envelope-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-fax"></i>fa fa-fax
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-archive-o"></i>fa fa-file-archive-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-audio-o"></i>fa fa-file-audio-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-code-o"></i>fa fa-file-code-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-excel-o"></i>fa fa-file-excel-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-image-o"></i>fa fa-file-image-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-movie-o"></i>fa fa-file-movie-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-pdf-o"></i>fa fa-file-pdf-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-photo-o"></i>fa fa-file-photo-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-picture-o"></i>fa fa-file-picture-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-powerpoint-o"></i>fa fa-file-powerpoint-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-sound-o"></i>fa fa-file-sound-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-video-o"></i>fa fa-file-video-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-word-o"></i>fa fa-file-word-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-zip-o"></i>fa fa-file-zip-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-ge"></i>fa fa-ge <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-git"></i>fa fa-git
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-git-square"></i>fa fa-git-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-google"></i>fa fa-google
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-graduation-cap"></i>fa fa-graduation-cap
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-hacker-news"></i>fa fa-hacker-news
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-header"></i>fa fa-header
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-history"></i>fa fa-history
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-institution"></i>fa fa-institution <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-joomla"></i>fa fa-joomla
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-jsfiddle"></i>fa fa-jsfiddle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-language"></i>fa fa-language
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-life-bouy"></i>fa fa-life-bouy <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-life-ring"></i>fa fa-life-ring
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-life-saver"></i>fa fa-life-saver <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-mortar-board"></i>fa fa-mortar-board <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-openid"></i>fa fa-openid
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paper-plane"></i>fa fa-paper-plane
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paper-plane-o"></i>fa fa-paper-plane-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paragraph"></i>fa fa-paragraph
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paw"></i>fa fa-paw
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-pied-piper"></i>fa fa-pied-piper
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-pied-piper-alt"></i>fa fa-pied-piper-alt
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-pied-piper-square"></i>fa fa-pied-piper-square <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-qq"></i>fa fa-qq
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-ra"></i>fa fa-ra <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rebel"></i>fa fa-rebel
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-recycle"></i>fa fa-recycle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-reddit"></i>fa fa-reddit
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-reddit-square"></i>fa fa-reddit-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-send"></i>fa fa-send <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-send-o"></i>fa fa-send-o <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-share-alt"></i>fa fa-share-alt
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-share-alt-square"></i>fa fa-share-alt-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-slack"></i>fa fa-slack
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sliders"></i>fa fa-sliders
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-soundcloud"></i>fa fa-soundcloud
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-space-shuttle"></i>fa fa-space-shuttle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-spoon"></i>fa fa-spoon
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-spotify"></i>fa fa-spotify
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-steam"></i>fa fa-steam
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-steam-square"></i>fa fa-steam-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-stumbleupon"></i>fa fa-stumbleupon
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-stumbleupon-circle"></i>fa fa-stumbleupon-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-support"></i>fa fa-support <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-taxi"></i>fa fa-taxi
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tencent-weibo"></i>fa fa-tencent-weibo
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tree"></i>fa fa-tree
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-university"></i>fa fa-university
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-vine"></i>fa fa-vine
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-wechat"></i>fa fa-wechat <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-weixin"></i>fa fa-weixin
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-wordpress"></i>fa fa-wordpress
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-yahoo"></i>fa fa-yahoo
			</div>
		</div>
		<h3>Web Application Icons</h3>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-adjust"></i>fa fa-adjust
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-anchor"></i>fa fa-anchor
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-archive"></i>fa fa-archive
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrows"></i>fa fa-arrows
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrows-h"></i>fa fa-arrows-h
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrows-v"></i>fa fa-arrows-v
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-asterisk"></i>fa fa-asterisk
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-automobile"></i>fa fa-automobile <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-ban"></i>fa fa-ban
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bank"></i>fa fa-bank <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bar-chart-o"></i>fa fa-bar-chart-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-barcode"></i>fa fa-barcode
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bars"></i>fa fa-bars
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-beer"></i>fa fa-beer
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bell"></i>fa fa-bell
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bell-o"></i>fa fa-bell-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bolt"></i>fa fa-bolt
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bomb"></i>fa fa-bomb
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-book"></i>fa fa-book
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bookmark"></i>fa fa-bookmark
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bookmark-o"></i>fa fa-bookmark-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-briefcase"></i>fa fa-briefcase
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bug"></i>fa fa-bug
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-building"></i>fa fa-building
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-building-o"></i>fa fa-building-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bullhorn"></i>fa fa-bullhorn
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bullseye"></i>fa fa-bullseye
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cab"></i>fa fa-cab <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-calendar"></i>fa fa-calendar
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-calendar-o"></i>fa fa-calendar-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-camera"></i>fa fa-camera
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-camera-retro"></i>fa fa-camera-retro
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-car"></i>fa fa-car
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-square-o-down"></i>fa fa-caret-square-o-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-square-o-left"></i>fa fa-caret-square-o-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-square-o-right"></i>fa fa-caret-square-o-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-square-o-up"></i>fa fa-caret-square-o-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-certificate"></i>fa fa-certificate
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-check"></i>fa fa-check
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-check-circle"></i>fa fa-check-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-check-circle-o"></i>fa fa-check-circle-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-check-square"></i>fa fa-check-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-check-square-o"></i>fa fa-check-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-child"></i>fa fa-child
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle"></i>fa fa-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle-o"></i>fa fa-circle-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle-o-notch"></i>fa fa-circle-o-notch
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle-thin"></i>fa fa-circle-thin
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-clock-o"></i>fa fa-clock-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cloud"></i>fa fa-cloud
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cloud-download"></i>fa fa-cloud-download
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cloud-upload"></i>fa fa-cloud-upload
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-code"></i>fa fa-code
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-code-fork"></i>fa fa-code-fork
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-coffee"></i>fa fa-coffee
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cog"></i>fa fa-cog
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cogs"></i>fa fa-cogs
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-comment"></i>fa fa-comment
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-comment-o"></i>fa fa-comment-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-comments"></i>fa fa-comments
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-comments-o"></i>fa fa-comments-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-compass"></i>fa fa-compass
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-credit-card"></i>fa fa-credit-card
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-crop"></i>fa fa-crop
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-crosshairs"></i>fa fa-crosshairs
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cube"></i>fa fa-cube
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cubes"></i>fa fa-cubes
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cutlery"></i>fa fa-cutlery
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-dashboard"></i>fa fa-dashboard <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-database"></i>fa fa-database
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-desktop"></i>fa fa-desktop
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-dot-circle-o"></i>fa fa-dot-circle-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-download"></i>fa fa-download
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-edit"></i>fa fa-edit <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-ellipsis-h"></i>fa fa-ellipsis-h
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-ellipsis-v"></i>fa fa-ellipsis-v
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-envelope"></i>fa fa-envelope
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-envelope-o"></i>fa fa-envelope-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-envelope-square"></i>fa fa-envelope-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-eraser"></i>fa fa-eraser
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-exchange"></i>fa fa-exchange
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-exclamation"></i>fa fa-exclamation
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-exclamation-circle"></i>fa fa-exclamation-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-exclamation-triangle"></i>fa fa-exclamation-triangle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-external-link"></i>fa fa-external-link
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-external-link-square"></i>fa fa-external-link-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-eye"></i>fa fa-eye
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-eye-slash"></i>fa fa-eye-slash
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-fax"></i>fa fa-fax
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-female"></i>fa fa-female
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-fighter-jet"></i>fa fa-fighter-jet
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-archive-o"></i>fa fa-file-archive-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-audio-o"></i>fa fa-file-audio-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-code-o"></i>fa fa-file-code-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-excel-o"></i>fa fa-file-excel-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-image-o"></i>fa fa-file-image-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-movie-o"></i>fa fa-file-movie-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-pdf-o"></i>fa fa-file-pdf-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-photo-o"></i>fa fa-file-photo-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-picture-o"></i>fa fa-file-picture-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-powerpoint-o"></i>fa fa-file-powerpoint-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-sound-o"></i>fa fa-file-sound-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-video-o"></i>fa fa-file-video-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-word-o"></i>fa fa-file-word-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-zip-o"></i>fa fa-file-zip-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-film"></i>fa fa-film
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-filter"></i>fa fa-filter
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-fire"></i>fa fa-fire
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-fire-extinguisher"></i>fa fa-fire-extinguisher
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-flag"></i>fa fa-flag
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-flag-checkered"></i>fa fa-flag-checkered
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-flag-o"></i>fa fa-flag-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-flash"></i>fa fa-flash <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-flask"></i>fa fa-flask
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-folder"></i>fa fa-folder
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-folder-o"></i>fa fa-folder-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-folder-open"></i>fa fa-folder-open
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-folder-open-o"></i>fa fa-folder-open-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-frown-o"></i>fa fa-frown-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-gamepad"></i>fa fa-gamepad
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-gavel"></i>fa fa-gavel
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-gear"></i>fa fa-gear <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-gears"></i>fa fa-gears <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-gift"></i>fa fa-gift
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-glass"></i>fa fa-glass
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-globe"></i>fa fa-globe
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-graduation-cap"></i>fa fa-graduation-cap
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-group"></i>fa fa-group <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-hdd-o"></i>fa fa-hdd-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-headphones"></i>fa fa-headphones
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-heart"></i>fa fa-heart
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-heart-o"></i>fa fa-heart-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-history"></i>fa fa-history
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-home"></i>fa fa-home
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-image"></i>fa fa-image <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-inbox"></i>fa fa-inbox
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-info"></i>fa fa-info
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-info-circle"></i>fa fa-info-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-institution"></i>fa fa-institution <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-key"></i>fa fa-key
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-keyboard-o"></i>fa fa-keyboard-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-language"></i>fa fa-language
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-laptop"></i>fa fa-laptop
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-leaf"></i>fa fa-leaf
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-legal"></i>fa fa-legal <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-lemon-o"></i>fa fa-lemon-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-level-down"></i>fa fa-level-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-level-up"></i>fa fa-level-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-life-bouy"></i>fa fa-life-bouy <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-life-ring"></i>fa fa-life-ring
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-life-saver"></i>fa fa-life-saver <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-lightbulb-o"></i>fa fa-lightbulb-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-location-arrow"></i>fa fa-location-arrow
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-lock"></i>fa fa-lock
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-magic"></i>fa fa-magic
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-magnet"></i>fa fa-magnet
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-mail-forward"></i>fa fa-mail-forward <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-mail-reply"></i>fa fa-mail-reply <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-mail-reply-all"></i>fa fa-mail-reply-all <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-male"></i>fa fa-male
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-map-marker"></i>fa fa-map-marker
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-meh-o"></i>fa fa-meh-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-microphone"></i>fa fa-microphone
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-microphone-slash"></i>fa fa-microphone-slash
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-minus"></i>fa fa-minus
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-minus-circle"></i>fa fa-minus-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-minus-square"></i>fa fa-minus-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-minus-square-o"></i>fa fa-minus-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-mobile"></i>fa fa-mobile
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-mobile-phone"></i>fa fa-mobile-phone <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-money"></i>fa fa-money
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-moon-o"></i>fa fa-moon-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-mortar-board"></i>fa fa-mortar-board <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-music"></i>fa fa-music
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-navicon"></i>fa fa-navicon <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paper-plane"></i>fa fa-paper-plane
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paper-plane-o"></i>fa fa-paper-plane-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paw"></i>fa fa-paw
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-pencil"></i>fa fa-pencil
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-pencil-square"></i>fa fa-pencil-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-pencil-square-o"></i>fa fa-pencil-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-phone"></i>fa fa-phone
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-phone-square"></i>fa fa-phone-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-photo"></i>fa fa-photo <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-picture-o"></i>fa fa-picture-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-plane"></i>fa fa-plane
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-plus"></i>fa fa-plus
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-plus-circle"></i>fa fa-plus-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-plus-square"></i>fa fa-plus-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-plus-square-o"></i>fa fa-plus-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-power-off"></i>fa fa-power-off
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-print"></i>fa fa-print
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-puzzle-piece"></i>fa fa-puzzle-piece
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-qrcode"></i>fa fa-qrcode
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-question"></i>fa fa-question
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-question-circle"></i>fa fa-question-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-quote-left"></i>fa fa-quote-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-quote-right"></i>fa fa-quote-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-random"></i>fa fa-random
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-recycle"></i>fa fa-recycle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-refresh"></i>fa fa-refresh
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-reorder"></i>fa fa-reorder <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-reply"></i>fa fa-reply
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-reply-all"></i>fa fa-reply-all
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-retweet"></i>fa fa-retweet
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-road"></i>fa fa-road
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rocket"></i>fa fa-rocket
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rss"></i>fa fa-rss
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rss-square"></i>fa fa-rss-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-search"></i>fa fa-search
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-search-minus"></i>fa fa-search-minus
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-search-plus"></i>fa fa-search-plus
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-send"></i>fa fa-send <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-send-o"></i>fa fa-send-o <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-share"></i>fa fa-share
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-share-alt"></i>fa fa-share-alt
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-share-alt-square"></i>fa fa-share-alt-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-share-square"></i>fa fa-share-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-share-square-o"></i>fa fa-share-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-shield"></i>fa fa-shield
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-shopping-cart"></i>fa fa-shopping-cart
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sign-in"></i>fa fa-sign-in
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sign-out"></i>fa fa-sign-out
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-signal"></i>fa fa-signal
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sitemap"></i>fa fa-sitemap
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sliders"></i>fa fa-sliders
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-smile-o"></i>fa fa-smile-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort"></i>fa fa-sort
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-alpha-asc"></i>fa fa-sort-alpha-asc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-alpha-desc"></i>fa fa-sort-alpha-desc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-amount-asc"></i>fa fa-sort-amount-asc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-amount-desc"></i>fa fa-sort-amount-desc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-asc"></i>fa fa-sort-asc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-desc"></i>fa fa-sort-desc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-down"></i>fa fa-sort-down <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-numeric-asc"></i>fa fa-sort-numeric-asc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-numeric-desc"></i>fa fa-sort-numeric-desc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sort-up"></i>fa fa-sort-up <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-space-shuttle"></i>fa fa-space-shuttle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-spinner"></i>fa fa-spinner
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-spoon"></i>fa fa-spoon
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-square"></i>fa fa-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-square-o"></i>fa fa-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-star"></i>fa fa-star
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-star-half"></i>fa fa-star-half
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-star-half-empty"></i>fa fa-star-half-empty <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-star-half-full"></i>fa fa-star-half-full <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-star-half-o"></i>fa fa-star-half-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-star-o"></i>fa fa-star-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-suitcase"></i>fa fa-suitcase
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-sun-o"></i>fa fa-sun-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-support"></i>fa fa-support <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tablet"></i>fa fa-tablet
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tachometer"></i>fa fa-tachometer
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tag"></i>fa fa-tag
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tags"></i>fa fa-tags
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tasks"></i>fa fa-tasks
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-taxi"></i>fa fa-taxi
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-terminal"></i>fa fa-terminal
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-thumb-tack"></i>fa fa-thumb-tack
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-thumbs-down"></i>fa fa-thumbs-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-thumbs-o-down"></i>fa fa-thumbs-o-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-thumbs-o-up"></i>fa fa-thumbs-o-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-thumbs-up"></i>fa fa-thumbs-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-ticket"></i>fa fa-ticket
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-times"></i>fa fa-times
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-times-circle"></i>fa fa-times-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-times-circle-o"></i>fa fa-times-circle-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tint"></i>fa fa-tint
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-toggle-down"></i>fa fa-toggle-down <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-toggle-left"></i>fa fa-toggle-left <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-toggle-right"></i>fa fa-toggle-right <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-toggle-up"></i>fa fa-toggle-up <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-trash-o"></i>fa fa-trash-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-tree"></i>fa fa-tree
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-trophy"></i>fa fa-trophy
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-truck"></i>fa fa-truck
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-umbrella"></i>fa fa-umbrella
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-university"></i>fa fa-university
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-unlock"></i>fa fa-unlock
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-unlock-alt"></i>fa fa-unlock-alt
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-unsorted"></i>fa fa-unsorted <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-upload"></i>fa fa-upload
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-user"></i>fa fa-user
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-users"></i>fa fa-users
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-video-camera"></i>fa fa-video-camera
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-volume-down"></i>fa fa-volume-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-volume-off"></i>fa fa-volume-off
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-volume-up"></i>fa fa-volume-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-warning"></i>fa fa-warning <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-wheelchair"></i>fa fa-wheelchair
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-wrench"></i>fa fa-wrench
			</div>
		</div>
		<h3>File Type Icons</h3>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file"></i>fa fa-file
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-archive-o"></i>fa fa-file-archive-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-audio-o"></i>fa fa-file-audio-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-code-o"></i>fa fa-file-code-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-excel-o"></i>fa fa-file-excel-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-image-o"></i>fa fa-file-image-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-movie-o"></i>fa fa-file-movie-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-o"></i>fa fa-file-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-pdf-o"></i>fa fa-file-pdf-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-photo-o"></i>fa fa-file-photo-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-picture-o"></i>fa fa-file-picture-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-powerpoint-o"></i>fa fa-file-powerpoint-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-sound-o"></i>fa fa-file-sound-o <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-text"></i>fa fa-file-text
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-text-o"></i>fa fa-file-text-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-video-o"></i>fa fa-file-video-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-word-o"></i>fa fa-file-word-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-zip-o"></i>fa fa-file-zip-o <span
					class="text-muted">(alias)</span>
			</div>
		</div>
		<h3>Spinner Icons</h3>
		<div class="alert alert-success">
			<ul class="fa-ul">
				<li><i class="fa fa-info-circle fa-lg fa-li"></i> These icons
					work great with the <code>fa-spin</code> class. Check out the
					spinning icons example.</li>
			</ul>
		</div>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle-o-notch"></i>fa fa-circle-o-notch
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cog"></i>fa fa-cog
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-gear"></i>fa fa-gear <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-refresh"></i>fa fa-refresh
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-spinner"></i>fa fa-spinner
			</div>
		</div>
		<h3>Form Control Icons</h3>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-check-square"></i>fa fa-check-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-check-square-o"></i>fa fa-check-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle"></i>fa fa-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-circle-o"></i>fa fa-circle-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-dot-circle-o"></i>fa fa-dot-circle-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-minus-square"></i>fa fa-minus-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-minus-square-o"></i>fa fa-minus-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-plus-square"></i>fa fa-plus-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-plus-square-o"></i>fa fa-plus-square-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-square"></i>fa fa-square
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-square-o"></i>fa fa-square-o
			</div>
		</div>
		<h3>Currency Icons</h3>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bitcoin"></i>fa fa-bitcoin <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-btc"></i>fa fa-btc
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cny"></i>fa fa-cny <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-dollar"></i>fa fa-dollar <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-eur"></i>fa fa-eur
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-euro"></i>fa fa-euro <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-gbp"></i>fa fa-gbp
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-inr"></i>fa fa-inr
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-jpy"></i>fa fa-jpy
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-krw"></i>fa fa-krw
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-money"></i>fa fa-money
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rmb"></i>fa fa-rmb <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rouble"></i>fa fa-rouble <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rub"></i>fa fa-rub
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-ruble"></i>fa fa-ruble <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rupee"></i>fa fa-rupee <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-try"></i>fa fa-try
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-turkish-lira"></i>fa fa-turkish-lira <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-usd"></i>fa fa-usd
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-won"></i>fa fa-won <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-yen"></i>fa fa-yen <span class="text-muted">(alias)</span>
			</div>
		</div>
		<h3>Text Editor Icons</h3>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-align-center"></i>fa fa-align-center
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-align-justify"></i>fa fa-align-justify
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-align-left"></i>fa fa-align-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-align-right"></i>fa fa-align-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-bold"></i>fa fa-bold
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chain"></i>fa fa-chain <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chain-broken"></i>fa fa-chain-broken
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-clipboard"></i>fa fa-clipboard
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-columns"></i>fa fa-columns
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-copy"></i>fa fa-copy <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-cut"></i>fa fa-cut <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-dedent"></i>fa fa-dedent <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-eraser"></i>fa fa-eraser
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file"></i>fa fa-file
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-o"></i>fa fa-file-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-text"></i>fa fa-file-text
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-file-text-o"></i>fa fa-file-text-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-files-o"></i>fa fa-files-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-floppy-o"></i>fa fa-floppy-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-font"></i>fa fa-font
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-header"></i>fa fa-header
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-indent"></i>fa fa-indent
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-italic"></i>fa fa-italic
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-link"></i>fa fa-link
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-list"></i>fa fa-list
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-list-alt"></i>fa fa-list-alt
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-list-ol"></i>fa fa-list-ol
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-list-ul"></i>fa fa-list-ul
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-outdent"></i>fa fa-outdent
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paperclip"></i>fa fa-paperclip
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paragraph"></i>fa fa-paragraph
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-paste"></i>fa fa-paste <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-repeat"></i>fa fa-repeat
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rotate-left"></i>fa fa-rotate-left <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-rotate-right"></i>fa fa-rotate-right <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-save"></i>fa fa-save <span class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-scissors"></i>fa fa-scissors
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-strikethrough"></i>fa fa-strikethrough
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-subscript"></i>fa fa-subscript
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-superscript"></i>fa fa-superscript
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-table"></i>fa fa-table
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-text-height"></i>fa fa-text-height
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-text-width"></i>fa fa-text-width
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-th"></i>fa fa-th
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-th-large"></i>fa fa-th-large
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-th-list"></i>fa fa-th-list
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-underline"></i>fa fa-underline
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-undo"></i>fa fa-undo
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-unlink"></i>fa fa-unlink <span class="text-muted">(alias)</span>
			</div>
		</div>
		<h3>Directional Icons</h3>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-angle-double-down"></i>fa fa-angle-double-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-angle-double-left"></i>fa fa-angle-double-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-angle-double-right"></i>fa fa-angle-double-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-angle-double-up"></i>fa fa-angle-double-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-angle-down"></i>fa fa-angle-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-angle-left"></i>fa fa-angle-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-angle-right"></i>fa fa-angle-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-angle-up"></i>fa fa-angle-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-circle-down"></i>fa fa-arrow-circle-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-circle-left"></i>fa fa-arrow-circle-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-circle-o-down"></i>fa fa-arrow-circle-o-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-circle-o-left"></i>fa fa-arrow-circle-o-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-circle-o-right"></i>fa fa-arrow-circle-o-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-circle-o-up"></i>fa fa-arrow-circle-o-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-circle-right"></i>fa fa-arrow-circle-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-circle-up"></i>fa fa-arrow-circle-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-down"></i>fa fa-arrow-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-left"></i>fa fa-arrow-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-right"></i>fa fa-arrow-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrow-up"></i>fa fa-arrow-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrows"></i>fa fa-arrows
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrows-alt"></i>fa fa-arrows-alt
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrows-h"></i>fa fa-arrows-h
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrows-v"></i>fa fa-arrows-v
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-down"></i>fa fa-caret-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-left"></i>fa fa-caret-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-right"></i>fa fa-caret-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-square-o-down"></i>fa fa-caret-square-o-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-square-o-left"></i>fa fa-caret-square-o-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-square-o-right"></i>fa fa-caret-square-o-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-square-o-up"></i>fa fa-caret-square-o-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-caret-up"></i>fa fa-caret-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chevron-circle-down"></i>fa fa-chevron-circle-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chevron-circle-left"></i>fa fa-chevron-circle-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chevron-circle-right"></i>fa fa-chevron-circle-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chevron-circle-up"></i>fa fa-chevron-circle-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chevron-down"></i>fa fa-chevron-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chevron-left"></i>fa fa-chevron-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chevron-right"></i>fa fa-chevron-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-chevron-up"></i>fa fa-chevron-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-hand-o-down"></i>fa fa-hand-o-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-hand-o-left"></i>fa fa-hand-o-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-hand-o-right"></i>fa fa-hand-o-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-hand-o-up"></i>fa fa-hand-o-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-long-arrow-down"></i>fa fa-long-arrow-down
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-long-arrow-left"></i>fa fa-long-arrow-left
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-long-arrow-right"></i>fa fa-long-arrow-right
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-long-arrow-up"></i>fa fa-long-arrow-up
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-toggle-down"></i>fa fa-toggle-down <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-toggle-left"></i>fa fa-toggle-left <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-toggle-right"></i>fa fa-toggle-right <span
					class="text-muted">(alias)</span>
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-toggle-up"></i>fa fa-toggle-up <span
					class="text-muted">(alias)</span>
			</div>
		</div>
		<h3>Video Player Icons</h3>
		<div class="row margin-bottom-20">
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-arrows-alt"></i>fa fa-arrows-alt
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-backward"></i>fa fa-backward
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-compress"></i>fa fa-compress
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-eject"></i>fa fa-eject
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-expand"></i>fa fa-expand
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-fast-backward"></i>fa fa-fast-backward
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-fast-forward"></i>fa fa-fast-forward
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-forward"></i>fa fa-forward
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-pause"></i>fa fa-pause
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-play"></i>fa fa-play
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-play-circle"></i>fa fa-play-circle
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-play-circle-o"></i>fa fa-play-circle-o
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-step-backward"></i>fa fa-step-backward
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-step-forward"></i>fa fa-step-forward
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-stop"></i>fa fa-stop
			</div>
			<div class="fa-item col-md-3 col-sm-4">
				<i class="fa fa-youtube-play"></i>fa fa-youtube-play
			</div>
		</div>
		<h3>Brand Icons</h3>
		<div class="alert alert-success">
			<ul class="margin-bottom-none padding-left-lg">
				<li>All brand icons are trademarks of their respective owners.
				</li>
				<li>The use of these trademarks does not indicate endorsement
					of the trademark holder by Font Awesome, nor vice versa.</li>
			</ul>
			<div class="alert alert-warning">
				<h4>
					<i class="fa fa-warning"></i> Warning!
				</h4>
				Apparently, Adblock Plus can remove Font Awesome brand icons with
				their "Remove Social Media Buttons" setting. We will not use hacks
				to force them to display. Please report an issue with Adblock Plus
				if you believe this to be an error. To work around this, you'll need
				to modify the social icon class names.
			</div>
			<div class="row margin-bottom-20">
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-adn"></i>fa fa-adn
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-android"></i>fa fa-android
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-apple"></i>fa fa-apple
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-behance"></i>fa fa-behance
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-behance-square"></i>fa fa-behance-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-bitbucket"></i>fa fa-bitbucket
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-bitbucket-square"></i>fa fa-bitbucket-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-bitcoin"></i>fa fa-bitcoin <span class="text-muted">(alias)</span>
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-btc"></i>fa fa-btc
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-codepen"></i>fa fa-codepen
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-css3"></i>fa fa-css3
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-delicious"></i>fa fa-delicious
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-deviantart"></i>fa fa-deviantart
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-digg"></i>fa fa-digg
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-dribbble"></i>fa fa-dribbble
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-dropbox"></i>fa fa-dropbox
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-drupal"></i>fa fa-drupal
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-empire"></i>fa fa-empire
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-facebook"></i>fa fa-facebook
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-facebook-square"></i>fa fa-facebook-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-flickr"></i>fa fa-flickr
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-foursquare"></i>fa fa-foursquare
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-ge"></i>fa fa-ge <span class="text-muted">(alias)</span>
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-git"></i>fa fa-git
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-git-square"></i>fa fa-git-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-github"></i>fa fa-github
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-github-alt"></i>fa fa-github-alt
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-github-square"></i>fa fa-github-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-gittip"></i>fa fa-gittip
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-google"></i>fa fa-google
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-google-plus"></i>fa fa-google-plus
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-google-plus-square"></i>fa fa-google-plus-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-hacker-news"></i>fa fa-hacker-news
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-html5"></i>fa fa-html5
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-instagram"></i>fa fa-instagram
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-joomla"></i>fa fa-joomla
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-jsfiddle"></i>fa fa-jsfiddle
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-linkedin"></i>fa fa-linkedin
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-linkedin-square"></i>fa fa-linkedin-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-linux"></i>fa fa-linux
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-maxcdn"></i>fa fa-maxcdn
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-openid"></i>fa fa-openid
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-pagelines"></i>fa fa-pagelines
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-pied-piper"></i>fa fa-pied-piper
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-pied-piper-alt"></i>fa fa-pied-piper-alt
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-pied-piper-square"></i>fa fa-pied-piper-square <span
						class="text-muted">(alias)</span>
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-pinterest"></i>fa fa-pinterest
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-pinterest-square"></i>fa fa-pinterest-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-qq"></i>fa fa-qq
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-ra"></i>fa fa-ra <span class="text-muted">(alias)</span>
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-rebel"></i>fa fa-rebel
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-reddit"></i>fa fa-reddit
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-reddit-square"></i>fa fa-reddit-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-renren"></i>fa fa-renren
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-share-alt"></i>fa fa-share-alt
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-share-alt-square"></i>fa fa-share-alt-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-skype"></i>fa fa-skype
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-slack"></i>fa fa-slack
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-soundcloud"></i>fa fa-soundcloud
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-spotify"></i>fa fa-spotify
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-stack-exchange"></i>fa fa-stack-exchange
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-stack-overflow"></i>fa fa-stack-overflow
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-steam"></i>fa fa-steam
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-steam-square"></i>fa fa-steam-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-stumbleupon"></i>fa fa-stumbleupon
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-stumbleupon-circle"></i>fa fa-stumbleupon-circle
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-tencent-weibo"></i>fa fa-tencent-weibo
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-trello"></i>fa fa-trello
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-tumblr"></i>fa fa-tumblr
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-tumblr-square"></i>fa fa-tumblr-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-twitter"></i>fa fa-twitter
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-twitter-square"></i>fa fa-twitter-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-vimeo-square"></i>fa fa-vimeo-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-vine"></i>fa fa-vine
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-vk"></i>fa fa-vk
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-wechat"></i>fa fa-wechat <span class="text-muted">(alias)</span>
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-weibo"></i>fa fa-weibo
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-weixin"></i>fa fa-weixin
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-windows"></i>fa fa-windows
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-wordpress"></i>fa fa-wordpress
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-xing"></i>fa fa-xing
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-xing-square"></i>fa fa-xing-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-yahoo"></i>fa fa-yahoo
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-youtube"></i>fa fa-youtube
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-youtube-play"></i>fa fa-youtube-play
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-youtube-square"></i>fa fa-youtube-square
				</div>
			</div>
			<h3>Medical Icons</h3>
			<div class="row margin-bottom-20">
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-ambulance"></i>fa fa-ambulance
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-h-square"></i>fa fa-h-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-hospital-o"></i>fa fa-hospital-o
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-medkit"></i>fa fa-medkit
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-plus-square"></i>fa fa-plus-square
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-stethoscope"></i>fa fa-stethoscope
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-user-md"></i>fa fa-user-md
				</div>
				<div class="fa-item col-md-3 col-sm-4">
					<i class="fa fa-wheelchair"></i>fa fa-wheelchair
				</div>
			</div>
		</div>
		<div class="note note-success">
			<p>
				Includes 200 glyphs in font format from the Glyphicon Halflings set.
				<a href="http://glyphicons.com/" target="_blank"> Glyphicons </a>
				Halflings are normally not available for free, but their creator has
				made them available for Bootstrap free of cost.
			</p>
			For more info check out <a
				href="http://getbootstrap.com/components/#glyphicons"
				target="_blank">http://getbootstrap.com/components/#glyphicons</a>
		</div>
		<div class="glyphicons-demo">
			<ul class="bs-glyphicons bs-glyphicons-list">
				<li><span class="glyphicon glyphicon-asterisk"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-asterisk </span></li>
				<li><span class="glyphicon glyphicon-plus"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-plus </span></li>
				<li><span class="glyphicon glyphicon-euro"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-euro </span></li>
				<li><span class="glyphicon glyphicon-minus"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-minus </span></li>
				<li><span class="glyphicon glyphicon-cloud"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-cloud </span></li>
				<li><span class="glyphicon glyphicon-envelope"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-envelope </span></li>
				<li><span class="glyphicon glyphicon-pencil"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-pencil </span></li>
				<li><span class="glyphicon glyphicon-glass"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-glass </span></li>
				<li><span class="glyphicon glyphicon-music"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-music </span></li>
				<li><span class="glyphicon glyphicon-search"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-search </span></li>
				<li><span class="glyphicon glyphicon-heart"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-heart </span></li>
				<li><span class="glyphicon glyphicon-star"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-star </span></li>
				<li><span class="glyphicon glyphicon-star-empty"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-star-empty </span></li>
				<li><span class="glyphicon glyphicon-user"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-user </span></li>
				<li><span class="glyphicon glyphicon-film"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-film </span></li>
				<li><span class="glyphicon glyphicon-th-large"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-th-large </span></li>
				<li><span class="glyphicon glyphicon-th"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-th </span></li>
				<li><span class="glyphicon glyphicon-th-list"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-th-list </span></li>
				<li><span class="glyphicon glyphicon-ok"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-ok </span></li>
				<li><span class="glyphicon glyphicon-remove"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-remove </span></li>
				<li><span class="glyphicon glyphicon-zoom-in"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-zoom-in </span></li>
				<li><span class="glyphicon glyphicon-zoom-out"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-zoom-out </span></li>
				<li><span class="glyphicon glyphicon-off"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-off </span></li>
				<li><span class="glyphicon glyphicon-signal"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-signal </span></li>
				<li><span class="glyphicon glyphicon-cog"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-cog </span></li>
				<li><span class="glyphicon glyphicon-trash"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-trash </span></li>
				<li><span class="glyphicon glyphicon-home"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-home </span></li>
				<li><span class="glyphicon glyphicon-file"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-file </span></li>
				<li><span class="glyphicon glyphicon-time"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-time </span></li>
				<li><span class="glyphicon glyphicon-road"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-road </span></li>
				<li><span class="glyphicon glyphicon-download-alt"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-download-alt
				</span></li>
				<li><span class="glyphicon glyphicon-download"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-download </span></li>
				<li><span class="glyphicon glyphicon-upload"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-upload </span></li>
				<li><span class="glyphicon glyphicon-inbox"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-inbox </span></li>
				<li><span class="glyphicon glyphicon-play-circle"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-play-circle
				</span></li>
				<li><span class="glyphicon glyphicon-repeat"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-repeat </span></li>
				<li><span class="glyphicon glyphicon-refresh"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-refresh </span></li>
				<li><span class="glyphicon glyphicon-list-alt"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-list-alt </span></li>
				<li><span class="glyphicon glyphicon-lock"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-lock </span></li>
				<li><span class="glyphicon glyphicon-flag"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-flag </span></li>
				<li><span class="glyphicon glyphicon-headphones"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-headphones </span></li>
				<li><span class="glyphicon glyphicon-volume-off"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-volume-off </span></li>
				<li><span class="glyphicon glyphicon-volume-down"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-volume-down
				</span></li>
				<li><span class="glyphicon glyphicon-volume-up"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-volume-up </span></li>
				<li><span class="glyphicon glyphicon-qrcode"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-qrcode </span></li>
				<li><span class="glyphicon glyphicon-barcode"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-barcode </span></li>
				<li><span class="glyphicon glyphicon-tag"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-tag </span></li>
				<li><span class="glyphicon glyphicon-tags"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-tags </span></li>
				<li><span class="glyphicon glyphicon-book"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-book </span></li>
				<li><span class="glyphicon glyphicon-bookmark"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-bookmark </span></li>
				<li><span class="glyphicon glyphicon-print"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-print </span></li>
				<li><span class="glyphicon glyphicon-camera"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-camera </span></li>
				<li><span class="glyphicon glyphicon-font"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-font </span></li>
				<li><span class="glyphicon glyphicon-bold"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-bold </span></li>
				<li><span class="glyphicon glyphicon-italic"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-italic </span></li>
				<li><span class="glyphicon glyphicon-text-height"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-text-height
				</span></li>
				<li><span class="glyphicon glyphicon-text-width"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-text-width </span></li>
				<li><span class="glyphicon glyphicon-align-left"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-align-left </span></li>
				<li><span class="glyphicon glyphicon-align-center"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-align-center
				</span></li>
				<li><span class="glyphicon glyphicon-align-right"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-align-right
				</span></li>
				<li><span class="glyphicon glyphicon-align-justify"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-align-justify </span></li>
				<li><span class="glyphicon glyphicon-list"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-list </span></li>
				<li><span class="glyphicon glyphicon-indent-left"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-indent-left
				</span></li>
				<li><span class="glyphicon glyphicon-indent-right"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-indent-right
				</span></li>
				<li><span class="glyphicon glyphicon-facetime-video"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-facetime-video </span></li>
				<li><span class="glyphicon glyphicon-picture"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-picture </span></li>
				<li><span class="glyphicon glyphicon-map-marker"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-map-marker </span></li>
				<li><span class="glyphicon glyphicon-adjust"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-adjust </span></li>
				<li><span class="glyphicon glyphicon-tint"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-tint </span></li>
				<li><span class="glyphicon glyphicon-edit"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-edit </span></li>
				<li><span class="glyphicon glyphicon-share"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-share </span></li>
				<li><span class="glyphicon glyphicon-check"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-check </span></li>
				<li><span class="glyphicon glyphicon-move"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-move </span></li>
				<li><span class="glyphicon glyphicon-step-backward"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-step-backward </span></li>
				<li><span class="glyphicon glyphicon-fast-backward"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-fast-backward </span></li>
				<li><span class="glyphicon glyphicon-backward"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-backward </span></li>
				<li><span class="glyphicon glyphicon-play"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-play </span></li>
				<li><span class="glyphicon glyphicon-pause"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-pause </span></li>
				<li><span class="glyphicon glyphicon-stop"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-stop </span></li>
				<li><span class="glyphicon glyphicon-forward"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-forward </span></li>
				<li><span class="glyphicon glyphicon-fast-forward"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-fast-forward
				</span></li>
				<li><span class="glyphicon glyphicon-step-forward"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-step-forward
				</span></li>
				<li><span class="glyphicon glyphicon-eject"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-eject </span></li>
				<li><span class="glyphicon glyphicon-chevron-left"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-chevron-left
				</span></li>
				<li><span class="glyphicon glyphicon-chevron-right"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-chevron-right </span></li>
				<li><span class="glyphicon glyphicon-plus-sign"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-plus-sign </span></li>
				<li><span class="glyphicon glyphicon-minus-sign"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-minus-sign </span></li>
				<li><span class="glyphicon glyphicon-remove-sign"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-remove-sign
				</span></li>
				<li><span class="glyphicon glyphicon-ok-sign"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-ok-sign </span></li>
				<li><span class="glyphicon glyphicon-question-sign"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-question-sign </span></li>
				<li><span class="glyphicon glyphicon-info-sign"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-info-sign </span></li>
				<li><span class="glyphicon glyphicon-screenshot"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-screenshot </span></li>
				<li><span class="glyphicon glyphicon-remove-circle"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-remove-circle </span></li>
				<li><span class="glyphicon glyphicon-ok-circle"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-ok-circle </span></li>
				<li><span class="glyphicon glyphicon-ban-circle"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-ban-circle </span></li>
				<li><span class="glyphicon glyphicon-arrow-left"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-arrow-left </span></li>
				<li><span class="glyphicon glyphicon-arrow-right"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-arrow-right
				</span></li>
				<li><span class="glyphicon glyphicon-arrow-up"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-arrow-up </span></li>
				<li><span class="glyphicon glyphicon-arrow-down"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-arrow-down </span></li>
				<li><span class="glyphicon glyphicon-share-alt"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-share-alt </span></li>
				<li><span class="glyphicon glyphicon-resize-full"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-resize-full
				</span></li>
				<li><span class="glyphicon glyphicon-resize-small"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-resize-small
				</span></li>
				<li><span class="glyphicon glyphicon-exclamation-sign">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-exclamation-sign </span></li>
				<li><span class="glyphicon glyphicon-gift"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-gift </span></li>
				<li><span class="glyphicon glyphicon-leaf"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-leaf </span></li>
				<li><span class="glyphicon glyphicon-fire"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-fire </span></li>
				<li><span class="glyphicon glyphicon-eye-open"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-eye-open </span></li>
				<li><span class="glyphicon glyphicon-eye-close"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-eye-close </span></li>
				<li><span class="glyphicon glyphicon-warning-sign"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-warning-sign
				</span></li>
				<li><span class="glyphicon glyphicon-plane"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-plane </span></li>
				<li><span class="glyphicon glyphicon-calendar"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-calendar </span></li>
				<li><span class="glyphicon glyphicon-random"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-random </span></li>
				<li><span class="glyphicon glyphicon-comment"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-comment </span></li>
				<li><span class="glyphicon glyphicon-magnet"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-magnet </span></li>
				<li><span class="glyphicon glyphicon-chevron-up"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-chevron-up </span></li>
				<li><span class="glyphicon glyphicon-chevron-down"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-chevron-down
				</span></li>
				<li><span class="glyphicon glyphicon-retweet"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-retweet </span></li>
				<li><span class="glyphicon glyphicon-shopping-cart"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-shopping-cart </span></li>
				<li><span class="glyphicon glyphicon-folder-close"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-folder-close
				</span></li>
				<li><span class="glyphicon glyphicon-folder-open"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-folder-open
				</span></li>
				<li><span class="glyphicon glyphicon-resize-vertical"> </span>
					<span class="bs-glyphicon-class"> glyphicon
						glyphicon-resize-vertical </span></li>
				<li><span class="glyphicon glyphicon-resize-horizontal">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-resize-horizontal </span></li>
				<li><span class="glyphicon glyphicon-hdd"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-hdd </span></li>
				<li><span class="glyphicon glyphicon-bullhorn"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-bullhorn </span></li>
				<li><span class="glyphicon glyphicon-bell"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-bell </span></li>
				<li><span class="glyphicon glyphicon-certificate"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-certificate
				</span></li>
				<li><span class="glyphicon glyphicon-thumbs-up"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-thumbs-up </span></li>
				<li><span class="glyphicon glyphicon-thumbs-down"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-thumbs-down
				</span></li>
				<li><span class="glyphicon glyphicon-hand-right"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-hand-right </span></li>
				<li><span class="glyphicon glyphicon-hand-left"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-hand-left </span></li>
				<li><span class="glyphicon glyphicon-hand-up"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-hand-up </span></li>
				<li><span class="glyphicon glyphicon-hand-down"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-hand-down </span></li>
				<li><span class="glyphicon glyphicon-circle-arrow-right">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-circle-arrow-right </span></li>
				<li><span class="glyphicon glyphicon-circle-arrow-left">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-circle-arrow-left </span></li>
				<li><span class="glyphicon glyphicon-circle-arrow-up"> </span>
					<span class="bs-glyphicon-class"> glyphicon
						glyphicon-circle-arrow-up </span></li>
				<li><span class="glyphicon glyphicon-circle-arrow-down">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-circle-arrow-down </span></li>
				<li><span class="glyphicon glyphicon-globe"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-globe </span></li>
				<li><span class="glyphicon glyphicon-wrench"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-wrench </span></li>
				<li><span class="glyphicon glyphicon-tasks"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-tasks </span></li>
				<li><span class="glyphicon glyphicon-filter"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-filter </span></li>
				<li><span class="glyphicon glyphicon-briefcase"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-briefcase </span></li>
				<li><span class="glyphicon glyphicon-fullscreen"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-fullscreen </span></li>
				<li><span class="glyphicon glyphicon-dashboard"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-dashboard </span></li>
				<li><span class="glyphicon glyphicon-paperclip"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-paperclip </span></li>
				<li><span class="glyphicon glyphicon-heart-empty"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-heart-empty
				</span></li>
				<li><span class="glyphicon glyphicon-link"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-link </span></li>
				<li><span class="glyphicon glyphicon-phone"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-phone </span></li>
				<li><span class="glyphicon glyphicon-pushpin"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-pushpin </span></li>
				<li><span class="glyphicon glyphicon-usd"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-usd </span></li>
				<li><span class="glyphicon glyphicon-gbp"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-gbp </span></li>
				<li><span class="glyphicon glyphicon-sort"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-sort </span></li>
				<li><span class="glyphicon glyphicon-sort-by-alphabet">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-sort-by-alphabet </span></li>
				<li><span class="glyphicon glyphicon-sort-by-alphabet-alt">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-sort-by-alphabet-alt </span></li>
				<li><span class="glyphicon glyphicon-sort-by-order"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-sort-by-order </span></li>
				<li><span class="glyphicon glyphicon-sort-by-order-alt">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-sort-by-order-alt </span></li>
				<li><span class="glyphicon glyphicon-sort-by-attributes">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-sort-by-attributes </span></li>
				<li><span class="glyphicon glyphicon-sort-by-attributes-alt">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-sort-by-attributes-alt </span></li>
				<li><span class="glyphicon glyphicon-unchecked"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-unchecked </span></li>
				<li><span class="glyphicon glyphicon-expand"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-expand </span></li>
				<li><span class="glyphicon glyphicon-collapse-down"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-collapse-down </span></li>
				<li><span class="glyphicon glyphicon-collapse-up"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-collapse-up
				</span></li>
				<li><span class="glyphicon glyphicon-log-in"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-log-in </span></li>
				<li><span class="glyphicon glyphicon-flash"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-flash </span></li>
				<li><span class="glyphicon glyphicon-log-out"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-log-out </span></li>
				<li><span class="glyphicon glyphicon-new-window"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-new-window </span></li>
				<li><span class="glyphicon glyphicon-record"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-record </span></li>
				<li><span class="glyphicon glyphicon-save"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-save </span></li>
				<li><span class="glyphicon glyphicon-open"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-open </span></li>
				<li><span class="glyphicon glyphicon-saved"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-saved </span></li>
				<li><span class="glyphicon glyphicon-import"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-import </span></li>
				<li><span class="glyphicon glyphicon-export"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-export </span></li>
				<li><span class="glyphicon glyphicon-send"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-send </span></li>
				<li><span class="glyphicon glyphicon-floppy-disk"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-floppy-disk
				</span></li>
				<li><span class="glyphicon glyphicon-floppy-saved"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-floppy-saved
				</span></li>
				<li><span class="glyphicon glyphicon-floppy-remove"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-floppy-remove </span></li>
				<li><span class="glyphicon glyphicon-floppy-save"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-floppy-save
				</span></li>
				<li><span class="glyphicon glyphicon-floppy-open"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-floppy-open
				</span></li>
				<li><span class="glyphicon glyphicon-credit-card"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-credit-card
				</span></li>
				<li><span class="glyphicon glyphicon-transfer"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-transfer </span></li>
				<li><span class="glyphicon glyphicon-cutlery"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-cutlery </span></li>
				<li><span class="glyphicon glyphicon-header"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-header </span></li>
				<li><span class="glyphicon glyphicon-compressed"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-compressed </span></li>
				<li><span class="glyphicon glyphicon-earphone"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-earphone </span></li>
				<li><span class="glyphicon glyphicon-phone-alt"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-phone-alt </span></li>
				<li><span class="glyphicon glyphicon-tower"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-tower </span></li>
				<li><span class="glyphicon glyphicon-stats"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-stats </span></li>
				<li><span class="glyphicon glyphicon-sd-video"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-sd-video </span></li>
				<li><span class="glyphicon glyphicon-hd-video"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-hd-video </span></li>
				<li><span class="glyphicon glyphicon-subtitles"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-subtitles </span></li>
				<li><span class="glyphicon glyphicon-sound-stereo"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-sound-stereo
				</span></li>
				<li><span class="glyphicon glyphicon-sound-dolby"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-sound-dolby
				</span></li>
				<li><span class="glyphicon glyphicon-sound-5-1"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-sound-5-1 </span></li>
				<li><span class="glyphicon glyphicon-sound-6-1"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-sound-6-1 </span></li>
				<li><span class="glyphicon glyphicon-sound-7-1"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-sound-7-1 </span></li>
				<li><span class="glyphicon glyphicon-copyright-mark"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-copyright-mark </span></li>
				<li><span class="glyphicon glyphicon-registration-mark">
				</span> <span class="bs-glyphicon-class"> glyphicon
						glyphicon-registration-mark </span></li>
				<li><span class="glyphicon glyphicon-cloud-download"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-cloud-download </span></li>
				<li><span class="glyphicon glyphicon-cloud-upload"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-cloud-upload
				</span></li>
				<li><span class="glyphicon glyphicon-tree-conifer"> </span> <span
					class="bs-glyphicon-class"> glyphicon glyphicon-tree-conifer
				</span></li>
				<li><span class="glyphicon glyphicon-tree-deciduous"> </span> <span
					class="bs-glyphicon-class"> glyphicon
						glyphicon-tree-deciduous </span></li>
			</ul>
		</div>
		<div class="note note-success">
			<p>
				Simple Line Icons. 162 Beautifully Crafted Webfont Icons.<br>
				For more info check out <a
					href="http://graphicburger.com/simple-line-icons-webfont/"
					target="_blank">http://graphicburger.com/simple-line-icons-webfont/</a>
			</p>
		</div>
		<div class="simplelineicons-demo">
			<span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-user"></span> &nbsp;icon-user
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-user-female"></span>
					&nbsp;icon-user-female
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-users"></span> &nbsp;icon-users
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-user-follow"></span>
					&nbsp;icon-user-follow
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-user-following"></span>
					&nbsp;icon-user-following
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-user-unfollow"></span>
					&nbsp;icon-user-unfollow
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-trophy"></span> &nbsp;icon-trophy
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-speedometer"></span>
					&nbsp;icon-speedometer
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-social-youtube"></span>
					&nbsp;icon-social-youtube
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-social-twitter"></span>
					&nbsp;icon-social-twitter
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-social-tumblr"></span>
					&nbsp;icon-social-tumblr
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-social-facebook"></span>
					&nbsp;icon-social-facebook
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-social-dropbox"></span>
					&nbsp;icon-social-dropbox
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-social-dribbble"></span>
					&nbsp;icon-social-dribbble
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-shield"></span> &nbsp;icon-shield
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-screen-tablet"></span>
					&nbsp;icon-screen-tablet
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-screen-smartphone"></span>
					&nbsp;icon-screen-smartphone
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-screen-desktop"></span>
					&nbsp;icon-screen-desktop
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-plane"></span> &nbsp;icon-plane
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-notebook"></span>
					&nbsp;icon-notebook
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-moustache"></span>
					&nbsp;icon-moustache
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-mouse"></span> &nbsp;icon-mouse
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-magnet"></span> &nbsp;icon-magnet
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-magic-wand"></span>
					&nbsp;icon-magic-wand
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-hourglass"></span>
					&nbsp;icon-hourglass
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-graduation"></span>
					&nbsp;icon-graduation
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-ghost"></span> &nbsp;icon-ghost
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-game-controller"></span>
					&nbsp;icon-game-controller
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-fire"></span> &nbsp;icon-fire
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-eyeglasses"></span>
					&nbsp;icon-eyeglasses
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-envelope-open"></span>
					&nbsp;icon-envelope-open
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-envelope-letter"></span>
					&nbsp;icon-envelope-letter
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-energy"></span> &nbsp;icon-energy
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-emoticon-smile"></span>
					&nbsp;icon-emoticon-smile
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-disc"></span> &nbsp;icon-disc
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-cursor-move"></span>
					&nbsp;icon-cursor-move
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-crop"></span> &nbsp;icon-crop
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-credit-card"></span>
					&nbsp;icon-credit-card
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-chemistry"></span>
					&nbsp;icon-chemistry
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-bell"></span> &nbsp;icon-bell
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-badge"></span> &nbsp;icon-badge
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-anchor"></span> &nbsp;icon-anchor
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-action-redo"></span>
					&nbsp;icon-action-redo
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-action-undo"></span>
					&nbsp;icon-action-undo
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-bag"></span> &nbsp;icon-bag
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-basket"></span> &nbsp;icon-basket
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-basket-loaded"></span>
					&nbsp;icon-basket-loaded
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-book-open"></span>
					&nbsp;icon-book-open
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-briefcase"></span>
					&nbsp;icon-briefcase
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-bubbles"></span> &nbsp;icon-bubbles
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-calculator"></span>
					&nbsp;icon-calculator
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-call-end"></span>
					&nbsp;icon-call-end
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-call-in"></span> &nbsp;icon-call-in
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-call-out"></span>
					&nbsp;icon-call-out
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-compass"></span> &nbsp;icon-compass
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-cup"></span> &nbsp;icon-cup
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-diamond"></span> &nbsp;icon-diamond
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-direction"></span>
					&nbsp;icon-direction
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-directions"></span>
					&nbsp;icon-directions
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-docs"></span> &nbsp;icon-docs
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-drawer"></span> &nbsp;icon-drawer
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-drop"></span> &nbsp;icon-drop
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-earphones"></span>
					&nbsp;icon-earphones
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-earphones-alt"></span>
					&nbsp;icon-earphones-alt
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-feed"></span> &nbsp;icon-feed
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-film"></span> &nbsp;icon-film
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-folder-alt"></span>
					&nbsp;icon-folder-alt
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-frame"></span> &nbsp;icon-frame
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-globe"></span> &nbsp;icon-globe
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-globe-alt"></span>
					&nbsp;icon-globe-alt
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-handbag"></span> &nbsp;icon-handbag
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-layers"></span> &nbsp;icon-layers
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-map"></span> &nbsp;icon-map
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-picture"></span> &nbsp;icon-picture
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-pin"></span> &nbsp;icon-pin
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-playlist"></span>
					&nbsp;icon-playlist
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-present"></span> &nbsp;icon-present
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-printer"></span> &nbsp;icon-printer
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-puzzle"></span> &nbsp;icon-puzzle
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-speech"></span> &nbsp;icon-speech
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-vector"></span> &nbsp;icon-vector
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-wallet"></span> &nbsp;icon-wallet
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-arrow-down"></span>
					&nbsp;icon-arrow-down
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-arrow-left"></span>
					&nbsp;icon-arrow-left
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-arrow-right"></span>
					&nbsp;icon-arrow-right
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-arrow-up"></span>
					&nbsp;icon-arrow-up
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-bar-chart"></span>
					&nbsp;icon-bar-chart
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-bulb"></span> &nbsp;icon-bulb
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-calendar"></span>
					&nbsp;icon-calendar
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-control-end"></span>
					&nbsp;icon-control-end
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-control-forward"></span>
					&nbsp;icon-control-forward
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-control-pause"></span>
					&nbsp;icon-control-pause
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-control-play"></span>
					&nbsp;icon-control-play
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-control-rewind"></span>
					&nbsp;icon-control-rewind
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-control-start"></span>
					&nbsp;icon-control-start
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-cursor"></span> &nbsp;icon-cursor
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-dislike"></span> &nbsp;icon-dislike
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-equalizer"></span>
					&nbsp;icon-equalizer
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-graph"></span> &nbsp;icon-graph
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-grid"></span> &nbsp;icon-grid
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-home"></span> &nbsp;icon-home
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-like"></span> &nbsp;icon-like
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-list"></span> &nbsp;icon-list
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-login"></span> &nbsp;icon-login
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-logout"></span> &nbsp;icon-logout
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-loop"></span> &nbsp;icon-loop
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-microphone"></span>
					&nbsp;icon-microphone
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-music-tone"></span>
					&nbsp;icon-music-tone
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-music-tone-alt"></span>
					&nbsp;icon-music-tone-alt
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-note"></span> &nbsp;icon-note
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-pencil"></span> &nbsp;icon-pencil
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-pie-chart"></span>
					&nbsp;icon-pie-chart
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-question"></span>
					&nbsp;icon-question
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-rocket"></span> &nbsp;icon-rocket
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-share"></span> &nbsp;icon-share
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-share-alt"></span>
					&nbsp;icon-share-alt
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-shuffle"></span> &nbsp;icon-shuffle
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-size-actual"></span>
					&nbsp;icon-size-actual
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-size-fullscreen"></span>
					&nbsp;icon-size-fullscreen
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-support"></span> &nbsp;icon-support
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-tag"></span> &nbsp;icon-tag
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-trash"></span> &nbsp;icon-trash
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-umbrella"></span>
					&nbsp;icon-umbrella
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-wrench"></span> &nbsp;icon-wrench
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-ban"></span> &nbsp;icon-ban
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-bubble"></span> &nbsp;icon-bubble
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-camcorder"></span>
					&nbsp;icon-camcorder
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-camera"></span> &nbsp;icon-camera
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-check"></span> &nbsp;icon-check
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-clock"></span> &nbsp;icon-clock
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-close"></span> &nbsp;icon-close
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-cloud-download"></span>
					&nbsp;icon-cloud-download
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-cloud-upload"></span>
					&nbsp;icon-cloud-upload
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-doc"></span> &nbsp;icon-doc
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-envelope"></span>
					&nbsp;icon-envelope
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-eye"></span> &nbsp;icon-eye
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-flag"></span> &nbsp;icon-flag
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-folder"></span> &nbsp;icon-folder
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-heart"></span> &nbsp;icon-heart
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-info"></span> &nbsp;icon-info
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-key"></span> &nbsp;icon-key
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-link"></span> &nbsp;icon-link
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-lock"></span> &nbsp;icon-lock
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-lock-open"></span>
					&nbsp;icon-lock-open
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-magnifier"></span>
					&nbsp;icon-magnifier
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-magnifier-add"></span>
					&nbsp;icon-magnifier-add
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-magnifier-remove"></span>
					&nbsp;icon-magnifier-remove
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-paper-clip"></span>
					&nbsp;icon-paper-clip
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-paper-plane"></span>
					&nbsp;icon-paper-plane
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-plus"></span> &nbsp;icon-plus
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-pointer"></span> &nbsp;icon-pointer
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-power"></span> &nbsp;icon-power
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-refresh"></span> &nbsp;icon-refresh
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-reload"></span> &nbsp;icon-reload
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-settings"></span>
					&nbsp;icon-settings
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-star"></span> &nbsp;icon-star
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-symbol-female"></span>
					&nbsp;icon-symbol-female
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-symbol-male"></span>
					&nbsp;icon-symbol-male
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-target"></span> &nbsp;icon-target
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-volume-1"></span>
					&nbsp;icon-volume-1
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-volume-2"></span>
					&nbsp;icon-volume-2
			</span>
			</span> <span class="item-box"> <span class="item"> <span
					aria-hidden="true" class="icon-volume-off"></span>
					&nbsp;icon-volume-off
			</span>
			</span>
		</div>
	</s:row>
</s:page>