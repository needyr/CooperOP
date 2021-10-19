<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-form-link">
		<xsl:if test="descendant::cooperop-form">
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/layout/form.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-form">
		<xsl:choose>
			<xsl:when test="parent::cooperop-tabpanel or @parenttype ='tabpanel'">
				<div class="form-horizontal" ctype="form" crid="{@crid}">
					<xsl:apply-templates select="cooperop-row"></xsl:apply-templates>
				</div>
				<xsl:if test="@extendable = 'true'">
					<div style="text-align:  center;position:  relative;height: 20px;">
						<i class="fa fa-angle-double-up" style="top: -1px;display:  inline-block;width: 60px;height: 20px;color: #9c9292;border: 1px solid #e1e1e1;position:  absolute;cursor:  pointer;">
							<xsl:if test="@collapsed = 'true'">
								<xsl:attribute name="class">fa fa-angle-double-down</xsl:attribute>
							</xsl:if>
						</i>
					</div>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<div class="cols{@cols}" crid="{@crid}">
					<xsl:if test="ancestor::cooperop-form">
						<xsl:attribute name="style">padding:0;</xsl:attribute>
					</xsl:if>
					<div class="portlet box {@color}">
						<xsl:if test="@fclass != ''">
							<xsl:attribute name="class"><xsl:value-of select="@fclass"></xsl:value-of></xsl:attribute>
						</xsl:if>
						<xsl:if test="@border != '' or @height != ''">
							<xsl:attribute name="style">border-width: <xsl:value-of select="@border"></xsl:value-of>px;height: <xsl:value-of select="@height"></xsl:value-of>px;</xsl:attribute>
						</xsl:if>
						<xsl:if test="@label != '' or @icon != '' or child::cooperop-toolbar">
							<div class="portlet-title">
								<xsl:if test="@label != '' or @icon != ''">
									<div class="caption">
										<xsl:if test="@icon != ''">
											<i class="{@icon}"></i>
										</xsl:if>
										<xsl:value-of select="@label"></xsl:value-of>
									</div>
								</xsl:if>
							<!-- 	<xsl:if test="@extendable = 'true'">
									<div class="tools" style="float: left; margin-left: 5px;">
										<a href="javascript:;" class="collapse">
											<xsl:attribute name="collapsed"><xsl:value-of select="@collapsed"></xsl:value-of></xsl:attribute>
										</a>
									</div>
								</xsl:if> -->
								<xsl:if test="child::cooperop-toolbar">
									<xsl:apply-templates select="cooperop-toolbar"></xsl:apply-templates>
								</xsl:if>
							</div>
						</xsl:if>
						<div class="portlet-body">
							<div class="form-horizontal" ctype="form">
							
								<xsl:for-each select="./@*[name()!='@color'][name()!='border'][name()!='label'][name()!='icon'][name()!='height'][name()!='cols'][name()!='fclass'][name()!='crid']">
									<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
								</xsl:for-each>
								<xsl:apply-templates select="cooperop-row"></xsl:apply-templates>
							</div>
						</div>
					</div>
					<xsl:if test="@extendable = 'true'">
						<div class="showhide-div">
							<i class="fa fa-angle-double-up showhide">
								<xsl:if test="@collapsed = 'true'">
									<xsl:attribute name="class">fa fa-angle-double-down showhide</xsl:attribute>
								</xsl:if>
							</i>
						</div>
					</xsl:if>
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>