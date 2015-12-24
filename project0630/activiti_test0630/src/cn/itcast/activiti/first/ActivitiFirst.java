package cn.itcast.activiti.first;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * <p>
 * Title: ActivitiFirst
 * </p>
 * <p>
 * Description:activiti的入门
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智.燕青
 * @date 2015-9-24
 * @version 1.0
 */
public class ActivitiFirst {
	
	private ProcessEngine processEngine;
	
	//让每个测试方法执行前构建processEngine
	@Before
	public void setUp()throws Exception{
		//构造 ProcessEngine
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		processEngine = processEngineConfiguration.buildProcessEngine();
		
	}

	@Test
	// 创建数据库
	public void createDB() throws Exception {
		// 从classpath下的activiti.cfg.xml中配置信息创建配置对象
		//默认读取id为processEngineConfiguration的 bean
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

		//如果id不为processEngineConfiguration，使用下边的方法，指定bean的name
//		ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(resource, beanName)
		
		//创建ProcessEngine
		//创建ProcessEngine对象时候 ，自动数据库环境
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
	}
	
	//部署流程定义
	@Test
	public void deployment()throws Exception{
		
		//使用RepositoryService的api
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//创建部署对象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		//将线下的bpmn和png文件部署到数据
		deploymentBuilder.addClasspathResource("diagram/purchasingflow4.bpmn");
		deploymentBuilder.addClasspathResource("diagram/purchasingflow4.png");
		
		//执行部署
		Deployment deployment = deploymentBuilder.deploy();
		
		System.out.println("部署id："+deployment.getId());
		
		
	}
	
	//启动一个流程实例 
	@Test
	public void testStartProcessInstance()throws Exception{
		
		//使用RuntimeService启动一个流程实例 
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//根据流程定义的key启动一个流程实例，启动了最新版本流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("purchasingflow");
		//定义流程变量variables
		//启动流程实例时设置一个变量
//		runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)
		System.out.println("流程实例id："+processInstance.getId());

		System.out.println("流程定义id："+processInstance.getProcessDefinitionId());
	}
	
	//查询待办任务
	@Test
	public void testfindTaskList()throws Exception{
		
		//任务负责人
		String assignee = "wangwu";
		
		//通过TaskService查询
		TaskService taskService = processEngine.getTaskService();
		//创建任务查询对象
		TaskQuery createTaskQuery = taskService.createTaskQuery();
		//设置查询条件
		
		//指定任务负责人
		createTaskQuery.taskAssignee(assignee);
		
		//执行查询
		List<Task> list = createTaskQuery.list();//查询全部
//		createTaskQuery.listPage(arg0, arg1);//分页查询
		//遍历任务
		for(Task task:list){
			System.out.println("=================");
			System.out.println("任务id："+task.getId());
			System.out.println("任务名称："+task.getName());
			System.out.println("任务负责人："+task.getAssignee());
			System.out.println("任务所属的流程实例："+task.getProcessInstanceId());
		}
	}
	//完成任务
	@Test
	public void testCompleteTask()throws Exception{
		
		//指定任务id
		String taskId  ="2802";
		//任务负责人
		String assignee = "lisi";
		//
		TaskService taskService = processEngine.getTaskService();
		//完成任务时api没有指定任务的完成人
		//使用任务id和任务负责人去查询任务，如果查询到说明当前任务是assignee指定的人负责
		TaskQuery createTaskQuery = taskService.createTaskQuery();
		createTaskQuery.taskAssignee(assignee);//指定任务负责人
		createTaskQuery.taskId(taskId);//指定任务id
		Task task = createTaskQuery.singleResult();
		if(task!=null){
			//说明assignee负责人负责此taskId任务
//			taskService.complete(taskId);
			
			//执行采购单审核时设置流程变量
			if("checkOrder".equals(task.getTaskDefinitionKey())){
				
				//使用基本类型作为流程变量
//				//审核结果 
//				String checkResult = "1";//1表示审核通过 0审核不通过
//				//设置流程变量variables
//				Map<String, Object> variables = new HashMap<String, Object>();
//				//checkResult就是变量名
//				variables.put("checkResult",checkResult);
//				//任务完成设置变量
//				taskService.complete(taskId, variables);
				
				//使用order对象作为流程变量
				Order order = new Order();
				order.setName("测试采购单001");
				order.setPrice(1000f);
				order.setCheckResult("1");//审核通过
				
				Map<String, Object> variables = new HashMap<String, Object>();
//				//order就是变量名
				variables.put("order",order);
//				//任务完成设置变量
				taskService.complete(taskId, variables);
				
			}else{
				taskService.complete(taskId);
			}
			
			
			System.out.println("完成任务："+taskId);
			
		}
		
		
		
	}
	
}
