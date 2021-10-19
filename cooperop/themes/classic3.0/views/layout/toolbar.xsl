<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-toolbar-link">
		<xsl:if test="descendant::cooperop-toolbar">
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/layout/toolbar.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-toolbar">
		<xsl:param name="active"></xsl:param>
		<xsl:choose>
			<xsl:when test="parent::cooperop-form or parent::cooperop-table or @tooltype='tftool'">
				<div class="tools" ctype="toolbar" crid="{@crid}">
					<xsl:if test="@ondrop != ''">
						<xsl:attribute name="ondrop"><xsl:value-of select="@ondrop"></xsl:value-of></xsl:attribute>
					</xsl:if>
					<xsl:if test="@ondragover != ''">
						<xsl:attribute name="ondragover"><xsl:value-of select="@ondragover"></xsl:value-of></xsl:attribute>
					</xsl:if>
					<xsl:if test="$active = 'true'">
						<xsl:attribute name="class">tools active</xsl:attribute>
					</xsl:if>
					<xsl:apply-templates>
					</xsl:apply-templates>
				</div>
			</xsl:when>
			<xsl:otherwise>
				<div class="navbar navbar-default" role="navigation" crid="{@crid}" ctype="toolbar">
					<xsl:if test="@ondrop != ''">
						<xsl:attribute name="ondrop"><xsl:value-of select="@ondrop"></xsl:value-of></xsl:attribute>
					</xsl:if>
					<xsl:if test="@ondragover != ''">
						<xsl:attribute name="ondragover"><xsl:value-of select="@ondragover"></xsl:value-of></xsl:attribute>
					</xsl:if>
					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse navbar-ex1-collapse">
					<ul class="nav navbar-nav">
						<xsl:apply-templates>
						<xsl:with-param name="type">link</xsl:with-param>
						</xsl:apply-templates>
						</ul>
					</div>
					<!-- /.navbar-collapse -->
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="cooperop-toolbar-left">
		<ul class="nav navbar-nav" crid="{@crid}">
			<xsl:apply-templates></xsl:apply-templates>
		</ul>
	</xsl:template>
	<xsl:template match="cooperop-toolbar-right">
		<ul class="nav navbar-nav navbar-right" crid="{@crid}">
			<xsl:apply-templates>
				<xsl:with-param name="side">right</xsl:with-param>
			</xsl:apply-templates>
		</ul>
	</xsl:template>
	<xsl:template match="cooperop-buttongroup">
		<xsl:param name="type"></xsl:param>
		<xsl:param name="side"></xsl:param>
		<xsl:choose>
			<xsl:when test="parent::cooperop-toolbar-right or parent::cooperop-toolbar-left">
				<xsl:choose>
					<xsl:when test="$type = 'link' or @type = 'link'">
						<li class="dropdown" crid="{@crid}">
							<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
								<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
									<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
								</xsl:for-each>
								<xsl:if test="@icon!=''">
									<i class="{@icon}"></i>
								</xsl:if>
								<xsl:value-of select="@label"></xsl:value-of>
								<i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu">
								<xsl:if test="$side = 'left' or @side = 'link'">
									<xsl:attribute name="class">dropdown-menu pull-right</xsl:attribute>
								</xsl:if>
								<xsl:for-each select="cooperop-buttongroupbutton">
									<li>
										<a href="javascript:;" action="{@action}">
											<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
												<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
											</xsl:for-each>
											<xsl:if test="@icon!=''">
												<i class="{@icon}"></i>
											</xsl:if>
											<xsl:value-of select="@label"></xsl:value-of>
										</a>
									</li>
								</xsl:for-each>
							</ul>
						</li>
					</xsl:when>
					<xsl:otherwise>
						<div class="btn-group pull-right" crid="{@crid}">
							<button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
								data-delay="1000" data-close-others="true" aria-expanded="false">
								<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
									<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
								</xsl:for-each>
								<xsl:if test="@icon!=''">
									<i class="{@icon}"></i>
								</xsl:if>
								<xsl:value-of select="@label"></xsl:value-of>
								<i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<xsl:for-each select="cooperop-buttongroupbutton">
									<li>
										<a href="javascript:;" action="{@action}">
											<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
												<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
											</xsl:for-each>
											<xsl:if test="@icon!=''">
												<i class="{@icon}"></i>
											</xsl:if>
											<xsl:value-of select="@label"></xsl:value-of>
										</a>
									</li>
								</xsl:for-each>
							</ul>
						</div>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<ul class="nav navbar-nav {@class}" crid="{@crid}">
					<xsl:if test="$side = 'right' or @side = 'right'">
						<xsl:attribute name="class">nav navbar-nav navbar-right <xsl:value-of select="@class"></xsl:value-of></xsl:attribute>
					</xsl:if>
					
					<xsl:choose>
					<xsl:when test="$type = 'link' or @type = 'link'">
						<li class="dropdown" crid="{@crid}">
							<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
								<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
									<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
								</xsl:for-each>
								<xsl:if test="@icon!=''">
									<i class="{@icon}"></i>
								</xsl:if>
								<xsl:value-of select="@label"></xsl:value-of>
								<i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu">
								<xsl:if test="$side = 'left' or @side = 'link'">
									<xsl:attribute name="class">dropdown-menu pull-right</xsl:attribute>
								</xsl:if>
								<xsl:for-each select="cooperop-buttongroupbutton">
									<li>
										<a href="javascript:;" action="{@action}">
											<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
												<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
											</xsl:for-each>
											<xsl:if test="@icon!=''">
												<i class="{@icon}"></i>
											</xsl:if>
											<xsl:value-of select="@label"></xsl:value-of>
										</a>
									</li>
								</xsl:for-each>
							</ul>
						</li>
					</xsl:when>
					<xsl:otherwise>
						<div class="btn-group pull-right" crid="{@crid}">
							<button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
								data-delay="1000" data-close-others="true" aria-expanded="false">
								<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
									<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
								</xsl:for-each>
								<xsl:if test="@icon!=''">
									<i class="{@icon}"></i>
								</xsl:if>
								<xsl:value-of select="@label"></xsl:value-of>
								<i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<xsl:for-each select="cooperop-buttongroupbutton">
									<li>
										<a href="javascript:;" action="{@action}">
											<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
												<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
											</xsl:for-each>
											<xsl:if test="@icon!=''">
												<i class="{@icon}"></i>
											</xsl:if>
											<xsl:value-of select="@label"></xsl:value-of>
										</a>
									</li>
								</xsl:for-each>
							</ul>
						</div>
					</xsl:otherwise>
					</xsl:choose>
				</ul>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="cooperop-toolbar-button">
		<xsl:param name="type"></xsl:param>
		<xsl:choose>
			<xsl:when test="$type = 'button'">
				<button type="button" class="btn btn-sm btn-default" action="{@action}">
					<xsl:if test="@icon!=''">
						<i class="{@icon}"></i>
					</xsl:if>
					<xsl:value-of select="@label"></xsl:value-of>
				</button>
			</xsl:when>
			<xsl:otherwise>
				<li>
					<a href="javascript:;" action="{@action}">
						<xsl:if test="@icon!=''">
							<i class="{@icon}"></i>
						</xsl:if>
						<xsl:value-of select="@label"></xsl:value-of>
					</a>
				</li>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="cooperop-toolbar-split">
		<li class="divider {@color}"></li>
	</xsl:template>
</xsl:stylesheet>