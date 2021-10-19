<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-radio-link">
		<xsl:if test="descendant::cooperop-radio">
			<link href="{$contextpath}/theme/css/components/input/radio.css?_v={$_v}" rel="stylesheet" type="text/css" />
			<script type="text/javascript"
				src="{$contextpath}/theme/scripts/controls/input/radio.js?_v={$_v}"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-radio">
		<xsl:if test="child::cooperop-option or @dictionary != ''">
			<xsl:choose>
				<xsl:when test="parent::cooperop-tablefield or parent::cooperop-tabledata">
					<div class="control-content">
						<xsl:if test="@ishidden = 'true'">
							<xsl:attribute name="style">display:none;</xsl:attribute>
						</xsl:if>
						<xsl:if test="@icon != ''">
							<xsl:attribute name="class">control-content has-icon</xsl:attribute>
						</xsl:if>
						<xsl:if test="@required ='true' or @required ='required'">
							<xsl:attribute name="class">control-content required</xsl:attribute>
							<xsl:if test="@icon != ''">
								<xsl:attribute name="class">control-content required has-icon</xsl:attribute>
							</xsl:if>
							<i class="control-required">*</i>
						</xsl:if>
						<div class="radio-list" ctype="radio" value="{@defaultValue}">
							<xsl:choose>
								<xsl:when test="@value != ''">
									<xsl:attribute name="value"><xsl:value-of select="@value"></xsl:value-of></xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:if test="@defaultValue != ''">
										<xsl:attribute name="value"><xsl:value-of select="@defaultValue"></xsl:value-of></xsl:attribute>
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:if test="@required ='true' or @required ='required'">
								<xsl:attribute name="required">required</xsl:attribute>
							</xsl:if>
							<xsl:if test="@readonly ='true'">
								<xsl:attribute name="readonly">readonly</xsl:attribute>
							</xsl:if>
							<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
								(./@*[name()!='defaultValue'][name()!='required'][name()!='readonly'][name()!='draggable'][name()!='ondragstart']
								[name()!='ondrop'][name()!='ondragover'][name()!='crid'])">
								<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
							</xsl:for-each>
							<xsl:apply-templates select="cooperop-option">
								<xsl:with-param name="readonly"><xsl:value-of select="@readonly"></xsl:value-of></xsl:with-param>
							</xsl:apply-templates>
						</div>
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
									<p class="form-control-static">
										<xsl:if test="@dictionary != ''">
											<xsl:attribute name="dictionary"><xsl:value-of select="@dictionary"></xsl:value-of></xsl:attribute>
										</xsl:if>
										<xsl:if test="@action != ''">
											<xsl:attribute name="action"><xsl:value-of select="@action"></xsl:value-of></xsl:attribute>
										</xsl:if>
										<xsl:if test="child::cooperop-option">
											<xsl:attribute name="select-option">[<xsl:for-each select="cooperop-option" ><xsl:if test="position() > 1">,</xsl:if>{"code":"<xsl:value-of select="@value"></xsl:value-of>","name":"<xsl:value-of select="@label"></xsl:value-of>"}</xsl:for-each>]</xsl:attribute>
										</xsl:if>
										<xsl:value-of select="."/>
										<xsl:choose>
											<xsl:when test="@value != ''">
												<xsl:value-of select="@value"></xsl:value-of>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="@defaultValue != ''"><xsl:value-of select="@defaultValue"></xsl:value-of></xsl:if>
											</xsl:otherwise>
										</xsl:choose>
									</p>
								</xsl:when>
								<xsl:otherwise>
									<div class="radio-list" ctype="radio" value="{@defaultValue}">
										<xsl:choose>
											<xsl:when test="@value != ''">
												<xsl:attribute name="value"><xsl:value-of select="@value"></xsl:value-of></xsl:attribute>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="@defaultValue != ''">
													<xsl:attribute name="value"><xsl:value-of select="@defaultValue"></xsl:value-of></xsl:attribute>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
										<xsl:if test="@required ='true' or @required ='required'">
											<xsl:attribute name="required">required</xsl:attribute>
										</xsl:if>
										<xsl:if test="@readonly ='true'">
											<xsl:attribute name="readonly">readonly</xsl:attribute>
										</xsl:if>
										<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
											(./@*[name()!='defaultValue'][name()!='required'][name()!='readonly'][name()!='draggable'][name()!='ondragstart']
											[name()!='ondrop'][name()!='ondragover'][name()!='crid'])">
											<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
										</xsl:for-each>
										<xsl:apply-templates select="cooperop-option">
											<xsl:with-param name="readonly"><xsl:value-of select="@readonly"></xsl:value-of></xsl:with-param>
										</xsl:apply-templates>
									</div>
								</xsl:otherwise>
							</xsl:choose>
						</div>
					</div>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>