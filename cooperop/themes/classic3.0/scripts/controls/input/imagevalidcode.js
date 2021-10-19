$(document).ready(function() {
	$(document).find("[cooperoptype='imagevalidcode']").each(function() {
		var $this = $(this);
		$this.find("img[cooperoptype='imagevalidcode_img']").attr("title", "看不清楚？点击切换");
		$this.find("img[cooperoptype='imagevalidcode_img']").attr("src", cooperopcontextpath + "/validimage.png?t=" + Math.random());
		$this.find("input[cooperoptype='imagevalidcode_input']").attr("maxLength", 6);
		$this.find("img[cooperoptype='imagevalidcode_img']").click(function() {
			$this.find("img[cooperoptype='imagevalidcode_img']").attr("src", cooperopcontextpath + "/validimage.png?t=" + Math.random());
			$this.find("input[cooperoptype='imagevalidcode_input']").val("");
		});
	});
});