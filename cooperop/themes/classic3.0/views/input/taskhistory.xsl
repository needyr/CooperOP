<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-taskhistory-link">
		<xsl:if test="descendant::cooperop-taskhistory">
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/taskhistory.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-taskhistory">
		<div class="cols4 nolabel" crid="{@crid}">
			<xsl:if test="@ishidden = 'true'">
				<xsl:attribute name="style">display:none;</xsl:attribute>
			</xsl:if>
			<xsl:if test="@draggable != ''">
				<xsl:attribute name="draggable"><xsl:value-of select="@draggable"></xsl:value-of></xsl:attribute>
			</xsl:if>
			<xsl:if test="@ondragstart != ''">
				<xsl:attribute name="ondragstart"><xsl:value-of select="@ondragstart"></xsl:value-of></xsl:attribute>
			</xsl:if>
			<xsl:if test="@ondrop != ''">
				<xsl:attribute name="ondrop"><xsl:value-of select="@ondrop"></xsl:value-of></xsl:attribute>
			</xsl:if>
			<xsl:if test="@ondragover != ''">
				<xsl:attribute name="ondragover"><xsl:value-of select="@ondragover"></xsl:value-of></xsl:attribute>
			</xsl:if>
			<xsl:if test="@isdesign != ''">
				<xsl:attribute name="data-toggle">context</xsl:attribute>
				<xsl:attribute name="data-target">#context-menu</xsl:attribute>
			</xsl:if>
			
			<div ctype="taskhistory" djbh="{@djbh}" expand="true" class="portlet box grey-steel">
				<div class="portlet-title">		
				<div class="caption"><i class="fa fa-history"></i>审批历史</div>	
					<div class="tools"><button type="button" class="btn btn-sm btn-sm btn-default expand" style="display: none;"><i class="fa fa-chevron-down"></i>展开</button>			<button type="button" class="btn btn-sm btn-sm btn-default collapse" style="display: inline-block;"><i class="fa fa-chevron-up"></i>收起</button>		</div></div>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>