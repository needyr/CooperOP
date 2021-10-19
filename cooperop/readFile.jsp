<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

 <link rel="stylesheet" type="text/css" href="/theme/flexpaper/css/flexpaper.css" />
<script src="/theme/flexpaper/js/jquery.min.js" type="text/javascript"></script>
<script src="/theme/flexpaper/js/flexpaper.js" type="text/javascript"></script>
<script src="/theme/flexpaper/swfobject/swfobject.js" type="text/javascript"></script>
<script src="/theme/flexpaper/js/flexpaper_handlers.js" type="text/javascript"></script>

<div style="left:10px;top:10px;">
<div id="documentViewer" class="flexpaper_viewer" style="width:100%;height:100%"></div>
		
<script type="text/javascript">

    var startDocument = "Paper";
	var file = escape('/readonline/swfFile/<%=(String)session.getAttribute("fileName")%>');
    $('#documentViewer').FlexPaperViewer(
            { config : {

                SWFFile : file,
                Scale : 0.6,
                ZoomTransition : 'easeOut',
                ZoomTime : 0.5,
                ZoomInterval : 0.2,
                FitPageOnLoad : true,
                FitWidthOnLoad : true,
                FullScreenAsMaxWindow : false,
                ProgressiveLoading : false,
                MinZoomSize : 0.2,
                MaxZoomSize : 5,
                SearchMatchAll : false,
                InitViewMode : 'Portrait',
                RenderingOrder : 'flash',
                StartAtPage : '',
                
                PrintPaperAsBitmap : false,
				PrintEnabled : false,
				PrintVisible: false,
				
                ViewModeToolsVisible : true,
                ZoomToolsVisible : true,
                NavToolsVisible : true,
                CursorToolsVisible : true,
                SearchToolsVisible : true,
                WMode : 'window',
                localeChain: 'en_US'
            }}
    );
</script>
</div>