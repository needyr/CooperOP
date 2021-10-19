$.fn.extend({
	"ccinit_richeditor": function() {
		var toolbars = {
			"full": [
			          // [groupName, [list of button]]
			          ['misc', ['fullscreen', 'codeview', 'undo', 'redo']],
			          ['style', ['style']],
			          ['fontname', ['fontname']],
			          ['fontsize', ['fontsize']],
			          ['color', ['color']],
			          ['special', ['bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']],
			          ['height', ['height']],
			          ['para', ['ul', 'ol', 'paragraph']],
			          ['other', ['picture', ['link'], ['video'], ['hr']]],
			          ['help', ['help']]
			        ],
			"basic": [
			          ['style', ['style']],
			          ['fontname', ['fontname']],
			          ['fontsize', ['fontsize']],
			          ['color', ['color']],
			          ['special', ['bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']],
			          ['height', ['height']],
			          ['para', ['ul', 'ol', 'paragraph']]
			        ],
			"simple": [
				          ['fontname', ['fontname']],
				          ['fontsize', ['fontsize']],
				          ['color', ['color']],
				          ['special', ['bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']]
			        ],
			"none": []
		};
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		$this.summernote({
			lang: 'zh-CN',
			disableDragAndDrop: true,
			toolbar: toolbars[$this.attr("toolbar") || "basic"],
			fontNames: ['宋体', '黑体', '楷体', '隶书', '微软雅黑', '微软雅黑 Light', '新宋体', '幼圆', 
			            'Microsoft YaHei', 'Helvetica Neue'],
			height: $this.attr("height") || "160"
		});
		if ($this.attr("toolbar") == "none") {
			$this.next(".note-editor").find(".note-toolbar").hide();
		}
		if ($this.attr("resize") == "false") {
			$this.next(".note-editor").find(".note-statusbar").hide();
		}
		$this.next(".note-editor").find("input, textarea").attr("ccinput", "ccinput");
		$this.attr("cinited", "cinited");
	},
	"getData_richeditor": function() {
		var $this = this;
		var value = $this.code();
		var d = {};
		d[$this.attr("name")] = value;
		return d;
	},
	"setData_richeditor": function(data) {
		var $this = this;
		$this.code(data);
	}
});
