<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-datefield-link">
		<xsl:if test="descendant::cooperop-datefield">
			<link type="text/css" rel="stylesheet" href="{$contextpath}/theme/plugins/DatePicker/skin/default/datepicker.css?_v={$_v}"/>
			<link href="{$contextpath}/theme/css/components/input/datefield.css?_v={$_v}" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="{$contextpath}/theme/plugins/DatePicker/WdatePicker.js?_v={$_v}"></script>
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/datefield.js?_v={$_v}"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-datefield">
		<xsl:choose>
			<xsl:when test="parent::cooperop-tablefield or parent::cooperop-tabledata">
				<div class="control-content">
					<xsl:if test="@ishidden = 'true'">
						<xsl:attribute name="style">display:none;</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="class">control-content has-icon</xsl:attribute>
					<xsl:if test="@required ='true' or @required ='required'">
						<xsl:attribute name="class">control-content required has-icon</xsl:attribute>
						<i class="control-required">*</i>
					</xsl:if>
					<i class="control-icon cicon icon-calendar"></i>
					<input type="text" class="form-control date-picker" ctype="datefield" name="{@name}" format="{@format}" value="{@value}">
						<xsl:if test="@required ='true' or @required ='required'">
							<xsl:attribute name="required">required</xsl:attribute>
						</xsl:if>
						<xsl:if test="@readonly ='true'">
							<xsl:attribute name="readonly">readonly</xsl:attribute>
						</xsl:if>
						<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
						(./@*[name()!='defaultValue'][name()!='maxlength'][name()!='draggable'][name()!='ondragstart']
						[name()!='ondrop'][name()!='ondragover'][name()!='readonly'][name()!='required'][name()!='crid'])">
							<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
						</xsl:for-each>
					</input>
				</div>
			</xsl:when>
			<xsl:otherwise>
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
					<xsl:choose>
						<xsl:when test="@islabel = 'true'">
							<div class="control-content">
								<p class="form-control-static">
									<xsl:if test="@datatype != ''">
										<xsl:attribute name="datatype"><xsl:value-of select="@datatype"></xsl:value-of></xsl:attribute>
									</xsl:if>
									<xsl:if test="@format != ''">
										<xsl:attribute name="format"><xsl:value-of select="@format"></xsl:value-of></xsl:attribute>
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
							</div>
						</xsl:when>
						<xsl:otherwise>
							<div class="control-content has-icon">
								<i class="control-icon cicon icon-calendar"></i>
								<input type="text" class="form-control date-picker" ctype="datefield" name="{@name}" format="{@format}" value="{@value}">
									<xsl:if test="@required ='true' or @required ='required'">
										<xsl:attribute name="required">required</xsl:attribute>
									</xsl:if>
									<xsl:if test="@readonly ='true'">
										<xsl:attribute name="readonly">readonly</xsl:attribute>
									</xsl:if>
									<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
									(./@*[name()!='defaultValue'][name()!='maxlength'][name()!='draggable'][name()!='ondragstart']
									[name()!='ondrop'][name()!='ondragover'][name()!='readonly'][name()!='required'][name()!='crid'])">
										<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
									</xsl:for-each>
								</input>
							</div>
						</xsl:otherwise>
					</xsl:choose>
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>