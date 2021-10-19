package cn.crtech.precheck.ipc.service;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface HYTInterface extends Library{
	//public static HYTInterface instanceDll =(HYTInterface)Native.load(HYTInterface.class.getResource("HYTCallService.dll").getFile(),  HYTInterface.class);
		public static HYTInterface instanceDll =(HYTInterface)Native.load("HYTCallService",  HYTInterface.class);
		  
		 /*
		   * @deprecated 接口初始化         
		   * 
		   * @param server 服务器名或ip地址，包括端口信息，用冒号(:)分割，如192.168.1.2:8765。若使用慧药通默认端口，则可不填端口信息，如192.168.1.2
		   * @param hospitalcode 慧药通为每个医疗机构分配的机构代码。如不填，将使用默认代码。建议使用。如区域平台系统上，则必须输入相应的代码，否则分析结果将可能不准确
		   * @param timeout 系统分析超时时间，单位秒。系统默认5秒
		   */
		  Pointer HYT_Initialize(String server,String hospitalcode,int timeout);
		  
		  int HYT_GetMedInstruction(Pointer session, String medicinecode, String medicinename, String medspecific);
		  	 /* 
		      * @deprecated 输入处方及病人基本信息      * 
		      * 在同一个分析过程当中，多次调用输入用药基本信息时，将只保留最后一次的信息。
		      * 在分析完一次的用药情况之后，直接调用输入用药基本信息时，将会把之前的分析所使用的资源自动释放
		      * @param sessionHYT_Initialize返回的连接句柄
		      * @param registerno当次门急诊处方的挂号号；或住院期间的住院号
		      * @param prescriptionno当次门急诊处方的处方号；或住院期间当次医嘱的医嘱号
		      * @param prescriptiontype当次分析的类别，1为门诊，2为住院，3为急诊；不填或其他均为0
		      * @param prescriptiondate当次处方或医嘱的时间，在合理用药当中一般为当前时间。格式采用YYYY-MM-DD
		      * @param cost当次门急诊用药的总金额或住院期间总费用。对于住院处方为必填
		      * @param indate住院医嘱的入院时间。对于门急诊可不填。格式为YYYY-MM-DD hh:mm:ss
		      * @param outdate住院医嘱的出院时间。对于门急诊可不填。格式为YYYY-MM-DD hh:mm:ss
		      * @param doctorno医生在HIS系统中的医生编号
		      * @param doctorname医生姓名
		      * @param officename医生所在科室的科室名
		      * @param patientno病人编号
		      * @param patientname病人姓名
		      * @param patienttype病人类别，如自费，医保，绿色通道等
		      * @param birthday病人出生日期
		      * @param gender病人性别。’M’或’男’为男性；’F’或’女’为女性；’N’为其他，如不填或填写其他值，系统会检测为’N’
		      * @param height病人身高，系统单位厘米（CM）
		      * @param weight病人体重，系统单位公斤（KG）
		      * @return添加基本信息成功或失败。 
		       */
		  int HYT_PrescriptionInfoExt(Pointer session, String registerno, String prescriptionno, int prescriptiontype,
	             String prescriptiondate,  double cost, String indate,  String outdate,
	             String doctorno, String doctorname, String officename, String patientno,
	             String patientname,
	             String patienttype,
	             String birthday,
	             String gender,
	             int height,
	             double weight,
	             String opsid,
	             String opsname,
	             String qktype,
	             String wardcode,
	             String bednum,
	             String ContactWay,
	             String StickCount,
	             String s3,
	             String s4,
	             String s5,
	             String s6,
	             String s7,
	             String s8,
	             String s9,
	             String s10
	             );
		  /*
	     * @deprecated 输入用药信息
	     * 
	     * 在同一个分析过程当中，由于一般有多个用药，所以HIS系统需要在提交分析之前多次调用接口函数，
	     * 将所有需要进行分析的用药详细信息输入分析系统。
	     * 在分析完一次的用药情况之后，直接调用输入用药详细信息时，将会把前一次的分析所使用的资源自动释放
	     * 
	     * @param session HYT_Initialize返回的连接句柄 
	     * @param medicinecode HIS系统中使用的药品代码 
	     * @param medicinename 处方或医嘱中的药品名 
	     * @param specification 药品规格 
	     * @param ordertype 是否长期医嘱。0为临时医嘱，1为长期医嘱。门急诊时这里使用默认值0 
	     * @param doseformname 该药品的用法，如口服，注射，静注等 
	     * @param frequencycode 用药频次代码，如qid，tid，bid等 
	     * @param frequencyname 用药频次对应的中文，如一天一次，一天两次等等 
	     * @param unit 药品单位，使用药品最小包装单位，如片，袋，粒，支等 
	     * @param price 药品价格，单位为unit中的单位 
	     * @param count 药品数量，单位为unit中的单位 
	     * @param dosage 用药单次剂量，使用最小包装单位 
	     * @param dosagename 药品计量单位 
	     * @param groupno 用药分组组号 
	     * @param beginusingtime 开始用药时间。对于门急诊处方可不填。格式为YYYY-DD-MM hh:mm:ss 
	     * @param endusingtime 停用药时间。对于门急诊处方可不填。格式为YYYY-DD-MM hh:mm:ss 
	     * @return添加用药信息成功或失败。          
	      */
		  int HYT_MedicineInfoExt(Pointer session,
	             String medicinecode,
	             String medicinename,
	             String specification,
	             int ordertype,
	             String doseformname,
	             String frequencycode,
	             String frequencyname,
	             String unit,
	             double price,
	             double count,
	             double dosage,
	             String dosagename,
	             String groupno,
	             String beginusingtime,
	             String endusingtime,
	             String medcinedepartment, String precodeitem,
	             String s2,
	             String s3,
	             String s4,
	             String s5
	             );
		  /*
	     * @deprecated 输入诊断信息
	     * 
	     * 在同一个分析过程当中，可能有多个诊断，所以HIS系统可以在提交分析之前多次调用接口函数，将所有当前病人的诊断信息输入分析系统。
	     * 在分析完一次的用药情况之后，直接调用输入诊断详细信息时，将会把前一次的分析所使用的资源自动释放
	     * 
	     * @param session HYT_Initialize返回的连接句柄 
	     * @param diagnosecode 诊断代码，这里用标准ICD10代码，用以之后的用药分析 
	     * @param diagnosename 诊断名 
	     * @return添加诊断信息成功或失败。   
	     * 
	     */
		  int HYT_DiagnoseInfo(Pointer session,
	             String diagnosecode,
	             String diagnosename);
		  /*
	     * 
	     * @deprecated 输入手术信息      
	     * 在同一个分析过程当中，可能有多个手术，所以HIS系统可以在提交分析之前多次调用接口函数，将所有当前病人的诊断信息输入分析系统。
	     * 在分析完一次的用药情况之后，直接调用输入手术详细信息时，将会把前一次的分析所使用的资源自动释放
	     *
	     * @param session HYT_Initialize返回的连接句柄 
	     * @param opsid 手术代码
	     * @param opsname 手术名
	     * @param begintime 手术开始时间
	     * @param endtime 手术结束时间
	     * @return添加手术信息成功或失败。   
	     * 
	     */
		  int HYT_OpsInfo(Pointer session,
	             String opsid,
	             String opsname,
	             String qktype,
	             String begintime,
	             String endtime);
		  /*
	     * @deprecated 输入过敏史信息
	     * 
	     * 在同一个分析过程当中，可能病人有过敏史，而且可能有多个过敏史信息，
	     * 所以HIS系统可以在提交分析之前多次调用接口函数，将所有当前病人的过敏史信息提交到分析系统。
	     * 在分析完一次的用药情况之后，直接调用输入过敏史信息时，将会把前一次的分析所使用的资源自动释放
	     * 
	     * @param session HYT_Initialize返回的连接句柄 
	     * @param allergycode 过敏源代码 
	     * @param allergyname 过敏源名称 
	     * @return添加过敏信息成功或失败。  
	      * 
	      */
		  int HYT_AllergyInfo(Pointer session,
	             String allergycode,
	             String allergyname);
		  /*
	     * @deprecated 输入病生理状态信息
	     * 
	     * 在同一个分析过程当中，可能病人有病生理状态，而且可能有多个病生理状态信息，
	     * 所以HIS系统可以在提交分析之前多次调用接口函数，将所有当前病人的病生理状态信息提交到分析系统。
	     * 在分析完一次的用药情况之后，直接调用输入病生理状态信息时，将会把前一次的分析所使用的资源自动释放
	     * 
	     * @param session HYT_Initialize返回的连接句柄 
	     * @param physiologycode 病生理状态代码 P001	妊娠期,P002	哺乳期,P003	肝功能不全,P004	肾功能不全,P005	严重肝功能不全,P006	严重肾功能不全 
	     * @param physiologyname 病生理状态名称 
	     * @return添加病生理状态信息成功或失败。 
	      * 
	      */
		  int HYT_PhysiologyInfo(Pointer session,
	             String physiologycode,
	             String physiologyname);
		  /*
	     * @deprecated 慧药通合理用药系统分析处方
	     * 在输入完分析所需要的信息之后，外部程序需要调用该接口提交信息通知合理用药分析系统进行分析，在分析完之后，系统将发回分析结果
	     * @param session HYT_Initialize返回的连接句柄 
	     * @param saveresult 是否需要服务器将分析结果保存 
	     * @param showresult 是否在客户端使用慧药通的分析结果现实方式自动显示错误信息。0为不使用慧药通提供的弹出框提示；1为慧药通默认提示框（先在窗口右下角弹出消息框，如果点击消息框，则打开详细问题显示窗口）；2为直接打开详细问题窗口 
	     * @param analysislist  
	     * @return分析系统分析成功或失败。 
	     */
		  int HYT_Analyze(Pointer session,
	             int saveresult,
	             int showresult,
	             String analysislist);
		  /*
	     * @deprecated 获取分析结果
	     * 
	     * 在系统分析完用药情况后，系统将返回分析的结果。
	     * 外部程序（HIS）系统可以通过反复调用该接口来获取分析结果的详细信息，从而自定义的方式显示分析结果
	     * 
	     * @param session HYT_Initialize返回的连接句柄 
	     * @param type 当前错误信息的类别
	     * 1	循证医学问题 ， 2	适应症问题 ， 3	相互作用问题 , 4	配伍禁忌问题 
	     * 5	禁忌症问题 , 6	新生儿用药问题 ,7	老年人用药问题 ,8	妊娠期用药问题 
	     * 9	过量用药问题 , 10	用药频次检测问题  , 11	给药途径问题 , 13	重复用药问题
	     * 14	婴幼儿用药问题 ,15	儿童用药问题 , 16	哺乳期用药问题 , 17	肝功能不全用药问题
	     * 18	肾功能不全用药问题  , 19	严重肝功能不全用药问题 , 20	严重肾功能不全用药问题
	     *   
	     * @param severity 当前问题的严重级别，1为最严重，4为一般 
	     * @param message 当前问题的详细信息描述 
	     * @param summary 当前问题的概要描述 
	     * @param reference 参考资料信息 
	     * @return获取信息是否成功。  
	      * 
	      */
		  int HYT_GetAnalyzeResultExByLevel(Pointer session,
				  IntByReference type,
				  IntByReference severity,
				  IntByReference holdup,
				  IntByReference msgtype,
				  Pointer message,
				  Pointer summary,
				  Pointer reference,
				  Pointer precodeitem1,
				  Pointer precodeitem2);
		  
	     /*
		  * 释放内存
		  */
		 int HYT_UnInitialize(Pointer session);
}
