<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-switch-link">
		<xsl:if test="descendant::cooperop-switch">
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/switch.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-switch">
		<div class="cols{@cols}" crid="{@crid}">
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
				<xsl:choose>
					<xsl:when test="@readonly ='true'">
						<label ctype="switch" ontext="是" offtext="否"  oncolor="primary" offcolor="default">
							<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
								(./@*[name()!='defaultValue'][name()!='required'][name()!='readonly'][name()!='draggable'][name()!='ondragstart']
								[name()!='ondrop'][name()!='ondragover'][name()!='crid'])">
								<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
							</xsl:for-each>
						</label>
					</xsl:when>
					<xsl:otherwise>
						<input type="checkbox" ctype="switch" ontext="是" offtext="否"  oncolor="primary" offcolor="default">
							<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
								(./@*[name()!='defaultValue'][name()!='required'][name()!='readonly'][name()!='draggable'][name()!='ondragstart']
								[name()!='ondrop'][name()!='ondragover'][name()!='crid'])">
								<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
							</xsl:for-each>
						</input>
					</xsl:otherwise>
				</xsl:choose>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>