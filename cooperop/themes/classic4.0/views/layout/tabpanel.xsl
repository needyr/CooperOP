<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-tabpanel-link">
		<xsl:if test="descendant::cooperop-tabpanel">
			<link href="{$contextpath}/theme/css/components/layout/tabpanel.css" rel="stylesheet" type="text/css" />
			<script type="text/javascript"
				src="{$contextpath}/theme/scripts/controls/layout/tabpanel.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-tabpanel">
		<div class="cols{@cols}" crid="{@crid}">
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
			<div class="portlet box {@color} tab-portlet" ctype="tabpanel" style="min-height: 30px;">
				<div class="portlet-title">
					<ul class="nav nav-tabs">
						<xsl:for-each select="cooperop-form | cooperop-table | cooperop-list">
							<li>
								<xsl:if test="@active = 'true'">
									<xsl:attribute name="class">active</xsl:attribute>
								</xsl:if>
								<xsl:if test="@onclick != ''">
									<xsl:attribute name="onclick"><xsl:value-of select="@onclick"></xsl:value-of></xsl:attribute>
								</xsl:if>
								<a>
									<xsl:if test="@icon != ''">
										<i class="{@icon}"></i>
									</xsl:if>
									<xsl:value-of select="@label"></xsl:value-of>
								</a>
							</li>
						</xsl:for-each>
					</ul>
					<div class="tab-tools">
						<xsl:for-each select="cooperop-form | cooperop-table | cooperop-list">
							<xsl:choose>
								<xsl:when test="child::cooperop-toolbar">
									<xsl:apply-templates select="cooperop-toolbar">
										<xsl:with-param name="active"><xsl:value-of select="@active"></xsl:value-of></xsl:with-param>
									</xsl:apply-templates>
								</xsl:when>
								<xsl:otherwise>
									<div class="tools">
									</div>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
					</div>
				</div>
				<div class="portlet-body" > <!-- style="padding-bottom:0px;" -->
					<div class="tab-content">
						<xsl:for-each select="cooperop-form | cooperop-table | cooperop-list">
							<div class="tab-pane">
								<xsl:if test="@crid != ''">
									<xsl:attribute name="crid"><xsl:value-of select="@crid"></xsl:value-of></xsl:attribute>
								</xsl:if>
								<xsl:if test="@active = 'true'">
									<xsl:attribute name="class">tab-pane active</xsl:attribute>
								</xsl:if>
								<xsl:if test="name() = 'cooperop-form'">
									<div class="form-horizontal" ctype="form">
										<xsl:if test="child::cooperop-formextend">
											<xsl:attribute name="class">form-horizontal form-extend</xsl:attribute>
										</xsl:if>
										<xsl:for-each select="./@*[name()!='@color'][name()!='border'][name()!='label'][name()!='icon'][name()!='height'][name()!='cols'][name()!='fclass'][name()!='crid']">
											<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
										</xsl:for-each>
										<xsl:apply-templates select="cooperop-row"></xsl:apply-templates>
										<xsl:for-each select="cooperop-formextend">
											<div class="form-extend-body">
												<xsl:for-each select="./@*[name()!='@color'][name()!='border'][name()!='label'][name()!='icon'][name()!='height'][name()!='cols'][name()!='fclass'][name()!='crid']">
													<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
												</xsl:for-each>
												<xsl:apply-templates select="cooperop-row"></xsl:apply-templates>
											</div>
											<div class="form-extend-btn">
												<a href="javascript:void(0);"></a>
											</div>
										</xsl:for-each>
									</div>
								</xsl:if>
								<xsl:if test="name() = 'cooperop-table'">
									<div class="table-info">
										<div class="table-totals {@tableid}"></div>
										<div class="table-templage"></div>
									</div>
									<table crid="{@crid}" capptype="datatable" ctype="table" class="table table-striped table-bordered table-hover table-nowrap">
										<xsl:if test="@ondrop != ''">
											<xsl:attribute name="ondrop"><xsl:value-of select="@ondrop"></xsl:value-of></xsl:attribute>
										</xsl:if>
										<xsl:if test="@ondragover != ''">
											<xsl:attribute name="ondragover"><xsl:value-of select="@ondragover"></xsl:value-of></xsl:attribute>
										</xsl:if>
										<xsl:if test="@height > 0">
											<xsl:attribute name="theight"><xsl:value-of select="@height"></xsl:value-of></xsl:attribute>
										</xsl:if>
										<xsl:for-each select="./@*[name()!='@color'][name()!='border'][name()!='label'][name()!='icon'][name()!='height'][name()!='ondrop'][name()!='ondragover'][name()!='crid']">
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
								</xsl:if>
								<xsl:if test="name() = 'cooperop-list'">
									<div capptype="datalist" ctype="list" class="datalist">
										<xsl:if test="@height > 0">
											<xsl:attribute name="theight"><xsl:value-of select="@height"></xsl:value-of></xsl:attribute>
										</xsl:if>
										<xsl:for-each select="./@*[name()!='@color'][name()!='border'][name()!='label'][name()!='icon'][name()!='class'][name()!='height'][name()!='ondrop'][name()!='ondragover'][name()!='crid']">
											<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
										</xsl:for-each>
										<ul class="datalist-fields">
											<xsl:apply-templates select="cooperop-listfields"></xsl:apply-templates>
										</ul>
										<xsl:apply-templates select="cooperop-listcontent">
											<xsl:with-param name="class" select="@class"></xsl:with-param>
										</xsl:apply-templates>
										<div class="datalist-data">
											<xsl:apply-templates select="cooperop-listdata"></xsl:apply-templates>
										</div>
									</div>
								</xsl:if>
							</div>
						</xsl:for-each>
					</div>
				</div>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>