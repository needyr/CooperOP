<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template name="cooperop-image-link">
		<xsl:if test="descendant::cooperop-image">
			<xsl:choose>
				<xsl:when test="descendant::cooperop-file">
				</xsl:when>
				<xsl:otherwise>
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
				</xsl:otherwise>
			</xsl:choose>
			<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/image.js"></script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="cooperop-image">
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
				<!-- <input class="form-control" ctype="image" type="file" name="{@name}" accept="image/*"/> -->
				<div ctype="image" class="imageupload">
					<xsl:for-each select="(./@*[name()!='draggable'][name()!='ondragstart'][name()!='value'][name()!='cols']
						[name()!='ondrop'][name()!='ondragover'][name()!='label'][name()!='crid'])">
							<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
					</xsl:for-each>
					<input type="hidden" class="imageupload_file" name="{@name}" value="{@value}"/>
					<ul class="files">
					</ul>
					<xsl:choose>
						<xsl:when test="@islabel = 'true' or @addable = 'false'">
						</xsl:when>
						<xsl:otherwise>
							<div class="fileupload-buttonbar">
								<span class="fileinput-button" title="添加文件">
								<i class="fa fa-plus"></i>
								<input type="file" ccinput="image" name="files[]" multiple="" title="添加文件" />
								</span>
								<span class="fileupload-process">
								</span>
							</div>
						</xsl:otherwise>
					</xsl:choose>
				</div>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>