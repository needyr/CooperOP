<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-button-link">
		<xsl:if test="descendant::cooperop-button or descendant::cooperop-buttongroup">
			<link href="{$contextpath}/theme/css/components/input/button.css?_v={$_v}" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/button.js?_v={$_v}"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template name="cooperop-buttongroup-link">
		<xsl:if test="descendant::cooperop-button or descendant::cooperop-buttongroup">
			<link href="{$contextpath}/theme/css/components/input/button.css?_v={$_v}" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/button.js?_v={$_v}"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-buttongroup">
		<xsl:param name="type"></xsl:param>
		<xsl:param name="side"></xsl:param>
		<xsl:choose>
			<xsl:when test="parent::cooperop-toolbar-right or parent::cooperop-toolbar-left">
				<xsl:choose>
					<xsl:when test="$type = 'link' or @type = 'link'">
						<li class="dropdown" crid="{@crid}">
							<a href="javascript:;" class="dropdown-toggle {@size} {@class} {@color} " data-toggle="dropdown">
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
										<a href="javascript:;" style="{@size} {@class} {@color} " action="{@action}">
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
							<button type="button" class="btn btn-sm {@size} btn-default dropdown-toggle {@color} {@class}" data-toggle="dropdown" data-hover="dropdown"
								data-delay="1000" data-close-others="true" aria-expanded="false">
								<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
									<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
								</xsl:for-each>
								<xsl:if test="@icon!=''">
									<i class="{@icon}"></i>
								</xsl:if>
								<xsl:value-of select="@label"></xsl:value-of>
								<i class="cicon icon-dropdown3"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<xsl:for-each select="cooperop-buttongroupbutton">
									<li>
										<a href="javascript:;" style="{@size} {@class} {@color} " action="{@action}">
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
				<ul class="nav navbar-nav" crid="{@crid}">
					<xsl:if test="$side = 'right' or @side = 'right'">
						<xsl:attribute name="class">nav navbar-nav navbar-right <xsl:value-of select="@class"></xsl:value-of></xsl:attribute>
					</xsl:if>
					
					<xsl:choose>
					<xsl:when test="$type = 'link' or @type = 'link'">
						<li class="dropdown" crid="{@crid}">
							<a href="javascript:;" class="dropdown-toggle {@size} {@class} {@color}" data-toggle="dropdown">
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
										<a href="javascript:;" class=" {@size} {@class} {@color}" action="{@action}">
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
							<button type="button" class="btn btn-sm btn-default dropdown-toggle {@size} {@class} {@color}" data-toggle="dropdown" data-hover="dropdown"
								data-delay="1000" data-close-others="true" aria-expanded="false">
								<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
									<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
								</xsl:for-each>
								<xsl:if test="@icon!=''">
									<i class="{@icon}"></i>
								</xsl:if>
								<xsl:value-of select="@label"></xsl:value-of>
								<i class="cicon icon-dropdown3"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<xsl:for-each select="cooperop-buttongroupbutton">
									<li>
										<a href="javascript:;" class="{@size} {@class} {@color}" action="{@action}">
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
	<xsl:template match="cooperop-button">
		<xsl:param name="type"></xsl:param>
		<xsl:choose>
			<xsl:when test="$type = 'link' or @type = 'link'">
				<li  crid="{@crid}">
					<a href="javascript:;" action="{@action}" onclick="{@onclick}">
						<xsl:if test="@icon!=''">
							<i class="{@icon}"></i>
						</xsl:if>
						<xsl:value-of select="@label"></xsl:value-of>
						<xsl:apply-templates></xsl:apply-templates>
					</a>
				</li>
		</xsl:when>
		<xsl:otherwise>
		<button type="button" t="1" ctype="button" class="btn btn-sm {@size} btn-default {@color}" action="{@action}" crid="{@crid}">
			<xsl:if test="@icon = 'fa fa-search'">
				<xsl:attribute name="type">submit</xsl:attribute>
			</xsl:if>
			<xsl:if test="@color != ''">
				<xsl:attribute name="class">btn btn-sm <xsl:value-of select="@size"></xsl:value-of> btn <xsl:value-of select="@color"></xsl:value-of></xsl:attribute>
			</xsl:if>
			<xsl:for-each select="./@*[name()!='size'][name()!='color'][name()!='class'][name()!='action'][name()!='isdesign'][name()!='icon'][name()!='label'][name()!='crid']">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
			<xsl:if test="@type ='default'">
				<xsl:attribute name="type">submit</xsl:attribute>
			</xsl:if>
			<xsl:if test="@icon!=''">
				<i class="{@icon}"></i>
			</xsl:if>
			<xsl:value-of select="@label"></xsl:value-of>
			<xsl:apply-templates></xsl:apply-templates>
		</button>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>