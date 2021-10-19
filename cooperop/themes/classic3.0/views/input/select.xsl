<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-select-link">
		<xsl:if test="descendant::cooperop-select">
			<script type="text/javascript"
				src="{$contextpath}/theme/scripts/controls/input/select.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-select">
		<xsl:if test="child::cooperop-option">
			<xsl:choose>
				<xsl:when test="parent::cooperop-tablefield or parent::cooperop-tabledata">
					<div class="control-content">
				<xsl:if test="@required ='true'">
					<i class="control-required">*</i>
				</xsl:if>
						<xsl:if test="@ishidden = 'true'">
							<xsl:attribute name="style">display:none;</xsl:attribute>
						</xsl:if>
						<xsl:if test="@icon != ''">
							<xsl:attribute name="class">control-content has-icon</xsl:attribute>
						</xsl:if>
						<select ctype="select" class="form-control " value="2" intable="intable">
							<xsl:if test="@required ='true'">
								<xsl:attribute name="required">required</xsl:attribute>
							</xsl:if>
							<xsl:if test="@readonly ='true'">
								<xsl:attribute name="readonly">readonly</xsl:attribute>
							</xsl:if>
							<xsl:for-each select=" ./@*[name()!='defaultValue'][name()!='draggable'][name()!='ondragstart']
								[name()!='ondrop'][name()!='ondragover'][name()!='readonly'][name()!='required'][name()!='crid']">
								<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
							</xsl:for-each>
							<option value=""></option>
							<xsl:apply-templates select="cooperop-option">
								<xsl:with-param name="readonly"><xsl:value-of select="@readonly"></xsl:value-of></xsl:with-param>
							</xsl:apply-templates>
						</select>
					</div>
				</xsl:when>
				<xsl:otherwise>
					<div class="cols{@cols}" crid="{@crid}">
						<xsl:if test="@ishidden = 'true'">
							<xsl:attribute name="style">display:none;</xsl:attribute>
						</xsl:if>
						<xsl:if test="@draggable != ''">
							<xsl:attribute name="draggable"><xsl:value-of
								select="@draggable"></xsl:value-of></xsl:attribute>
						</xsl:if>
						<xsl:if test="@ondragstart != ''">
							<xsl:attribute name="ondragstart"><xsl:value-of
								select="@ondragstart"></xsl:value-of></xsl:attribute>
						</xsl:if>
						<xsl:if test="@ondrop != ''">
							<xsl:attribute name="ondrop"><xsl:value-of
								select="@ondrop"></xsl:value-of></xsl:attribute>
						</xsl:if>
						<xsl:if test="@ondragover != ''">
							<xsl:attribute name="ondragover"><xsl:value-of
								select="@ondragover"></xsl:value-of></xsl:attribute>
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
				<xsl:if test="@required ='true'">
					<i class="control-required">*</i>
				</xsl:if>
							<select ctype="select" class="form-control " value="2">
								<xsl:if test="@required ='true'">
									<xsl:attribute name="required">required</xsl:attribute>
								</xsl:if>
								<xsl:if test="@readonly ='true'">
									<xsl:attribute name="readonly">readonly</xsl:attribute>
								</xsl:if>
								<xsl:for-each select=" ./@*[name()!='defaultValue'][name()!='draggable'][name()!='ondragstart']
									[name()!='ondrop'][name()!='ondragover'][name()!='required'][name()!='readonly'][name()!='crid']">
									<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
								</xsl:for-each>
								<!-- <option value="">请选择...</option> -->
								<xsl:apply-templates select="cooperop-option">
									<xsl:with-param name="readonly"><xsl:value-of select="@readonly"></xsl:value-of></xsl:with-param>
								</xsl:apply-templates>
							</select>
						</div>
					</div>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>