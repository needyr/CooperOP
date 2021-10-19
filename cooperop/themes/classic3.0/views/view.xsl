<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:import href="page.xsl" />
	
	<xsl:import href="layout/form.xsl" />
	<xsl:import href="layout/row.xsl" />
	<xsl:import href="layout/table.xsl" />
	<xsl:import href="layout/tabpanel.xsl" />
	<xsl:import href="layout/toolbar.xsl" />
	
	<xsl:import href="input/autocomplete.xsl" />
	<xsl:import href="input/button.xsl" />
	<xsl:import href="input/datefield.xsl" />
	<xsl:import href="input/checkbox.xsl" />
	<xsl:import href="input/file.xsl" />
	<xsl:import href="input/image.xsl" />
	<xsl:import href="input/radio.xsl" />
	<xsl:import href="input/richeditor.xsl" />
	<xsl:import href="input/select.xsl" />
	<xsl:import href="input/switch.xsl" />
	<xsl:import href="input/textarea.xsl" />
	<xsl:import href="input/textfield.xsl" />
	<xsl:import href="input/timefield.xsl" />
	<xsl:import href="input/option.xsl" />
	<xsl:import href="input/taskhistory.xsl" />
	<xsl:import href="input/chart.xsl" />
	<xsl:import href="input/selecttree.xsl" />
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
	<xsl:template match="cooperop-html">
		<xsl:value-of select="text()" disable-output-escaping="yes">
		</xsl:value-of>
	</xsl:template>
</xsl:stylesheet>