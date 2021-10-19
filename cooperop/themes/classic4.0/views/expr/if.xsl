<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="cooperop-if">
		<if>
			<xsl:for-each select="./@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
			<xsl:apply-templates></xsl:apply-templates>
		</if>
	</xsl:template>
</xsl:stylesheet>