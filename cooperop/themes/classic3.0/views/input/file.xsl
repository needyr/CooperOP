<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-file-link">
		<xsl:if test="descendant::cooperop-file">
			<link href="{$contextpath}/theme/plugins/jquery-file-upload/css/jquery.fileupload.css?m={$module}" rel="stylesheet"/>
			<link href="{$contextpath}/theme/plugins/jquery-file-upload/css/jquery.fileupload-ui.css?m={$module}" rel="stylesheet"/>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/vendor/tmpl.min.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/vendor/load-image.min.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-image.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-audio.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-video.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/file.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-file">
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
				<!-- <input class="form-control" type="file" ctype="file" name="{@name}" accept="*"/> -->
				<div ctype="file" class="fileupload">
					<xsl:for-each select="(./@*[name()!='draggable'][name()!='ondragstart'][name()!='value'][name()!='cols']
						[name()!='ondrop'][name()!='ondragover'][name()!='label'][name()!='crid'])">
							<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
					</xsl:for-each>
					<xsl:if test="@islabel = 'true'">
						<xsl:attribute name="style">border:none;</xsl:attribute>
					</xsl:if>
					<input type="hidden" class="file_file" name="{@name}" value="{@value}"/>
					<xsl:choose>
						<xsl:when test="@islabel = 'true' or @addable = 'false'">
						</xsl:when>
						<xsl:otherwise>
							<div class="fileupload-buttonbar">
								<span class="btn btn-sm green fileinput-button">
								<i class="fa fa-plus"></i>
								<span>添加文件</span>
								<input type="file" ccinput="file" name="{@name}_files" multiple="" title="添加文件" />
								</span>
								<xsl:choose>
									<xsl:when test="@autoupload = 'true'">
									</xsl:when>
									<xsl:otherwise>
										<button type="submit" class="btn btn-sm blue start">
										<i class="fa fa-upload"></i>
										<span>
										开始上传</span>
										</button>
									</xsl:otherwise>
								</xsl:choose>
								<span class="fileupload-process">
								</span>
							</div>
						</xsl:otherwise>
					</xsl:choose>
					<table role="presentation" class="table table-striped clearfix">
						<tbody class="files">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>