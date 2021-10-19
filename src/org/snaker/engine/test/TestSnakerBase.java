/* Copyright 2013-2015 www.snakerflow.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.engine.test;

import java.io.File;

import org.snaker.engine.IProcessService;
import org.snaker.engine.IQueryService;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.cfg.Configuration;

import cn.crtech.cooperop.bus.rdms.ConnectionPool;
import cn.crtech.cooperop.bus.util.GlobalVar;

/**
 * 测试辅助基类，提供execute的递归方法及SnakerEngine实例
 * @author yuqs
 * @since 1.0
 */
public class TestSnakerBase {
	protected String processId;
	protected SnakerEngine engine = getEngine();
	protected IProcessService processService = engine.process();
	protected IQueryService queryService = engine.query();
	protected SnakerEngine getEngine() {
		try {
			File file = new File("."); 
			GlobalVar.init(file.getAbsolutePath(), file.getAbsolutePath() + "/resources/conf.properties");
			ConnectionPool.init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return new Configuration().buildSnakerEngine();
	}
}
