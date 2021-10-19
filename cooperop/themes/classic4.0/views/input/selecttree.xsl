<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-selecttree-link">
		<xsl:if test="descendant::cooperop-selecttree">
			<link rel="stylesheet" href="{$contextpath}/theme/plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
			<link href="{$contextpath}/theme/css/components/input/selecttree.css?_v={$_v}" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="{$contextpath}/theme/plugins/ztree/js/jquery.ztree.core.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/ztree/js/jquery.ztree.excheck.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/selecttree.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-selecttree">
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
			<div class="control-content has-icon">
				 <i class="control-icon cicon icon-dropdown2"></i>
				<input ctype="selecttree" icon="fa fa-sort-desc" style="cursor: text;border: 1px solid #d4cdcd;background-color: #fff;" class="form-control ui-selecttree-input" type="text" readonly="readonly" value="{@text}" tt="tt">
					<xsl:if test="@required ='true' or @required ='required'">
						<xsl:attribute name="required">required</xsl:attribute>
					</xsl:if>
					<xsl:if test="@readonly ='true'">
						<xsl:attribute name="readonly">readonly</xsl:attribute>
					</xsl:if>
					<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
					(./@*[name()!='defaultValue'][name()!='maxlength'][name()!='draggable'][name()!='ondragstart']
					[name()!='ondrop'][name()!='ondragover'][name()!='readonly'][name()!='required'][name()!='crid'][name()!='value'])">
						<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
					</xsl:for-each>
				</input>
				<div class="form-control {@name}_v" type="text"  data-v="{@value}" style="display:none;">
				</div>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>