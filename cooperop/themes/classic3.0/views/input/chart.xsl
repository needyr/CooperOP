<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-chart-link">
		<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/chart.js"></script>
	</xsl:template>
	<xsl:template match="cooperop-chart">
				<div class="cols{@cols} nolabel" crid="{@crid}" >
					<xsl:if test="@cols = 3 ">
						<xsl:attribute name="style">width:75%;float:left;</xsl:attribute>
					</xsl:if>
					<xsl:if test="@cols = 4 ">
						<xsl:attribute name="style">width:100%;float:left;</xsl:attribute>
					</xsl:if>
					<xsl:if test="@cols = 1 ">
						<xsl:attribute name="style">width:25%;float:left;</xsl:attribute>
					</xsl:if>
					<xsl:if test="@cols = 2 ">
						<xsl:attribute name="style">width:50%;float:left;</xsl:attribute>
					</xsl:if>
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
				<!-- <div style="float: right"><button class="xuanzhuan">旋转</button></div> -->
				<div class="chart_container" ctype="chart" >
					<xsl:for-each select="(./@*[name()='maxlength' and . &gt; 0]) | 
						(./@*[name()!='defaultValue'][name()!='maxlength'][name()!='draggable'][name()!='ondragstart']
						[name()!='ondrop'][name()!='ondragover'][name()!='readonly'][name()!='required'][name()!='crid'][name()!='value'])">
						<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
					</xsl:for-each>
					<xsl:choose>
						<xsl:when test="@is_span != ''">
							<xsl:attribute name="class">chart-span</xsl:attribute>
							<xsl:if test="@height != '' ">
								<xsl:attribute name="style">height:<xsl:value-of select="@height"></xsl:value-of>px;</xsl:attribute>
							</xsl:if>
							<div class="chart-div">
								<div class="chart-title">区块统计<xsl:value-of select="@flag"></xsl:value-of></div>
								<div class="cspan">
									<div class="cspan-head">
										区块头部
									</div>
									<div class="cspan-content">
										54,234
									</div>
									<div class="cspan-footer">
										本期同比增幅 15.3%
									</div>
								</div>
							</div>
						</xsl:when>
						<xsl:otherwise>
							<xsl:choose>
								<xsl:when test="@label != ''">
									<label class="control-label demo-chart-label" style="display: block;text-align: center;">
										<xsl:attribute name="title"><xsl:value-of select="@label"></xsl:value-of></xsl:attribute>
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
										<span class="demo_chart" type="text" value="{@defaultValue}">
											<xsl:if test="@cols &gt; 2">
												<img src="{$contextpath}/theme/img/chart_d.png" style="width:100%;"/>
											</xsl:if>
											<xsl:if test="@cols &lt; 3">
												<img src="{$contextpath}/theme/img/chart_x.png" style="width:100%;"/>
											</xsl:if>
										</span>
									</xsl:otherwise>
								</xsl:choose>
							</div>
						</xsl:otherwise>
					</xsl:choose>
					
				</div>
			</div>
	</xsl:template>
</xsl:stylesheet>