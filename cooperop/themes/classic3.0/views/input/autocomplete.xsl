<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-autocomplete-link">
		<xsl:if test="descendant::cooperop-autocomplete">
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/autocomplete.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-autocomplete">
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
					<xsl:when test="@islabel = 'true'">
						<div class="control-content">
							<p class="form-control-static"><xsl:value-of select="@value"></xsl:value-of></p>
						</div>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">control-content has-icon</xsl:attribute>
						<i class="control-icon fa fa-caret-down"></i>
						<input type="text" class="form-control" value="{@defaultValue}" ctype="autocomplete">
							<xsl:if test="@autocomplete_initaction !=''">
								<xsl:attribute name="action"><xsl:value-of select="@autocomplete_initaction"></xsl:value-of></xsl:attribute>
							</xsl:if>
							<xsl:if test="@required ='true'">
								<xsl:attribute name="required">required</xsl:attribute>
							</xsl:if>
							<xsl:if test="@readonly ='true'">
								<xsl:attribute name="readonly">readonly</xsl:attribute>
							</xsl:if>
							<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
							(./@*[name()!='defaultValue'][name()!='maxlength'][name()!='draggable'][name()!='ondragstart']
							[name()!='ondrop'][name()!='ondragover'][name()!='readonly'][name()!='required'][name()!='autocomplete_initaction'])">
								<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
							</xsl:for-each>
						</input>
						<div class="autocomplete-content">
						<xsl:for-each select="cooperop-option">
							<xsl:if test="position() = 1">
								<div data-value="{@value}" data-label="{@label}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></div>
							</xsl:if>
							<xsl:if test="position() &gt; 1">
								<div>
									<xsl:for-each select="./@*">
										<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
									</xsl:for-each>
								</div>
							</xsl:if>
						</xsl:for-each>
						</div>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@required ='true'">
					<i class="control-required">*</i>
				</xsl:if>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>