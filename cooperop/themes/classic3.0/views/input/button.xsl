<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-button-link">
		<xsl:if test="descendant::cooperop-button">
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/button.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-button">
		<xsl:param name="type"></xsl:param>
		<xsl:choose>
			<xsl:when test="$type = 'link' or @type = 'link'">
				<li  crid="{@crid}">
					<a href="javascript:;" action="{@action}" onclick="{@onclick}">
						<xsl:if test="@icon!=''">
							<i class="{@icon}"></i>
						</xsl:if>
						<xsl:value-of select="@label"></xsl:value-of>
						<xsl:apply-templates></xsl:apply-templates>
					</a>
				</li>
		</xsl:when>
		<xsl:otherwise>
		<button type="button" t="1" ctype="button" class="btn btn-sm {@size} btn-default {@color}" action="{@action}" crid="{@crid}">
			<xsl:if test="@icon = 'fa fa-search'">
				<xsl:attribute name="type">submit</xsl:attribute>
			</xsl:if>
			<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
			<xsl:if test="@type ='default'">
				<xsl:attribute name="type">submit</xsl:attribute>
			</xsl:if>
			<xsl:if test="@icon!=''">
				<i class="{@icon}"></i>
			</xsl:if>
			<xsl:value-of select="@label"></xsl:value-of>
			<xsl:apply-templates></xsl:apply-templates>
		</button>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>