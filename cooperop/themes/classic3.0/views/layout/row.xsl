<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-row-link">
		<xsl:if test="descendant::cooperop-row">
			<script type="text/javascript"
				src="{$contextpath}/theme/scripts/controls/layout/row.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-row">
		<xsl:choose>
			<xsl:when test="parent::cooperop-table">
				<xsl:apply-templates></xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<div class="row-fluid {@ishidden}" style="height:{@height}px;" crid="{@crid}" type="row">
					<xsl:choose>
						<xsl:when test="@isdesign">
						</xsl:when>
						<xsl:otherwise>
							<xsl:if test="@ishidden != ''">
								<xsl:attribute name="style">display:none;</xsl:attribute>
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:for-each select="./@*[name()!='height'][name()!='crid']">
						<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
					</xsl:for-each>
					<xsl:apply-templates></xsl:apply-templates>
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>