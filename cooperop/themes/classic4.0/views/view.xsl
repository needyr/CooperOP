<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:import href="page.xsl" />
	
	<xsl:import href="layout/form.xsl" />
	<xsl:import href="layout/row.xsl" />
	<xsl:import href="layout/column.xsl" />
	<xsl:import href="layout/condition.xsl" />
	<xsl:import href="layout/table.xsl" />
	<xsl:import href="layout/list.xsl" />
	<xsl:import href="layout/tabpanel.xsl" />
	<xsl:import href="layout/toolbar.xsl" />
	
	<xsl:import href="input/autocomplete.xsl" />
	<xsl:import href="input/button.xsl" />
	<xsl:import href="input/datefield.xsl" />
	<xsl:import href="input/chart.xsl" />
	<xsl:import href="input/checkbox.xsl" />
	<xsl:import href="input/file.xsl" />
	<xsl:import href="input/image.xsl" />
	<xsl:import href="input/pdf.xsl" />
	<xsl:import href="input/radio.xsl" />
	<xsl:import href="input/richeditor.xsl" />
	<xsl:import href="input/select.xsl" />
	<xsl:import href="input/select2.xsl" />
	<xsl:import href="input/selecttree.xsl" />
	<xsl:import href="input/switch.xsl" />
	<xsl:import href="input/textarea.xsl" />
	<xsl:import href="input/textfield.xsl" />
	<xsl:import href="input/timefield.xsl" />
	<xsl:import href="input/option.xsl" />
	<xsl:import href="input/taskhistory.xsl" />
	
	<xsl:import href="expr/if.xsl" />
	<xsl:import href="expr/foreach.xsl" />
	<xsl:import href="expr/set.xsl" />
	<xsl:import href="expr/choose.xsl" />
	
	<xsl:template match="none"></xsl:template>
	
	<xsl:param name="contextpath"></xsl:param>
	<xsl:param name="ismodal"></xsl:param>
	<xsl:param name="module"></xsl:param>
	<xsl:param name="pageid"></xsl:param>
	<xsl:param name="refreshcycle"></xsl:param>
	<xsl:param name="welcomepage"></xsl:param>
	<xsl:param name="url"></xsl:param>
	<xsl:param name="page"></xsl:param>
	<xsl:param name="nowyear"></xsl:param>
	<xsl:param name="cooperopusername"></xsl:param>
	<xsl:param name="userinfo"></xsl:param>
	<xsl:param name="system_title"></xsl:param>
	<xsl:param name="copyright"></xsl:param>
	<xsl:param name="ws_token_key"></xsl:param>
	<xsl:param name="ws_app_key"></xsl:param>
	<xsl:param name="ws_url"></xsl:param>
	<xsl:param name="http_url"></xsl:param>
	<xsl:param name="_v"></xsl:param>
	<xsl:param name="isblank"></xsl:param>
	<xsl:param name="rm_url"></xsl:param>
	
	<xsl:template match="cooperop-html">
		<xsl:variable name="html">
			<xsl:variable name="str"><xsl:value-of select="text()"></xsl:value-of></xsl:variable>
			<xsl:call-template name="replaceImg">
				<xsl:with-param name="text" select="$str" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:value-of select="$html" disable-output-escaping="yes"/>
	</xsl:template>
	
	<xsl:template name="replaceImg">
		<xsl:param name="text" />
		<xsl:variable name="start"><![CDATA[<img]]></xsl:variable>
		<xsl:variable name="end"><![CDATA[>]]></xsl:variable>
		<xsl:choose>
			<xsl:when test="contains($text,$start)">
				<xsl:value-of select="substring-before($text,$start)" />
				<xsl:variable name="img_b"><xsl:value-of select="substring-after($text, $start)" /></xsl:variable>
				<xsl:value-of select="$start" />
				<xsl:variable name="img"><xsl:value-of select="substring-before($img_b, $end)" /></xsl:variable>
				<xsl:variable name="img_a">
					<xsl:variable name="x"><![CDATA[ src=]]></xsl:variable>
					<xsl:variable name="y"><![CDATA[ cooperop-src=]]></xsl:variable>
					<xsl:call-template name="replaceFunc">
						<xsl:with-param name="text" select="$img" />
						<xsl:with-param name="replace" select="$x" />
						<xsl:with-param name="by" select="$y" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:value-of select="$img_a" />
				<xsl:value-of select="$end" />
				<xsl:call-template name="replaceImg">
					<xsl:with-param name="text" select="substring-after($img_b,$end)" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$text" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="replaceFunc">
		<xsl:param name="text" />
		<xsl:param name="replace" />
		<xsl:param name="by" />
		<xsl:choose>
			<xsl:when test="contains($text,$replace)">
				<xsl:value-of select="substring-before($text,$replace)" />
				<xsl:value-of select="$by" />
				<xsl:call-template name="replaceFunc">
					<xsl:with-param name="text"
						select="substring-after($text,$replace)" />
					<xsl:with-param name="replace" select="$replace" />
					<xsl:with-param name="by" select="$by" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$text" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>