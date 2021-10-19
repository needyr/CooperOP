<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-table-link">
		<xsl:if test="descendant::cooperop-table">
			<link rel="stylesheet" type="text/css" href="{$contextpath}/theme/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css?m={$module}"/>
			<link rel="stylesheet" type="text/css" href="{$contextpath}/theme/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css?m={$module}"/>
			<link rel="stylesheet" type="text/css" href="{$contextpath}/theme/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css?m={$module}"/>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/datatables/media/js/jquery.dataTables.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/datatables/extensions/ColReorder/js/dataTables.colReorder.min.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/datatables/extensions/Scroller/js/dataTables.scroller.min.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/layout/table.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-table">
		<div class="cols{@cols}" crid="{@crid}">
			<xsl:if test="ancestor::cooperop-form">
				<xsl:attribute name="style">padding:0;</xsl:attribute>
			</xsl:if>
			<xsl:if test="@ondrop != ''">
					<xsl:attribute name="ondrop"><xsl:value-of select="@ondrop"></xsl:value-of></xsl:attribute>
				</xsl:if>
				<xsl:if test="@ondragover != ''">
					<xsl:attribute name="ondragover"><xsl:value-of select="@ondragover"></xsl:value-of></xsl:attribute>
				</xsl:if>
			<div >
				<xsl:choose>
					<xsl:when test="@color != ''">
						<xsl:attribute name="class">portlet box <xsl:value-of select="@color"></xsl:value-of></xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">portlet box</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@border != ''">
					<xsl:attribute name="style">border-width: <xsl:value-of select="@border"></xsl:value-of>px;</xsl:attribute>
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
						<xsl:if test="@extendable = 'true'">
							<div class="tools" style="float: left; margin-left: 5px;">
								<a href="javascript:;" class="collapse">
									<xsl:attribute name="collapsed"><xsl:value-of select="@collapsed"></xsl:value-of></xsl:attribute>
								</a>
							</div>
						</xsl:if>
						<xsl:if test="child::cooperop-toolbar">
							<xsl:apply-templates select="cooperop-toolbar"></xsl:apply-templates>
						</xsl:if>
					</div>
				</xsl:if>
				<div class="portlet-body">
					<table cooperoptype="datatable" ctype="table">
					<xsl:choose>
						<xsl:when test="@noborder != ''">
							<xsl:attribute name="class">table table-striped table-hover table-nowrap</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="class">table table-striped table-bordered table-hover table-nowrap</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					
						<xsl:if test="@height > 0">
							<xsl:attribute name="theight"><xsl:value-of select="@height"></xsl:value-of></xsl:attribute>
						</xsl:if>
						<xsl:for-each select="./@*[name()!='@color'][name()!='border'][name()!='label']
						[name()!='icon'][name()!='height'][name()!='ondrop'][name()!='ondragover'][name()!='crid'][name()!='initsql']">
							<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
						</xsl:for-each>
						<thead>
							<tr>
								<xsl:apply-templates select="cooperop-tablefields"></xsl:apply-templates>
							</tr>
						</thead>
						<tbody>
							<xsl:apply-templates select="cooperop-tabledata"></xsl:apply-templates>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</xsl:template>
	<xsl:template match="cooperop-tablefields">
		<xsl:apply-templates select="cooperop-tablefield"></xsl:apply-templates>
	</xsl:template>
	<xsl:template match="cooperop-tablefield">
		<th scope="col">
			<xsl:if test="@hidden = 'true'">
				<xsl:attribute name="hidden">true</xsl:attribute>
			</xsl:if>
			<xsl:if test="@readonly = 'true'">
				<xsl:attribute name="readonly">readonly</xsl:attribute>
			</xsl:if>
			<xsl:for-each select="./@*[name()!='readonly'][name()!='hidden'][name()!='crid']">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
			<xsl:choose>
				<xsl:when test="@datatype='script'">
					<textarea class="fieldscript" style="display:none;"><xsl:apply-templates></xsl:apply-templates></textarea>				
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates></xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</th>
	</xsl:template>
	<xsl:template match="cooperop-tabledata">
		<xsl:apply-templates></xsl:apply-templates>
	</xsl:template>
</xsl:stylesheet>