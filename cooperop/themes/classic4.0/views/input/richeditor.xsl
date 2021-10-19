<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-richeditor-link">
		<xsl:if test="descendant::cooperop-richeditor">
			<link rel="stylesheet" type="text/css" href="{$contextpath}/theme/plugins/bootstrap-summernote/summernote.css?_v={$_v}"></link>
			<link href="{$contextpath}/theme/css/components/input/richeditor.css?_v={$_v}" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="{$contextpath}/theme/plugins/bootstrap-summernote/summernote.js?_v={$_v}"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/bootstrap-summernote/lang/summernote-zh-CN.js?_v={$_v}"></script>
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/richeditor.js?_v={$_v}"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-richeditor">
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
						<xsl:if test="@required ='true' or @required ='required'">
							<i class="control-required">*</i>
						</xsl:if>
						<xsl:value-of select="@label"></xsl:value-of>
					</label>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="class">cols<xsl:value-of select="@cols"></xsl:value-of> nolabel</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<div class="control-content">
				<xsl:choose>
					<xsl:when test="@islabel = 'true'">
						<div class="form-control-static"><xsl:value-of select="@value"></xsl:value-of><xsl:apply-templates></xsl:apply-templates></div>
					</xsl:when>
					<xsl:otherwise>
						<textarea ctype="richeditor">
							<xsl:for-each select="(./@*[name()!='defaultValue'][name()!='value'][name()!='draggable'][name()!='ondragstart']
								[name()!='ondrop'][name()!='ondragover'][name()!='crid'])">
								<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
							</xsl:for-each>
							<xsl:value-of select="@value"></xsl:value-of>
							<xsl:apply-templates></xsl:apply-templates>
						</textarea>
					</xsl:otherwise>
				</xsl:choose>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>