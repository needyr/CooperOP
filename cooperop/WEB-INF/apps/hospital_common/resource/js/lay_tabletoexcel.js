/** layui导出数据
  *  参数 | 格式 | 说明
 * cols,[],layui列属性
 * data,[],处理好的数据
 * foot,string,导出附加数据
 * fileName,string,文件名称
 * columsName,[],合并列名称
 * columsIndex,[],合并列索引
 * check_column_all,Boolean,是否检查所有列:为true将去检查是否所有列值都相同,才进行合并
  *  注意：对于layui列属性中的templet，仅仅支持scrip写法的导出支持
 */
function tabletoexcel(cols, data, foot, fileName, columsName,columsIndex,check_column_all){
	var fileName = fileName?fileName:'noname';
	var excelFile = [];
	//excelFile.push((th + excelContent);
	var data_lie = []
	excelFile.push('<table width="10%" border="1"><thead>');
	for(var i=0;i<cols.length;i++){
		excelFile.push('<tr>');
		var col = cols[i];
		for(var j=0;j<col.length;j++){
			var fie = {}
			if(col[j].colspan){
				if(+col[j].colspan <= 1){
					fie.field = col[j].field;
					if(col[j].templet){
						fie.templet = col[j].templet;
					}
					data_lie.push(fie)
				}
			}else{
				fie.field = col[j].field;
				if(col[j].templet){
					fie.templet = col[j].templet;
				}
				data_lie.push(fie)
			}
			excelFile.push('<th style="vnd.ms-excel.numberformat:@" data-field="'+col[j].field+'"');
			if(col[j].colspan){
				excelFile.push(' colspan="'+col[j].colspan+'" ')
			}
			if(col[j].rowspan){
				excelFile.push(' rowspan="'+col[j].rowspan+'" ')
			}
			excelFile.push('>');
			excelFile.push(col[j].title);
			excelFile.push('</th>');
		}
		excelFile.push('</tr>');
	}
	excelFile.push('</thead>');
	excelFile.push('<tbody>');
	for(var i=0;i<data.length;i++){
		excelFile.push('<tr>');
		for(var j=0;j<data_lie.length;j++){
			excelFile.push('<td style="vnd.ms-excel.numberformat:@');
			excelFile.push('">');
			//注意temple参数计算
			if(data_lie[j].templet){
				var _td_data = data[i];
				var funcStr = data_lie[j].templet;
				var funcTest = new Function('return '+funcStr);
				excelFile.push(funcTest()(_td_data));
			}else{
				excelFile.push(data[i][data_lie[j].field]?data[i][data_lie[j].field]:'');
			}
			
			excelFile.push('</td>');
		}
		excelFile.push('</tr>');
	}
	excelFile.push("</tbody></table>");
	
	var dd = columsName?excel_merge(excelFile.join(''),data, columsName,columsIndex,check_column_all):excelFile.join('');
	var result = [];
	result.push("<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>");
	result.push("<head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head>");
	result.push("<body>");
	result.push("<body>");
	result.push('<table width="10%" border="1">');
	result.push(dd);
	result.push("</table>");
	result.push("</body>");
	result.push("</html>");
	//console.log(result.join(''))
	//var link = "data:application/vnd.ms-excel;base64," + window.btoa(unescape(encodeURIComponent(excelFile)));
	var blob = new Blob([result.join('')], {type: "data:application/vnd.ms-excel;base64"});  	
	//解决中文乱码问题
	blob =  new Blob([String.fromCharCode(0xFEFF), blob], {type: blob.type});  
	var a = document.createElement("a");
	a.download = fileName + ".xls";
	//a.href = link;
	a.href = window.URL.createObjectURL(blob);
	document.body.appendChild(a);
	a.click();
	document.body.removeChild(a);
}
 
/**
 * columsName,[],合并列名称
 * columsIndex,[],合并列索引
 * check_column_all,Boolean,是否检查所有列:为true将去检查是否所有列值都相同,才进行合并
 */
function merge(res, curr, count, columsName,columsIndex,check_column_all) {
    var data = res;
    for (var k = 0; k < columsName.length; k++)//这里循环所有要合并的列
        {
	    var mark = 1;
	    var mergeIndex = 0;//定位需要添加合并属性的行数
        var trArr = $(".layui-table-body>.layui-table").find("tr");//所有行
        for (var i = 1; i < data.length; i++) { //这里循环表格当前的数据
	   	    var check = false;
			if(check_column_all){
				for (var j = 0; j < columsName.length; j++){
		   		    if(data[i][columsName[j]] === data[i - 1][columsName[j]]){
		   			    check = true;
		   		    }else{
		   			    check = false;
		   			    break;
		   		    }
		   	    }
			}else{
				if(data[i][columsName[k]] === data[i - 1][columsName[k]]){
	   			    check = true;
	   		    }else{
	   			    check = false;
	   		    }
			}
	        var tdCurArr = trArr.eq(i).find("td").eq(columsIndex[k]);//获取当前行的当前列
	        var tdPreArr = trArr.eq(mergeIndex).find("td").eq(columsIndex[k]);//获取相同列的第一列
	        if (check) { //后一行的值与前一行的值做比较，相同就需要合并
	            mark += 1;
	            tdPreArr.each(function () {//相同列的第一列增加rowspan属性
	            $(this).attr("rowspan", mark);
	            });
	            tdCurArr.each(function () {//当前行隐藏
	           	$(this).css("display", "none");
	            });
	            }else {
	            mergeIndex = i;
	            mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
	        }
        }
    }
}


/**
 * columsName,[],合并列名称
 * columsIndex,[],合并列索引
 * check_column_all,Boolean,是否检查所有列:为true将去检查是否所有列值都相同,才进行合并
 */
function excel_merge(jq_object,res, columsName,columsIndex,check_column_all) {
    var data = res;
	var _this = $($(jq_object).get(0))
    for (var k = 0; k < columsName.length; k++)//这里循环所有要合并的列
        {
	    var mark = 1;
	    var mergeIndex = 0;//定位需要添加合并属性的行数
        for (var i = 1; i < data.length; i++) { //这里循环表格当前的数据
	   	    var check = false;
			if(check_column_all){
				for (var j = 0; j < columsName.length; j++){
		   		    if(data[i][columsName[j]] === data[i - 1][columsName[j]]){
		   			    check = true;
		   		    }else{
		   			    check = false;
		   			    break;
		   		    }
		   	    }
			}else{
				if(data[i][columsName[k]] === data[i - 1][columsName[k]]){
	   			    check = true;
	   		    }else{
	   			    check = false;
	   		    }
			}
	        if (check) { //后一行的值与前一行的值做比较，相同就需要合并
	            mark += 1;
	            _this.find("tbody tr").eq(mergeIndex).find("td").eq(columsIndex[k]).each(function () {//相同列的第一列增加rowspan属性
	           		$(this).attr("rowspan", mark);
	            });
	            _this.find("tbody tr").eq(i).find("td").eq(columsIndex[k]).each(function () {//当前行隐藏
	           		$(this).attr("needdelete", "needdelete");
	            });
	            }else {
	            mergeIndex = i;
	            mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
	        }
        }
    }
	_this.find('tbody tr td[needdelete=needdelete]').each(function () {//当前行隐藏
   		$(this).remove();
    });
	return _this.html();
}