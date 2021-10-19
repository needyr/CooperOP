<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-textfield-link">
		<xsl:if test="descendant::cooperop-textfield">
			<link href="{$contextpath}/theme/css/components/input/textfield.css?_v={$_v}" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/textfield.js?_v={$_v}"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-textfield">
		<xsl:choose>
			<xsl:when test="parent::cooperop-tablefield or parent::cooperop-tabledata">
				<div class="control-content">
					<xsl:if test="@ishidden = 'true'">
						<xsl:attribute name="style">display:none;</xsl:attribute>
					</xsl:if>
					<xsl:if test="(@dbl_action != '' or @dblaction != '')">
						<xsl:attribute name="class">control-content dblclick-open</xsl:attribute>
					</xsl:if>
					<xsl:if test="@icon != ''">
						<xsl:attribute name="class">control-content has-icon</xsl:attribute>
					</xsl:if>
					<xsl:if test="(@dbl_action != '' or @dblaction != '') and @icon != ''">
						<xsl:attribute name="class">control-content dblclick-open has-icon</xsl:attribute>
					</xsl:if>
					<xsl:if test="@required ='true' or @required ='required'">
						<xsl:attribute name="class">control-content required</xsl:attribute>
						<xsl:if test="(@dbl_action != '' or @dblaction != '')">
							<xsl:attribute name="class">control-content required dblclick-open</xsl:attribute>
						</xsl:if>
						<xsl:if test="@icon != ''">
							<xsl:attribute name="class">control-content required has-icon</xsl:attribute>
						</xsl:if>
						<xsl:if test="(@dbl_action != '' or @dblaction != '') and @icon != ''">
							<xsl:attribute name="class">control-content required dblclick-open has-icon</xsl:attribute>
						</xsl:if>
						<i class="control-required">*</i>
					</xsl:if>
					<xsl:if test="@icon != '' and @islabel !='true'">
						<i class="control-icon {@icon}"></i>
					</xsl:if>
					<xsl:if test="(@dbl_action != '' or @dblaction != '') and @islabel !='true'">
						<button type="button" class="open-icon">
							<i class="fa fa-ellipsis-h"></i>
						</button>
					</xsl:if>
					
					<xsl:choose>
					<xsl:when test="@is_href != ''">
						<a href="javascript:void(0);" onclick="showhref_(this);" is_href="{@is_href}" intable="intable">
						<xsl:value-of select="@value"></xsl:value-of>
						</a>
					</xsl:when>
					<xsl:otherwise>
						<input type="text" class="form-control" value="{@defaultValue}" ctype="textfield" intable="intable">
							<xsl:if test="@datatype = 'password'">
								<xsl:attribute name="type">password</xsl:attribute>
								<xsl:attribute name="ctype">password</xsl:attribute>
							</xsl:if>
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
					</xsl:otherwise>
					</xsl:choose>
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
					<div class="control-content">
						<xsl:if test="(@dbl_action != '' or @dblaction != '')">
							<xsl:attribute name="class">control-content dblclick-open</xsl:attribute>
						</xsl:if>
						<xsl:if test="@icon != ''">
							<xsl:attribute name="class">control-content has-icon</xsl:attribute>
						</xsl:if>
						<xsl:if test="(@dbl_action != '' or @dblaction != '') and @icon != ''">
							<xsl:attribute name="class">control-content dblclick-open has-icon</xsl:attribute>
						</xsl:if>
						<xsl:if test="@icon != '' and @islabel !='true'">
							<i class="control-icon {@icon}"></i>
						</xsl:if>
						<xsl:if test="(@dbl_action != '' or @dblaction != '') and @islabel !='true'">
							<button type="button" class="open-icon">
								<i class="fa fa-ellipsis-h"></i>
							</button>
						</xsl:if>
						<xsl:choose>
							<xsl:when test="@islabel = 'true'">
								<p class="form-control-static">
									<xsl:if test="@dictionary != ''">
										<xsl:attribute name="dictionary"><xsl:value-of select="@dictionary"></xsl:value-of></xsl:attribute>
									</xsl:if>
									<xsl:if test="@datatype != ''">
										<xsl:attribute name="datatype"><xsl:value-of select="@datatype"></xsl:value-of></xsl:attribute>
									</xsl:if>
									<xsl:if test="@format != ''">
										<xsl:attribute name="format"><xsl:value-of select="@format"></xsl:value-of></xsl:attribute>
									</xsl:if>
									<xsl:value-of select="."/>
									<xsl:choose>
										<xsl:when test="@value != ''">
											<xsl:choose>
												<xsl:when test="@is_href != ''">
													<a href="javascript:void(0);" onclick="showhref_(this);" is_href="{@is_href}" >
													<xsl:value-of select="@value"></xsl:value-of>
													</a>
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of select="@value"></xsl:value-of>
												</xsl:otherwise>
											</xsl:choose>
										</xsl:when>
										<xsl:otherwise>
											<xsl:if test="@defaultValue != ''"><xsl:value-of select="@defaultValue"></xsl:value-of></xsl:if>
										</xsl:otherwise>
									</xsl:choose>
								</p>
							</xsl:when>
							<xsl:otherwise>
								<input type="text" class="form-control" value="{@defaultValue}" ctype="textfield">
									<xsl:if test="@datatype = 'password'">
										<xsl:attribute name="type">password</xsl:attribute>
										<xsl:attribute name="ctype">password</xsl:attribute>
									</xsl:if>
									<xsl:if test="@required ='true' or @required ='required'">
										<xsl:attribute name="required">required</xsl:attribute>
									</xsl:if>
									<xsl:if test="@readonly ='true'">
										<xsl:attribute name="readonly">readonly</xsl:attribute>
									</xsl:if>
									<xsl:attribute name="value"><xsl:value-of select="@value"></xsl:value-of><xsl:value-of select="."/></xsl:attribute>
									<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
									(./@*[name()!='defaultValue'][name()!='maxlength'][name()!='draggable'][name()!='ondragstart']
									[name()!='ondrop'][name()!='ondragover'][name()!='readonly'][name()!='required'][name()!='crid'])">
										<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
									</xsl:for-each>
								</input>
							</xsl:otherwise>
						</xsl:choose>
					</div>
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>