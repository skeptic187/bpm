/* Licensed under the Apache License, Version 2.0 (the "License");
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
package de.hrw.bpm.app;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;

@Named
@ConversationScoped
public class ApproveOrderController implements Serializable {

	private static final long serialVersionUID = 1L;

	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;

	// Inject the EntityManager to access the persisted order
	@PersistenceContext
	private EntityManager entityManager;

	// Inject the OrderBusinessLogic to update the persisted order
	@Inject
	private OrderBusinessLogic orderBusinessLogic;

	// Caches the OrderEntity during the conversation
	private OrderEntity orderEntity;

	public OrderEntity getOrderEntity() {
		if (orderEntity == null) {
			// Load the order entity from the database if not already cached
			orderEntity = orderBusinessLogic.getOrder((Long) businessProcess.getVariable("orderId"));
		}
		return orderEntity;
	}

	public void submitForm() throws IOException {
		// Persist updated order entity and complete task form
		orderBusinessLogic.mergeOrderAndCompleteTask(orderEntity);
	}
}
