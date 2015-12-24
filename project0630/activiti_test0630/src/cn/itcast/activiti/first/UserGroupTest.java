package cn.itcast.activiti.first;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

public class UserGroupTest {
	private ProcessEngine processEngine;
	
	//让每个测试方法执行前构建processEngine
	@Before
	public void setUp()throws Exception{
		//构造 ProcessEngine
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		processEngine = processEngineConfiguration.buildProcessEngine();
		
	}
	
	//设置用户和组信息
		@Test
		public void saveUserGroup(){
			
			//通过identityService操作用户和组信息
			IdentityService identityService = processEngine.getIdentityService();
			//创建组
		  if(identityService.createGroupQuery().groupId("s0103").singleResult()==null){
				GroupEntity groupEntity = new GroupEntity();
				groupEntity.setId("s0103");
				groupEntity.setName("卫生室");

				identityService.saveGroup(groupEntity);
			}
			if(identityService.createGroupQuery().groupId("s0102").singleResult()==null){
				GroupEntity groupEntity = new GroupEntity();
				groupEntity.setId("s0102");
				groupEntity.setName("卫生院");

				identityService.saveGroup(groupEntity);
			}
			if(identityService.createGroupQuery().groupId("s0104").singleResult()==null){
				GroupEntity groupEntity = new GroupEntity();
				groupEntity.setId("s0104");
				groupEntity.setName("供货商");

				identityService.saveGroup(groupEntity);
			}
			
			//创建用户
			if(identityService.createUserQuery().userId("zhangsan").singleResult()==null){
				UserEntity userEntity = new UserEntity();
				userEntity.setId("zhangsan");
				userEntity.setFirstName("张三");
				userEntity.setLastName("");
				identityService.saveUser(userEntity);
			}
			if(identityService.createUserQuery().userId("lisi").singleResult()==null){
				UserEntity userEntity = new UserEntity();
				userEntity.setId("lisi");
				userEntity.setFirstName("李四");
				userEntity.setLastName("");
				identityService.saveUser(userEntity);
			}
			if(identityService.createUserQuery().userId("wangwu").singleResult()==null){
				UserEntity userEntity = new UserEntity();
				userEntity.setId("wangwu");
				userEntity.setFirstName("王五");
				userEntity.setLastName("");
				identityService.saveUser(userEntity);
			}
			
			//添加用户和组关系
			//添加方式，先删除关系再添加关系 
			identityService.deleteMembership("zhangsan", "s0103");//删除zhangsan和s0103的关系
			identityService.createMembership("zhangsan", "s0103");//添加zhangsan和s0103的关系
			identityService.deleteMembership("lisi", "s0102");
			identityService.createMembership("lisi", "s0102");
			identityService.deleteMembership("wangwu", "s0104");
			identityService.createMembership("wangwu", "s0104");

			
		}
		
		//部署流程定义
		@Test
		public void deployment()throws Exception{
			
			//使用RepositoryService的api
			RepositoryService repositoryService = processEngine.getRepositoryService();
			//创建部署对象
			DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
			//将线下的bpmn和png文件部署到数据
			deploymentBuilder.addClasspathResource("diagram/purchasingflow5.bpmn");
			deploymentBuilder.addClasspathResource("diagram/purchasingflow5.png");
			
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
//			runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)
			System.out.println("流程实例id："+processInstance.getId());

			System.out.println("流程定义id："+processInstance.getProcessDefinitionId());
		}
		
		//查询待办组任务
		@Test
		public void testFindGroupTaskList()throws Exception{
			//通过TaskService查询
			TaskService taskService = processEngine.getTaskService();
			
			//创建任务查询对象
			TaskQuery createTaskQuery = taskService.createTaskQuery();
			//指定候选人
			String candidateuser = "wangwu";
			createTaskQuery.taskCandidateUser(candidateuser);
			//查询组任务
			List<Task> list = createTaskQuery.list();
			
			//遍历任务
			for(Task task:list){
				System.out.println("=================");
				System.out.println("任务id："+task.getId());
				System.out.println("任务名称："+task.getName());
				System.out.println("任务负责人："+task.getAssignee());
				System.out.println("任务所属的流程实例："+task.getProcessInstanceId());
			}
			
		}
		
		//拾取组任务
		@Test
		public void testClaimGroupTask()throws Exception{
			//通过TaskService查询
			TaskService taskService = processEngine.getTaskService();
			//任务id,组任务id
			String taskId = "3606";
			//任务负责人
			String userId = "wangwu";
			//下边的方法，拾取人不是组内成员也可以拾取，实际使用时要校验，此人能否查询到组任务
			//创建任务查询对象
			TaskQuery createTaskQuery = taskService.createTaskQuery();
			//指定候选人
			String candidateuser = userId;
			createTaskQuery.taskCandidateUser(candidateuser);
			createTaskQuery.taskId(taskId);
			Task task = createTaskQuery.singleResult();
			if(task !=null){//说明userId这个人可以查询到taskId组任务，userId这个人是组内成员 
				taskService.claim(taskId, userId);
				System.out.println("拾取组任务："+taskId);
			}

		}
		
		//查询个人的待办任务
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
//			createTaskQuery.listPage(arg0, arg1);//分页查询
			//遍历任务
			for(Task task:list){
				System.out.println("=================");
				System.out.println("任务id："+task.getId());
				System.out.println("任务名称："+task.getName());
				System.out.println("任务负责人："+task.getAssignee());
				System.out.println("任务所属的流程实例："+task.getProcessInstanceId());
			}
		}		
		
		//完成个人任务
		@Test
		public void testCompleteTask()throws Exception{
			
			//指定任务id
			String taskId  ="3606";
			//任务负责人
			String assignee = "wangwu";
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
//				taskService.complete(taskId);
				
				//执行采购单审核时设置流程变量
				if("checkOrder".equals(task.getTaskDefinitionKey())){
					
					//使用基本类型作为流程变量
//					//审核结果 
//					String checkResult = "1";//1表示审核通过 0审核不通过
//					//设置流程变量variables
//					Map<String, Object> variables = new HashMap<String, Object>();
//					//checkResult就是变量名
//					variables.put("checkResult",checkResult);
//					//任务完成设置变量
//					taskService.complete(taskId, variables);
					
					//使用order对象作为流程变量
					Order order = new Order();
					order.setName("测试采购单001");
					order.setPrice(1000f);
					order.setCheckResult("1");//审核通过
					
					Map<String, Object> variables = new HashMap<String, Object>();
//					//order就是变量名
					variables.put("order",order);
//					//任务完成设置变量
					taskService.complete(taskId, variables);
					
				}else{
					taskService.complete(taskId);
				}
				
				
				System.out.println("完成任务："+taskId);
				
			}
		}
}
