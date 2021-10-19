<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-checkbox-link">
		<xsl:if test="descendant::cooperop-checkbox">
			<script type="text/javascript"
				src="{$contextpath}/theme/scripts/controls/input/checkbox.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-checkbox">
		<xsl:if test="child::cooperop-option">
			<div class="cols{@cols}" crid="{@crid}">
				<xsl:if test="@ishidden = 'true'">
					<xsl:attribute name="style">display:none;</xsl:attribute>
				</xsl:if>
				<xsl:if test="@draggable != ''">
					<xsl:attribute name="draggable"><xsl:value-of
						select="@draggable"></xsl:value-of></xsl:attribute>
				</xsl:if>
				<xsl:if test="@ondragstart != ''">
					<xsl:attribute name="ondragstart"><xsl:value-of
						select="@ondragstart"></xsl:value-of></xsl:attribute>
				</xsl:if>
				<xsl:if test="@ondrop != ''">
					<xsl:attribute name="ondrop"><xsl:value-of
						select="@ondrop"></xsl:value-of></xsl:attribute>
				</xsl:if>
				<xsl:if test="@ondragover != ''">
					<xsl:attribute name="ondragover"><xsl:value-of
						select="@ondragover"></xsl:value-of></xsl:attribute>
				</xsl:if>
				<xsl:if test="@isdesign != ''">
					<xsl:attribute name="data-toggle">context</xsl:attribute>
				</xsl:if>
				<xsl:choose>
					<xsl:when test="@label != ''">
						<label class="control-label">
							<xsl:attribute name="title"><xsl:value-of select="@label"></xsl:value-of></xsl:attribute>
							<xsl:value-of select="@label"></xsl:value-of>
						</label>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">cols<xsl:value-of select="@cols"></xsl:value-of> nolabel</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<div class="control-content">
				<xsl:if test="@required ='true'">
					<i class="control-required">*</i>
				</xsl:if>
					<div class="checkbox-list" ctype="checkbox" value="{@defaultValue}">
						<xsl:if test="@required ='true'">
							<xsl:attribute name="required">required</xsl:attribute>
						</xsl:if>
						<xsl:if test="@readonly ='true'">
							<xsl:attribute name="readonly">readonly</xsl:attribute>
						</xsl:if>
						<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
							(./@*[name()!='defaultValue'][name()!='required'][name()!='readonly'][name()!='draggable'][name()!='ondragstart']
							[name()!='ondrop'][name()!='ondragover'][name()!='crid'])">
							<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
						</xsl:for-each>
						<xsl:apply-templates select="cooperop-option">
							<xsl:with-param name="readonly"><xsl:value-of select="@readonly"></xsl:value-of></xsl:with-param>
						</xsl:apply-templates>
					</div>
				</div>
			</div>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>