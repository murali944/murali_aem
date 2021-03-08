package com.adobe.aem.guides.jishaan.core.servlets;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentFilter;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;

@Component(service = WorkflowProcess.class, property = {"process.label = Custom Workflow for Setting Data"})
public class CustomWorkflow implements WorkflowProcess{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Reference
	protected Replicator replicator;
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
		try {
			//Model path , workflow to be triggered
			String model="/var/workflow/models/set_property_to_node";
			log.debug("Inside execute method");
			WorkflowData workflowData =workItem.getWorkflowData();
			//payload path to be activated
			String path = workflowData.getPayload().toString();
			log.debug("Payload path-->{}",path);
			ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);
			Session session= resolver.adaptTo(Session.class);
			Resource resourcePath= resolver.getResource(path);
			Resource jcrContent=resourcePath.getChild(JcrConstants.JCR_CONTENT);
			Node nodePath=jcrContent.adaptTo(Node.class);
			nodePath.setProperty("customProp", "Murali");
			session.save();
			List<String> agents=new ArrayList<>();
			agents.add("publish");
			agents.add("publish-1");
			for (String agent : agents) {
				ReplicationOptions opts = getReplicationOptions(agent);
				replicator.replicate(session, ReplicationActionType.ACTIVATE, path, opts);
			}
			log.debug("Payload path Has customProp property-->{}",nodePath.hasProperty("customProp"));
		} catch (PathNotFoundException e) {
			log.debug("Exception occured->{}",e);
		} catch (RepositoryException e) {
			log.debug("RepositoryException Exception occured->{}",e);
		} catch (ReplicationException e) {
			log.debug("Replication Exception occured->{}",e);
		}
	}

	public ReplicationOptions getReplicationOptions(final String agentId){
		log.debug("In side getReplicationOptions--->");
		ReplicationOptions opts = null;
		try{
			opts = new ReplicationOptions();
			opts.setFilter(new AgentFilter(){
				public boolean isIncluded(final Agent agent) {
					log.debug("Printing agentID--->",agent.getId());
					return agentId.equals(agent.getId());
				}
			});
		} catch(Exception e){
			log.error("Exception in creating Replication Options : "+e);
		}
		log.debug("ReplicationOptions--->{}",opts);
		return opts;
	}
	
	public void invokeWorkFlow(String workflowpath, String payLoadPath,
			WorkflowSession wfSession) {
		WorkflowModel model;
		try {
			model = wfSession.getModel(workflowpath);
			WorkflowData wfData = wfSession.newWorkflowData("JCR_PATH",
					payLoadPath);
			wfSession.startWorkflow(model, wfData);
		} catch (WorkflowException e) {
			log.error("InvokeWorkFlow Encountered Problem " + e.getMessage());
		}

	}

}
