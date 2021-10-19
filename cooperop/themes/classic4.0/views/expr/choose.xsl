<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="cooperop-choose">
		<choose>
			<xsl:apply-templates></xsl:apply-templates>
		</choose>
	</xsl:template>
	<xsl:template match="cooperop-when">
		<when>
			<xsl:for-each select="./@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
			<xsl:apply-templates></xsl:apply-templates>
		</when>
	</xsl:template>
	<xsl:template match="cooperop-otherwise">
		<otherwise>
			<xsl:apply-templates></xsl:apply-templates>
		</otherwise>
	</xsl:template>
</xsl:stylesheet>