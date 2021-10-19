<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-condition-link">
		<xsl:if test="descendant::cooperop-condition">
			<link href="{$contextpath}/theme/css/components/layout/condition.css" rel="stylesheet" type="text/css" />
			<script type="text/javascript"
				src="{$contextpath}/theme/scripts/controls/layout/condition.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-condition">
		<xsl:choose>
			<xsl:when test="parent::cooperop-table or @parenttype ='table' or parent::cooperop-list or @parenttype ='list'">
				<div class="portlet-condition {@class}" ctype="condition" crid="{@crid}">
					<xsl:for-each select="./@*[name()!='@class'][name()!='@tips'][name()!='extendable'][name()!='crid']">
						<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
					</xsl:for-each>
					<div class="portlet-condition-main">
						<div class="portlet-condition-filter"><input name="filter" placeholder="{@tips}"/><a href="javascript:void(0);"><i class="fa fa-search"></i>查询</a></div>
						<xsl:if test="@extendable = 'true'">
							<a href="javascript:void(0);" style="">高级查询<i class="fa fa-caret-down"></i></a>
						</xsl:if>
					</div>
					<xsl:if test="@extendable = 'true'">
						<div class="portlet-condition-extend form-horizontal" style="display:none;">
							<xsl:apply-templates></xsl:apply-templates>
						</div>
					</xsl:if>
				</div>
			</xsl:when>
			<xsl:otherwise>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>