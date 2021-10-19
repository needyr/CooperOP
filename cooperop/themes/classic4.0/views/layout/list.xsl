<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-list-link">
		<xsl:if test="descendant::cooperop-list">
			<link href="{$contextpath}/theme/css/components/layout/list.css" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/layout/list.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-list">
		<div class="cols{@cols}" crid="{@crid}">
			<xsl:if test="ancestor::cooperop-form">
				<xsl:attribute name="style">padding:0;</xsl:attribute>
			</xsl:if>
			<xsl:if test="@ondrop != ''">
					<xsl:attribute name="ondrop"><xsl:value-of select="@ondrop"></xsl:value-of></xsl:attribute>
				</xsl:if>
				<xsl:if test="@ondragover != ''">
					<xsl:attribute name="ondragover"><xsl:value-of select="@ondragover"></xsl:value-of></xsl:attribute>
				</xsl:if>
			<div >
				<xsl:choose>
					<xsl:when test="@color != ''">
						<xsl:attribute name="class">portlet box <xsl:value-of select="@color"></xsl:value-of></xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">portlet box</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:choose>
					<xsl:when test="ancestor::cooperop-form and @border != ''">
						<xsl:attribute name="style">padding:0!important;box-shadow:none;border-width: <xsl:value-of select="@border"></xsl:value-of>px;</xsl:attribute>
					</xsl:when>
					<xsl:when test="ancestor::cooperop-form">
						<xsl:attribute name="style">padding:0!important;box-shadow:none;</xsl:attribute>
					</xsl:when>
					<xsl:when test="@border != ''">
						<xsl:attribute name="style">border-width: <xsl:value-of select="@border"></xsl:value-of>px;</xsl:attribute>
					</xsl:when>
				</xsl:choose>
				<xsl:if test="child::cooperop-condition">
					<xsl:apply-templates select="cooperop-condition"></xsl:apply-templates>
				</xsl:if>
				<xsl:if test="@label != '' or @icon != '' or child::cooperop-toolbar or @collapsable = 'true'">
					<div class="portlet-title">
						<xsl:if test="@label != '' or @icon != ''">
							<div class="caption">
								<xsl:if test="@icon != ''">
									<i class="{@icon}"></i>
								</xsl:if>
								<xsl:value-of select="@label"></xsl:value-of>
							</div>
						</xsl:if>
						<xsl:if test="@collapsable = 'true'">
							<a href="javascript:void(0);" class="form-collapse-btn"></a>
						</xsl:if>
						<xsl:if test="child::cooperop-toolbar">
							<xsl:apply-templates select="cooperop-toolbar"></xsl:apply-templates>
						</xsl:if>
					</div>
				</xsl:if>
				<div class="portlet-body">
					<div capptype="datalist" ctype="list" class="datalist">
						<xsl:if test="@height > 0">
							<xsl:attribute name="theight"><xsl:value-of select="@height"></xsl:value-of></xsl:attribute>
						</xsl:if>
						<xsl:for-each select="./@*[name()!='@color'][name()!='border'][name()!='label'][name()!='icon'][name()!='class'][name()!='height'][name()!='ondrop'][name()!='ondragover'][name()!='crid']">
							<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
						</xsl:for-each>
						<ul class="datalist-fields">
							<xsl:apply-templates select="cooperop-listfields"></xsl:apply-templates>
						</ul>
						<xsl:apply-templates select="cooperop-listcontent">
							<xsl:with-param name="class" select="@class"></xsl:with-param>
						</xsl:apply-templates>
						<div class="datalist-data">
							<xsl:apply-templates select="cooperop-listdata"></xsl:apply-templates>
						</div>
					</div>
				</div>
			</div>
		</div>
	</xsl:template>
	<xsl:template match="cooperop-listfields">
		<xsl:apply-templates select="cooperop-listfield"></xsl:apply-templates>
	</xsl:template>
	<xsl:template match="cooperop-listfield">
		<li class="datalist-field">
			<xsl:for-each select="./@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
		</li>
	</xsl:template>
	<xsl:template match="cooperop-listcontent">
		<xsl:param name="class"></xsl:param>
		<div class="datalist-content">
			<xsl:for-each select="./@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
			<xsl:if test="$class != ''">
				<xsl:attribute name="class">datalist-content <xsl:value-of select="$class"></xsl:value-of></xsl:attribute>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="@datatype='script'">
					<textarea class="listscript" style="display:none;"><xsl:apply-templates></xsl:apply-templates></textarea>				
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates></xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</div>
	</xsl:template>
	<xsl:template match="cooperop-listdata">
		<div class="datalist-listdata">
			<xsl:for-each select="./@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
		</div>
	</xsl:template>
</xsl:stylesheet>