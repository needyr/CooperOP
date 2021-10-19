<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="cooperop-option">
		<xsl:param name="readonly"></xsl:param>
		<xsl:choose>
			<xsl:when test="parent::cooperop-checkbox">
				<label class="checkbox-inline">
					<input type="checkbox" ccinput="ccinput" name="{../@name}" value="{@value}">
						<xsl:if test="@disabled ='true'">
							<xsl:attribute name="disabled">disabled</xsl:attribute>
						</xsl:if>
						<xsl:if test="$readonly ='true'">
						<xsl:attribute name="readonly">true</xsl:attribute>
					</xsl:if>
					</input>
					<xsl:value-of select="@label"></xsl:value-of>
				</label>
			</xsl:when>
			<xsl:when test="parent::cooperop-radio">
				<label class="radio-inline">
					<input type="radio" ccinput="ccinput" name="{../@name}" value="{@value}">
						<xsl:if test="@disabled ='true'">
							<xsl:attribute name="disabled">disabled</xsl:attribute>
						</xsl:if>
						<xsl:if test="$readonly ='true'">
						<xsl:attribute name="readonly">true</xsl:attribute>
					</xsl:if>
					</input>
					<xsl:value-of select="@label"></xsl:value-of>
				</label>
			</xsl:when>
			<xsl:when test="parent::cooperop-select">
				<option value="{@value}">
					<xsl:if test="@disabled ='true'">
						<xsl:attribute name="disabled">disabled</xsl:attribute>
					</xsl:if>
					<xsl:if test="$readonly ='true'">
						<xsl:attribute name="readonly">true</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label"></xsl:value-of>
				</option>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>